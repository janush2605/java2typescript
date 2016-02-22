/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.FluentIterable.from;

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
    private List<DeclarationConfig> declatationConfigs;


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

    @Override
    public void execute() throws MojoExecutionException {

        List<Module> refenceModules = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule("custom-mapping");
            mapper.registerModule(module);

            Configuration configuration = new Configuration();
            configuration.setGeneratePublicMethods(false);
            ClassLoader currentLoader = Thread.currentThread().getContextClassLoader();
            ClassPath classPath = ClassPath.from(currentLoader);

            // To Typescript
            for (DeclarationConfig declatationConfig : declatationConfigs) {
                Module tsModule = generateTypescriptDeclaration(mapper, configuration, classPath, declatationConfig, refenceModules);
                refenceModules.add(tsModule);
            }
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private Module generateTypescriptDeclaration(ObjectMapper mapper, Configuration configuration, ClassPath classPath,
                                                 DeclarationConfig declatationConfig, List<Module> refenceModules) throws IOException {
        Writer writer = createFileAndGetWriter(tsOutFolder, declatationConfig.getModuleName() + ".d.ts");
        List<Class<?>> classes =
                from(declatationConfig.getComplexTypePackages())
                        .transformAndConcat(ClassesFromPackages(classPath))
                        .transform((Function<ClassPath.ClassInfo, Class<?>>) (classInfo) -> classInfo.load())
                        .toList();

        DefinitionGenerator definitionGenerator = new DefinitionGenerator(mapper);
        Module dtoTSModule = definitionGenerator.generateTypeScript(declatationConfig.getModuleName(), classes, configuration, ComplexType.class, refenceModules);
        if (declatationConfig.getReferenceDeclarations() != null) {
            dtoTSModule.setReferencePaths(declatationConfig.getReferenceDeclarations());
        }
        dtoTSModule.write(writer);
        writer.close();
        return dtoTSModule;
    }

    private Function<String, Set<ClassPath.ClassInfo>> ClassesFromPackages(final ClassPath classPath) {
        return new Function<String, Set<ClassPath.ClassInfo>>() {
            @Override
            public Set<ClassPath.ClassInfo> apply(String complexTypePackage) {
                return classPath.getTopLevelClassesRecursive(complexTypePackage);
            }
        };
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
