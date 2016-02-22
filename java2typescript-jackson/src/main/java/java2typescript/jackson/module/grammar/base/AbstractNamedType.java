/*
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
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
