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
package java2typescript.jackson.module.grammar.base;

import java2typescript.jackson.module.grammar.LocalDateTimeType;
import java2typescript.jackson.module.visitors.ABaseTSJsonFormatVisitor;
import java2typescript.jackson.module.visitors.TSJsonLocalDateTimeFormatVisitor;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class AbstractPrimitiveType extends AbstractType {

    private String token;

    public AbstractPrimitiveType(String token) {
        this.token = token;
    }

    @Override
    public void write(Writer writer, String moduleGeneratorName) throws IOException {
        writer.write(token);
    }
}
