/*
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.grammar;

import java2typescript.jackson.module.grammar.base.AbstractPrimitiveType;

public class BooleanType extends AbstractPrimitiveType {

    static private BooleanType instance = new BooleanType();

    static public BooleanType getInstance() {
        return instance;
    }

    private BooleanType() {
        super("boolean");
    }
}
