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

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNullFormatVisitor;
import java2typescript.jackson.module.Configuration;
import java2typescript.jackson.module.grammar.NullType;

public class TSJsonNullFormatVisitor extends ABaseTSJsonFormatVisitor<NullType> implements JsonNullFormatVisitor {
    public TSJsonNullFormatVisitor(ABaseTSJsonFormatVisitor parentHolder, Configuration conf) {
        super(parentHolder, conf);
        type = NullType.getInstance();
    }
}
