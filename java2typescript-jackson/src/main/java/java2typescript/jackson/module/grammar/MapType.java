/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.grammar;

import java2typescript.jackson.module.grammar.base.AbstractType;

import java.io.IOException;
import java.io.Writer;


public class MapType extends AbstractType {
    private AbstractType valueType;
    private AbstractType keyType;

    public MapType() {
    }

    @Override
    public void write(Writer writer, String moduleGeneratorName) throws IOException {
        writer.write("{ [key: ");
        keyType.write(writer, moduleGeneratorName);
        writer.write(" ]: ");
        valueType.write(writer, moduleGeneratorName);
        writer.write(";}");
    }


    public AbstractType getValueType() {
        return valueType;
    }

    public void setValueType(AbstractType valueType) {
        this.valueType = valueType;
    }

    public AbstractType getKeyType() {
        return keyType;
    }

    public void setKeyType(AbstractType keyType) {
        this.keyType = keyType;
    }


}
