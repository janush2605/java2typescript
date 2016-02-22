/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.visitors;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import java2typescript.jackson.module.Configuration;
import java2typescript.jackson.module.grammar.NumberType;

import java.util.Set;

public class TSJsonNumberFormatVisitor extends ABaseTSJsonFormatVisitor<NumberType> implements JsonNumberFormatVisitor,
        JsonIntegerFormatVisitor {

    public TSJsonNumberFormatVisitor(ABaseTSJsonFormatVisitor parentHolder, Configuration conf) {
        super(parentHolder, conf);
        type = NumberType.getInstance();
    }

    @Override
    public void format(JsonValueFormat format) {
    }

    @Override
    public void enumTypes(Set<String> enums) {
    }

    @Override
    public void numberType(com.fasterxml.jackson.core.JsonParser.NumberType type) {

    }

}
