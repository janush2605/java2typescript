/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.grammar;

import java2typescript.jackson.module.grammar.base.AbstractPrimitiveType;

public class NullType extends AbstractPrimitiveType {

    static private NullType instance = new NullType();

    static public NullType getInstance() {
        return instance;
    }

    private NullType() {
        super("null");
    }
}
