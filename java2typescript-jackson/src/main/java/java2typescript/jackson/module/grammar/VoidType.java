/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.grammar;

import java2typescript.jackson.module.grammar.base.AbstractPrimitiveType;

public class VoidType extends AbstractPrimitiveType {

    static private VoidType instance = new VoidType();

    static public VoidType getInstance() {
        return instance;
    }

    private VoidType() {
        super("void");
    }
}
