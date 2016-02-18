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
package java2typescript.jackson.module.grammar;

import java2typescript.jackson.module.grammar.base.AbstractNamedType;
import java2typescript.jackson.module.grammar.base.AbstractType;
import java2typescript.jackson.module.writer.WriterPreferences;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import static java.lang.String.format;

public class ClassType extends AbstractNamedType {

    private Map<String, AbstractType> fields = new LinkedHashMap<String, AbstractType>();

    private Map<String, FunctionType> methods = new LinkedHashMap<String, FunctionType>();

    static private ClassType objectType = new ClassType("Object", EMPTY_MODULE);

    /**
     * Root Object class
     */
    static public ClassType getObjectClass() {
        return objectType;
    }

    public ClassType(String className, String moduleName) {
        super(className, moduleName);
    }

    @Override
    public void writeDefInternal(Writer writer, WriterPreferences preferences) throws IOException {
        writer.write(format("interface %s {\n", name));
        preferences.increaseIndentation();
        for (Entry<String, AbstractType> entry : fields.entrySet()) {
            writer.write(format("%s%s: ", preferences.getIndentation(), entry.getKey()));
            entry.getValue().write(writer, moduleName);
            writer.write(";\n");
        }
        for (String methodName : methods.keySet()) {
            writer.write(preferences.getIndentation() + methodName);
            this.methods.get(methodName).writeNonLambda(writer, moduleName);
            writer.write(";\n");
        }
        preferences.decreaseIndention();
        writer.write(preferences.getIndentation() + "}");
    }

    public Map<String, AbstractType> getFields() {
        return fields;
    }

    public void setFields(Map<String, AbstractType> fields) {
        this.fields = fields;
    }

    public Map<String, FunctionType> getMethods() {
        return methods;
    }
}
