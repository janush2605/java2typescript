/*
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jaxrs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Lukasz Janczara, PL
 */
public class Param {
    private String name;
    private ParamType type;
    private boolean context = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParamType getType() {
        return type;
    }

    public void setType(ParamType type) {
        this.type = type;
    }

    /**
     * If true, this param corresponds to a technical @Context param, and should be ignored in output generation
     */
    @JsonIgnore
    public boolean isContext() {
        return context;
    }

    public void setContext(boolean context) {
        this.context = context;
    }
}
