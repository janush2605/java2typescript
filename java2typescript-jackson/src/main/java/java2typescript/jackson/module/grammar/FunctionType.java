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
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class FunctionType extends AbstractType {

    private LinkedHashMap<String, AbstractType> parameters = new LinkedHashMap<String, AbstractType>();

    private AbstractType resultType;

    /**
     * By default, printed as lambda function type (with =>)
     */
    @Override
    public void write(Writer writer, String moduleGeneratorName) throws IOException {
        write(writer, moduleGeneratorName, true);
    }

    /**
     * Write as non lambda : func(a:string) : string
     */
    public void writeNonLambda(Writer writer, String moduleGeneratorName) throws IOException {
        write(writer, moduleGeneratorName, false);
    }

    private void write(Writer writer, String moduleGeneratorName, boolean lambdaSyntax) throws IOException {
        writer.write("(");
        int i = 1;
        for (Entry<String, AbstractType> entry : parameters.entrySet()) {
            writer.write(entry.getKey());
            writer.write(": ");
            entry.getValue().write(writer, moduleGeneratorName);
            if (i < parameters.size()) {
                writer.write(", ");
            }
            i++;
        }
        writer.write(")" + (lambdaSyntax ? "=> " : ": "));
        resultType.write(writer, moduleGeneratorName);
    }

    public LinkedHashMap<String, AbstractType> getParameters() {
        return parameters;
    }

    public AbstractType getResultType() {
        return resultType;
    }

    public void setResultType(AbstractType resultType) {
        this.resultType = resultType;
    }

}
