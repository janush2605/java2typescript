/*
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jaxrs;

import com.cg.helix.mib.annotation.ComponentInterface;
import com.cg.helix.schemadictionary.annotation.ComplexType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java2typescript.jackson.module.DefinitionGenerator;
import java2typescript.jackson.module.grammar.*;
import java2typescript.jackson.module.grammar.base.AbstractNamedType;
import java2typescript.jackson.module.grammar.base.AbstractType;
import java2typescript.jaxrs.model.Method;
import java2typescript.jaxrs.model.Param;
import java2typescript.jaxrs.model.RestService;

import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.beans.Introspector;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java2typescript.jaxrs.model.ParamType.*;

/**
 * @author Lukasz Janczara, PL
 *
 * Generates a {@link RestService} description out of a service class /
 * interface
 */
public class ServiceDescriptorGenerator {

    private static final String JS_TEMPLATE_RES = "module-template.js";

    private static final String MODULE_NAME_PLACEHOLDER = "%MODULE_NAME%";
    private static final String JSON_PLACEHOLDER = "%JSON%";

    static private final String ROOT_URL_VAR = "rootUrl";
    static private final String ADAPTER_VAR = "adapter";

    private final Collection<? extends Class<?>> classes;

    private ObjectMapper mapper;

    public ServiceDescriptorGenerator(Collection<? extends Class<?>> classes) {
        this(classes, new ObjectMapper());
    }

    public ServiceDescriptorGenerator(Collection<? extends Class<?>> classes, ObjectMapper mapper) {
        this.classes = classes;
        this.mapper = mapper;
        addDummyMappingForJAXRSClasses();
    }

    private class DummySerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
                JsonProcessingException {
            // No implementation here
        }
    }

    /**
     * Those classes will be transformed as "any"
     */
    private void addDummyMappingForJAXRSClasses() {
        SimpleModule module = new SimpleModule("dummy jax-rs mappings");
        module.addSerializer(Response.class, new DummySerializer());
        module.addSerializer(UriInfo.class, new DummySerializer());
        module.addSerializer(Request.class, new DummySerializer());
        mapper.registerModule(module);
    }

    /**
     * Main method to generate a REST Service desciptor out of JAX-RS service
     * class
     */
    private Collection<RestService> generateRestServices(Collection<? extends Class<?>> classes) {

        List<RestService> services = new ArrayList<RestService>();

        for (Class<?> clazz : classes) {

            RestService service = new RestService();
            service.setName(clazz.getSimpleName());

            ComponentInterface pathAnnotation = clazz.getAnnotation(ComponentInterface.class);

            if (pathAnnotation == null) {
                throw new RuntimeException("No @ComponentInterface on class " + clazz.getName());
            }

            service.setPath(pathAnnotation.name());

            for (java.lang.reflect.Method method : clazz.getDeclaredMethods()) {
                if (Modifier.isPublic(method.getModifiers())) {
                    Method restMethod = generateMethod(method);
                    service.getMethods().put(restMethod.getName(), restMethod);
                }
            }

            services.add(service);
        }
        return services;
    }

    /**
     * Generates a typescript definition of the REST service together with all
     * required named types (classes and enums)
     */
    public Module generateTypeScript(String moduleName) throws JsonMappingException {

        // Generates Typescript module out of service classses definition
        DefinitionGenerator defGen = new DefinitionGenerator(mapper);
        Module module = defGen.generateTypeScript(moduleName, classes, null, ComplexType.class, Collections.<Module>emptyList());

        // For each rest service, update methods with parameter names, got from Rest service descriptor
        for (RestService restService : generateRestServices(classes)) {
            ClassType classDef = (ClassType) module.getNamedTypes().get(restService.getName());
            decorateParamNames(restService, classDef);
        }

        addModuleVars(module, classes);

        return module;
    }

    private Method generateMethod(java.lang.reflect.Method method) {

        Method restMethod = new Method();
        Path pathAnnotation = method.getAnnotation(Path.class);

        restMethod.setPath(pathAnnotation == null ? "" : pathAnnotation.value());

        restMethod.setName(method.getName());

        restMethod.setParams(generateParams(method));

        return restMethod;
    }

    private List<Param> generateParams(java.lang.reflect.Method method) {
        List<Param> params = new ArrayList<Param>();
        for (Annotation[] annotations : method.getParameterAnnotations()) {
            Param param = new Param();
            param.setType(BODY); // By default, in case of no annotation
            param.setName("body");
            for (Annotation annotation : annotations) {
                fillParam(annotation, param);
            }
            params.add(param);
        }
        return params;
    }

    private void fillParam(Annotation annot, Param param) {
        if (annot instanceof PathParam) {
            param.setType(PATH);
            param.setName(((PathParam) annot).value());
        } else if (annot instanceof QueryParam) {
            param.setType(QUERY);
            param.setName(((QueryParam) annot).value());
        } else if (annot instanceof FormParam) {
            param.setType(FORM);
            param.setName(((FormParam) annot).value());
        } else if (annot instanceof Context) {
            param.setContext(true);
        }
    }

    /**
     * Use collected annotation in order to ad param names to service methods
     */
    private void decorateParamNames(RestService module, ClassType classDef) {

        // Loop on methods of the service
        for (Method method : module.getMethods().values()) {
            FunctionType function = classDef.getMethods().get(method.getName());

            // Copy ordered list of param types
            List<AbstractType> types = new ArrayList<AbstractType>();
            types.addAll(function.getParameters().values());

            function.getParameters().clear();

            int i = 0;
            for (Param param : method.getParams()) {

                // Skip @Context parameters
                if (!param.isContext()) {
                    function.getParameters().put(param.getName(), types.get(i));
                }
                i++;
            }
        }
    }

    private void addModuleVars(Module module, Collection<? extends Class<?>> serviceClasses) {
        module.getVars().put(ROOT_URL_VAR, StringType.getInstance());

        // Adapter function
        FunctionType adapterFuncType = new FunctionType();
        adapterFuncType.setResultType(VoidType.getInstance());
        adapterFuncType.getParameters().put("httpMethod", StringType.getInstance());
        adapterFuncType.getParameters().put("path", StringType.getInstance());
        adapterFuncType.getParameters().put("getParams", ClassType.getObjectClass());
        adapterFuncType.getParameters().put("postParams", ClassType.getObjectClass());
        adapterFuncType.getParameters().put("body", AnyType.getInstance());

        module.getVars().put(ROOT_URL_VAR, StringType.getInstance());
        module.getVars().put(ADAPTER_VAR, adapterFuncType);

        // Generate : var someService : SomeService;
        for (Class<?> clazz : serviceClasses) {
            String className = clazz.getSimpleName();
            AbstractNamedType type = module.getNamedTypes().get(className);
            String varName = Introspector.decapitalize(className);
            module.getVars().put(varName, type);
        }

    }
}
