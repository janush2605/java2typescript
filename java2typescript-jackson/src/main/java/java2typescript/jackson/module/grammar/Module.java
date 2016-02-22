/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.grammar;

import java2typescript.jackson.module.grammar.base.AbstractNamedType;
import java2typescript.jackson.module.grammar.base.AbstractType;
import java2typescript.jackson.module.writer.InternalModuleFormatWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Module {

    private String name;

    private Map<String, AbstractNamedType> namedTypes = new HashMap<String, AbstractNamedType>();

    private Map<String, AbstractType> vars = new HashMap<String, AbstractType>();

    private List<String> referencePaths = new ArrayList<>();

    private List<Module> referenceModules = new ArrayList<>();
    private AbstractNamedType abstractNamedType;

    public Module() {
    }


    //========================================================
    // Public methods
    //========================================================

    public AbstractNamedType resolveTypeName(String name) {
        for (Module module : referenceModules) {
            abstractNamedType = module.getNamedTypes().get(name);
            if (abstractNamedType != null) {
                return abstractNamedType;
            }
        }
        return getNamedTypes().get(name);
    }

    //========================================================
    // Accessors
    //========================================================

    public Module(String name) {
        this.name = name;
    }

    public Map<String, AbstractNamedType> getNamedTypes() {
        return namedTypes;
    }

    public Map<String, AbstractType> getVars() {
        return vars;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getReferencePaths() {
        return referencePaths;
    }

    public void setReferencePaths(List<String> referencePaths) {
        this.referencePaths = referencePaths;
    }

    public void write(Writer writer) throws IOException {
        new InternalModuleFormatWriter().write(this, writer);
    }

    public List<Module> getReferenceModules() {
        return referenceModules;
    }

    public void setReferenceModules(List<Module> referenceModules) {
        this.referenceModules = referenceModules;
    }
}
