/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.grammar;

import java2typescript.jackson.module.grammar.base.AbstractNamedType;
import java2typescript.jackson.module.writer.WriterPreferences;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;


public class EnumType extends AbstractNamedType {

    private List<String> values = new ArrayList<String>();

    public EnumType(String className, String moduleName) {
        super(className, moduleName);
    }

    @Override
    public void writeDefInternal(Writer writer, WriterPreferences preferences) throws IOException {
        writer.write(format("enum %s {\n", name));
        preferences.increaseIndentation();
        for (String value : values) {
            writer.write(format("%s%s,\n", preferences.getIndentation(), value));
        }
        preferences.decreaseIndention();
        writer.write(preferences.getIndentation() + "}");
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

}
