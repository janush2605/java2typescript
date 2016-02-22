/*
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.grammar;

import java2typescript.jackson.module.grammar.base.AbstractPrimitiveType;

public class AnyType extends AbstractPrimitiveType {

    static private AnyType instance = new AnyType();

    static public AnyType getInstance() {
        return instance;
    }

    private AnyType() {
        super("any");
    }

}
