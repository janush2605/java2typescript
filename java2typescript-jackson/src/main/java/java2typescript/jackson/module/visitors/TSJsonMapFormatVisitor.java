/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.visitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import java2typescript.jackson.module.Configuration;
import java2typescript.jackson.module.grammar.MapType;

import static java2typescript.jackson.module.visitors.TSJsonFormatVisitorWrapper.getTSTypeForHandler;


public class TSJsonMapFormatVisitor extends ABaseTSJsonFormatVisitor<MapType> implements JsonMapFormatVisitor {

    public TSJsonMapFormatVisitor(ABaseTSJsonFormatVisitor parentHolder, Configuration conf) {
        super(parentHolder, conf);
        type = new MapType();
    }

    @Override
    public void keyFormat(JsonFormatVisitable handler, JavaType keyType) throws JsonMappingException {
        type.setKeyType(getTSTypeForHandler(this, handler, keyType, conf));
    }

    @Override
    public void valueFormat(JsonFormatVisitable handler, JavaType valueType) throws JsonMappingException {
        type.setValueType(getTSTypeForHandler(this, handler, valueType, conf));
    }

}
