/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.writer;

import java2typescript.jackson.module.grammar.base.AbstractNamedType;

import java.io.IOException;
import java.io.Writer;

/**
 * Class implementing this interface can be used to customize how Java type is written to TypeScript
 *
 * @author Ats Uiboupin
 */
public interface CustomAbstractTypeWriter {
    /**
     * @return true if this class should handle writing the type to {@link Writer}
     */
    boolean accepts(AbstractNamedType type, WriterPreferences preferences);

    void writeDef(AbstractNamedType type, Writer writer, WriterPreferences preferences) throws IOException;
}
