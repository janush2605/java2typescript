/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.visitors;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import java2typescript.jackson.module.Configuration;
import java2typescript.jackson.module.grammar.AnyType;

public class TSJsonAnyFormatVisitor extends ABaseTSJsonFormatVisitor<AnyType> implements JsonAnyFormatVisitor {
    public TSJsonAnyFormatVisitor(ABaseTSJsonFormatVisitor parentHolder, Configuration conf) {
        super(parentHolder, conf);
        type = AnyType.getInstance();
    }
}
