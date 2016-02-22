/*
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.conf.typename;

public class WithEnclosingClassTSTypeNamingStrategy extends SimpleJacksonTSTypeNamingStrategy {

    @Override
    public String getName(Class<?> rawClass) {
        String className = rawClass.getName();
        return className.substring(className.lastIndexOf('.') + 1);
    }

}
