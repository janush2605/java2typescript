/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module.visitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWithSerializerProvider;
import java2typescript.jackson.module.Configuration;
import java2typescript.jackson.module.grammar.Module;
import java2typescript.jackson.module.grammar.base.AbstractType;

import java.util.HashMap;
import java.util.Map;

/**
 * Chained providers with a root parent holder keeping a map of already
 * compiuted types, and
 */
public abstract class ABaseTSJsonFormatVisitor<T extends AbstractType> implements
        JsonFormatVisitorWithSerializerProvider {

    private final ABaseTSJsonFormatVisitor<?> parentHolder;

    protected T type;

    private SerializerProvider serializerProvider;

    private Module module;

    private Map<JavaType, AbstractType> computedTypes;

    protected final Configuration conf;

    public ABaseTSJsonFormatVisitor(ABaseTSJsonFormatVisitor parentHolder, Configuration conf) {
        this.parentHolder = parentHolder;
        this.conf = conf;
    }

    public ABaseTSJsonFormatVisitor(Module module, Configuration conf) {
        this.parentHolder = null;
        this.module = module;
        this.conf = conf;
    }

    public SerializerProvider getProvider() {
        return (parentHolder == null) ? serializerProvider : parentHolder.getProvider();
    }

    public void setProvider(SerializerProvider provider) {
        if (parentHolder != null) {
            parentHolder.setProvider(provider);
        } else {
            serializerProvider = provider;
        }
    }

    public Module getModule() {
        if (parentHolder == null) {
            return module;
        } else {
            return parentHolder.getModule();
        }
    }

    public Map<JavaType, AbstractType> getComputedTypes() {
        if (parentHolder == null) {
            if (computedTypes == null) {
                computedTypes = new HashMap<JavaType, AbstractType>();
            }
            return computedTypes;
        } else {
            return parentHolder.getComputedTypes();
        }
    }

    public T getType() {
        return type;
    }
}
