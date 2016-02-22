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

public interface ModuleWriter {
    void write(Module module, Writer writer) throws IOException;
}
