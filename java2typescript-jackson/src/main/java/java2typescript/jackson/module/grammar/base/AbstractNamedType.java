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

import java2typescript.jackson.module.writer.WriterPreferences;

import java.io.IOException;
import java.io.Writer;

/**
 * Type referenced by its name and capable of writing its own definition
 */
abstract public class AbstractNamedType extends AbstractType {

    protected static final String EMPTY_MODULE = "";

    protected final String name;

    protected final String moduleName;

    public AbstractNamedType(String className, String moduleName) {
        this.name = className;
        this.moduleName = moduleName;
    }

    @Override
    public void write(Writer writer, String moduleGeneratorName) throws IOException {
        if (!moduleName.equals(moduleGeneratorName)){
            writer.write(moduleName + "." + name);
        } else {
            writer.write(name);
        }

    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        if (moduleName != null && EMPTY_MODULE.equals(moduleName)) {
            return moduleName.concat(".").concat(name);
        }
        return name;
    }

    public void writeDef(Writer writer, WriterPreferences preferences) throws IOException {
        if (!preferences.hasCustomWriter(this)) {
            writeDefInternal(writer, preferences);
        } else {
            preferences.writeDef(this, writer);
        }
    }

    abstract public void writeDefInternal(Writer writer, WriterPreferences preferences) throws IOException;
}
