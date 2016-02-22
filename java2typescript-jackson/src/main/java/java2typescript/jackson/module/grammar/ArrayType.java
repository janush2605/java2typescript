/*
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


public class ArrayType extends AbstractType {
    private AbstractType itemType;

    public ArrayType() {
    }

    public ArrayType(AbstractType aType) {
        itemType = aType;
    }

    @Override
    public void write(Writer writer, String moduleGeneratorName) throws IOException {
        writer.write("Array<");
        itemType.write(writer, moduleGeneratorName);
        writer.write(">");
    }

    public void setItemType(AbstractType itemType) {
        this.itemType = itemType;
    }


}
