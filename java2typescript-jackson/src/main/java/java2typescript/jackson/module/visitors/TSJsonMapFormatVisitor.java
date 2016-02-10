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
