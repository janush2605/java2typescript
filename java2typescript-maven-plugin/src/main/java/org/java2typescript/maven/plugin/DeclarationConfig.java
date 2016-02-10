/*
 * DeclarationConfig2
 * Date of creation: 2016-02-10
 * 
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package org.java2typescript.maven.plugin;

/**
 * @author £ukasz Janczara, PL
 */
public class DeclarationConfig {
    private String complexTypePackage;

    private String moduleName;

    private String referenceDeclaration;

    private boolean generateExternalClasses;

    public DeclarationConfig() {
    }

    public DeclarationConfig(String complexTypePackage, String moduleName) {
        this.complexTypePackage = complexTypePackage;
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getComplexTypePackage() {
        return complexTypePackage;
    }

    public void setComplexTypePackage(String complexTypePackage) {
        this.complexTypePackage = complexTypePackage;
    }

    public String getReferenceDeclaration() {
        return referenceDeclaration;
    }

    public void setReferenceDeclaration(String referenceDeclaration) {
        this.referenceDeclaration = referenceDeclaration;
    }

    public boolean isGenerateExternalClasses() {
        return generateExternalClasses;
    }

    public void setGenerateExternalClasses(boolean generateExternalClasses) {
        this.generateExternalClasses = generateExternalClasses;
    }
}
