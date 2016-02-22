/*
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.grammar.base;

import java2typescript.jackson.module.grammar.AnyType;
import java2typescript.jackson.module.grammar.LocalDateTimeType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Janczara, PL
 */
public class CustomPrimitiveTypeProvider {

    public static final Map<String, AbstractPrimitiveType> CUSTOM_PRIMITIVE_TYPES;

    static {
        CUSTOM_PRIMITIVE_TYPES = new HashMap<>();
        CUSTOM_PRIMITIVE_TYPES.put("LocalDateTime", LocalDateTimeType.getInstance());
        CUSTOM_PRIMITIVE_TYPES.put("LocalDate", LocalDateTimeType.getInstance());
        CUSTOM_PRIMITIVE_TYPES.put("DataObject", AnyType.getInstance());
    }
}
