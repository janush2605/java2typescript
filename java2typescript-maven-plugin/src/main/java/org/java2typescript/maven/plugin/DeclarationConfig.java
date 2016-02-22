/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package org.java2typescript.maven.plugin;

import java.util.List;

/**
 *
 * @author Lukasz Janczara, PL
 *
 * Full package names of the Complex Type class and name of their modules
 *
 * @optional
 * @parameter alias="declatationConfig"
 * expression="${j2ts.declatationConfig}"
 */
public class DeclarationConfig {

    /**
     * List of full package name of the Complex Type class
     *
     * @optional
     * @parameter alias="dtoPackages"
     * expression="${j2ts.dtoClasses}"
     */
    private List<String> complexTypePackages;


    /**
     * Name of output module (ts)
     *
     * @optional
     * @parameter alias="moduleName"
     * expression="${j2ts.moduleName}"
     */
    private String moduleName;

    /**
     * Name of reference to another  module (ts)
     *
     * @optional
     * @parameter alias="referenceDeclarations"
     * expression="${j2ts.referenceDeclarations}"
     */
    private List<String> referenceDeclarations;

    /**
     * Flag indicates generating external classes (ts)
     *
     * @optional
     * @parameter alias="generateExternalClasses"
     * expression="${j2ts.generateExternalClasses}"
     */
    private boolean generateExternalClasses;

    public DeclarationConfig() {
    }

    public DeclarationConfig(List<String> complexTypePackages, String moduleName) {
        this.complexTypePackages = complexTypePackages;
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public boolean isGenerateExternalClasses() {
        return generateExternalClasses;
    }

    public void setGenerateExternalClasses(boolean generateExternalClasses) {
        this.generateExternalClasses = generateExternalClasses;
    }

    public List<String> getReferenceDeclarations() {
        return referenceDeclarations;
    }

    public void setReferenceDeclarations(List<String> referenceDeclarations) {
        this.referenceDeclarations = referenceDeclarations;
    }

    public List<String> getComplexTypePackages() {
        return complexTypePackages;
    }

    public void setComplexTypePackages(List<String> complexTypePackages) {
        this.complexTypePackages = complexTypePackages;
    }
}
