package org.java2typescript.maven.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.cg.helix.schemadictionary.annotation.ComplexType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Function;
import com.google.common.reflect.ClassPath;
import java2typescript.jackson.module.Configuration;
import java2typescript.jackson.module.DefinitionGenerator;
import java2typescript.jackson.module.grammar.Module;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.*;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;

/**
 * Generate typescript file out of RESt service definition
 *
 * @goal generate
 * @phase process-classes
 * @configurator include-project-dependencies
 * @requiresDependencyResolution compile+runtime
 */
public class MainMojo extends AbstractMojo {

    /**
     * Full class name of the REST service
     *
     * @optional
     * @parameter alias="serviceClass"
     * expression="${j2ts.serviceClass}"
     */
    private String restServiceClassName;

    /**
     * Full class name of the Complex Type class
     *
     * @optional
     * @parameter alias="dtoClass"
     * expression="${j2ts.dtoClass}"
     */
    private String complexTypeClassName;

    /**
     * List of full package names of the Complex Type class and names of their modules
     *
     * @optional
     * @parameter alias="declatationConfigs"
     * expression="${j2ts.declatationConfigs}"
     */
    private List<DeclatationConfig> declatationConfigs;

    /**
     * Full package names of the Complex Type class and names of their modules
     *
     * @optional
     * @parameter alias="declatationConfig"
     * expression="${j2ts.declatationConfig}"
     */
    private DeclatationConfig declatationConfig;

    /**
     * Full package name of the Complex Type class
     *
     * @optional
     * @parameter alias="dtoPackage"
     * expression="${j2ts.dtoClass}"
     */
    private String complexTypePackage;


    /**
     * Name of output module (ts,js)
     *
     * @optional
     * @parameter alias="moduleName"
     * expression="${j2ts.moduleName}"
     */
    private String moduleName;

    /**
     * Path to output typescript folder
     * The name will be <moduleName>.d.ts
     *
     * @required
     * @parameter alias="tsOutFolder"
     * expression="${j2ts.tsOutFolder}"
     * default-value = "${project.build.directory}"
     */
    private File tsOutFolder;

    /**
     * Path to output Js file
     * The name will be <moduleName>.js
     *
     * @required
     * @parameter alias="jsOutFolder"
     * expression="${j2ts.jsOutFolder}"
     * default-value = "${project.build.directory}"
     */
    private File jsOutFolder;

    @Override
    public void execute() throws MojoExecutionException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule("custom-mapping");
            mapper.registerModule(module);

            Configuration configuration = new Configuration();
            configuration.setGeneratePublicMethods(false);
            ClassLoader currentLoader = Thread.currentThread().getContextClassLoader();
            ClassPath classPath = ClassPath.from(currentLoader);

            // To Typescript
            for (DeclatationConfig declatationConfig : declatationConfigs) {
                generateTypescriptDeclaration(mapper, configuration, classPath, declatationConfig);
            }
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private void generateTypescriptDeclaration(ObjectMapper mapper, Configuration configuration, ClassPath classPath, DeclatationConfig declatationConfig) throws IOException {
        Writer writer = createFileAndGetWriter(tsOutFolder, declatationConfig.getModuleName() + ".d.ts");
        Set<ClassPath.ClassInfo> classInfos = classPath.getTopLevelClassesRecursive(declatationConfig.getComplexTypePackage());
        List<Class<?>> classes = from(classInfos)
                .transform((Function<ClassPath.ClassInfo, Class<?>>) (classInfo) -> classInfo.load())
                .toList();

        DefinitionGenerator definitionGenerator = new DefinitionGenerator(mapper);
        Module dtoTSModule = definitionGenerator.generateTypeScript(declatationConfig.getModuleName(), classes, configuration, ComplexType.class);
        if (declatationConfig.getReferenceDeclaration() != null){
            dtoTSModule.setReferencePaths(asList(declatationConfig.getReferenceDeclaration()));
        }
        dtoTSModule.write(writer);
        writer.close();
    }

    private Writer createFileAndGetWriter(File folder, String fileName) throws IOException {
        File file = new File(folder, fileName);
        getLog().info("Create file : " + file.getCanonicalPath());
        file.createNewFile();
        FileOutputStream stream = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(stream);
        return writer;
    }

}
