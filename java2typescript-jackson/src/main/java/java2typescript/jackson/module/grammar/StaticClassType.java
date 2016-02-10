/*******************************************************************************
 * Copyright 2013 Raphael Jolivet, 2014 Florian Benz
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
package java2typescript.jackson.module.grammar;

import java2typescript.jackson.module.grammar.base.AbstractNamedType;
import java2typescript.jackson.module.grammar.base.Value;
import java2typescript.jackson.module.writer.WriterPreferences;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static java.lang.String.format;

public class StaticClassType extends AbstractNamedType {

    private Map<String, Value> fields = new HashMap<String, Value>();

    public StaticClassType(String className) {
        super(className);
    }

    @Override
    public void writeDefInternal(Writer writer, WriterPreferences prefs) throws IOException {
        writer.write(format("class %s {\n", name));
        prefs.increaseIndentation();
        for (Entry<String, Value> entry : fields.entrySet()) {
            writer.write(format("%sstatic %s: ", prefs.getIndentation(), entry.getKey()));
            entry.getValue().getType().write(writer);
            writer.write(" = ");
            writer.write(entry.getValue().getValue().toString());
            writer.write(";\n");
        }
        prefs.decreaseIndention();
        writer.write(prefs.getIndentation() + "}");
    }

    public Map<String, Value> getStaticFields() {
        return fields;
    }
}
