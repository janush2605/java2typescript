/*
 * LocalDateTimeType
 * Date of creation: 2016-02-18
 * 
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.grammar;

import java2typescript.jackson.module.grammar.base.AbstractPrimitiveType;

/**
 * @author £ukasz Janczara, PL
 */
public class LocalDateType extends AbstractPrimitiveType {
    static private LocalDateType instance = new LocalDateType();

    static public LocalDateType getInstance() {
        return instance;
    }

    private LocalDateType() {
        super("Date");
    }
}
