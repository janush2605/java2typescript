/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
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

    public StaticClassType(String className, String moduleName) {
        super(className, moduleName);
    }

    @Override
    public void writeDefInternal(Writer writer, WriterPreferences prefs) throws IOException {
        writer.write(format("class %s {\n", name));
        prefs.increaseIndentation();
        for (Entry<String, Value> entry : fields.entrySet()) {
            writer.write(format("%sstatic %s: ", prefs.getIndentation(), entry.getKey()));
            entry.getValue().getType().write(writer, moduleName);
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
