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
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import java2typescript.jackson.module.Configuration;
import java2typescript.jackson.module.grammar.*;
import java2typescript.jackson.module.grammar.base.AbstractType;

public class TSJsonArrayFormatVisitor extends ABaseTSJsonFormatVisitor<ArrayType> implements JsonArrayFormatVisitor {

    public TSJsonArrayFormatVisitor(ABaseTSJsonFormatVisitor parentHolder, Configuration conf) {
        super(parentHolder, conf);
        type = new ArrayType();
    }

    @Override
    public void itemsFormat(JsonFormatVisitable handler, JavaType elementType) throws JsonMappingException {
        TSJsonFormatVisitorWrapper visitorWrapper = new TSJsonFormatVisitorWrapper(this, conf);
        handler.acceptJsonFormatVisitor(visitorWrapper, elementType);
        type.setItemType(visitorWrapper.getType());
    }

    @Override
    public void itemsFormat(JsonFormatTypes format) throws JsonMappingException {
        type.setItemType(typeScriptTypeFromJsonType(format));
    }

    private static AbstractType typeScriptTypeFromJsonType(JsonFormatTypes type) {
        switch (type) {
            case ANY:
                return AnyType.getInstance();
            case BOOLEAN:
                return BooleanType.getInstance();
            case ARRAY:
                return new ArrayType(AnyType.getInstance());
            case INTEGER: //$FALL-THROUGH$
            case NUMBER:
                return NumberType.getInstance();
            case STRING:
                return StringType.getInstance();
            default:
                throw new UnsupportedOperationException();
        }
    }
}
