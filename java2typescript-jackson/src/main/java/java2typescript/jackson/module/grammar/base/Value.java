/*
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.grammar.base;

public class Value {
    private AbstractType type;

    private Object value;

    public Value(AbstractType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public AbstractType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
