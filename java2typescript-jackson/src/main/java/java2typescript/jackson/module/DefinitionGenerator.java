/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java2typescript.jackson.module.grammar.Module;
import java2typescript.jackson.module.visitors.TSJsonFormatVisitorWrapper;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Main class that generates a TypeScript grammar tree (a Module), out of a
 * class, together with a {@link ObjectMapper}
 */
public class DefinitionGenerator {

    private final ObjectMapper mapper;

    public DefinitionGenerator(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * @param moduleName Module to be filled with named types (classes, enums, ...)
     * @param classes    Class for which generating definition
     * @throws JsonMappingException
     */
    public Module generateTypeScript(String moduleName, Collection<? extends Class<?>> classes, Configuration conf)
            throws JsonMappingException {
        return generateTypeScript(moduleName, classes, conf, null, Collections.<Module>emptyList());
    }

    /**
     * @param moduleName Module to be filled with named types (classes, enums, ...)
     * @param classes    Class for which generating definition
     * @throws JsonMappingException
     */
    public Module generateTypeScript(String moduleName, Collection<? extends Class<?>> classes, Configuration configuration, Class classAnnotation)
            throws JsonMappingException {
        return generateTypeScript(moduleName, classes, configuration, classAnnotation, Collections.<Module>emptyList());
    }

    public Module generateTypeScript(String moduleName, Collection<? extends Class<?>> classes, Configuration configuration,
                                     Class classAnnotation, List<Module> refenceModules) throws JsonMappingException {
        if (configuration == null) {
            configuration = new Configuration();
        }

        Module module = new Module(moduleName);
        module.setReferenceModules(refenceModules);
        TSJsonFormatVisitorWrapper visitor = new TSJsonFormatVisitorWrapper(module, configuration);

        for (Class<?> clazz : classes) {
            if (classAnnotation != null) {
                Annotation annotation = clazz.getAnnotation(classAnnotation);
                if (annotation != null) {
                    mapClass(visitor, clazz);
                }
            } else {
                mapClass(visitor, clazz);
            }

        }
        return module;

    }

    private void mapClass(TSJsonFormatVisitorWrapper visitor, Class<?> clazz) throws JsonMappingException {
        System.out.println(clazz.getPackage().getName() + "." + clazz.getName());
        mapper.acceptJsonFormatVisitor(clazz, visitor);
    }
}
