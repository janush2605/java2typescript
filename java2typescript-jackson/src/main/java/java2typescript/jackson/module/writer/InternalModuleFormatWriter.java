/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.writer;

import java2typescript.jackson.module.grammar.Module;

import java.io.IOException;
import java.io.Writer;

import static java.lang.String.format;

/**
 * Generates TypeScript type definitions for given module in internal module format
 */
public class InternalModuleFormatWriter extends ExternalModuleFormatWriter {

    @Override
    public void write(Module module, Writer writer) throws IOException {
        for (String referencePath : module.getReferencePaths()) {
            writer.write(format("///<reference path=\"%s\"/>\n", referencePath));
        }
        writer.write("\n");
        writer.write(format("declare module %s {\n\n", module.getName()));
        preferences.increaseIndentation();
        writeModuleContent(module, writer);
        preferences.decreaseIndention();
        writer.write("}\n");
        writer.flush();
    }

}
