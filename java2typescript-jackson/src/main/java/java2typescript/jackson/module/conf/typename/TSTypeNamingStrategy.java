/*
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.conf.typename;

import com.fasterxml.jackson.databind.JavaType;

/**
 * Used to detect TypeScript type name based on given Java type
 */
public interface TSTypeNamingStrategy {

    /**
     * @param type Javatype
     * @return name of the TypeScript class corresponding to Java class
     */
    String getName(JavaType type);

}
