/*******************************************************************************
 * Copyright 2013 Raphael Jolivet
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
