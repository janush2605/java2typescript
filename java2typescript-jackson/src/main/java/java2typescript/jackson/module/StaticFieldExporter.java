/**
 * Copyright (c) CompuGROUP Software GmbH,
 * This software is the confidential and proprietary information of
 * CompuGROUP Software GmbH. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of
 * the license agreement you entered into with CompuGROUP Software GmbH.
 */
package java2typescript.jackson.module;

import com.fasterxml.jackson.databind.type.SimpleType;
import java2typescript.jackson.module.grammar.*;
import java2typescript.jackson.module.grammar.base.AbstractType;
import java2typescript.jackson.module.grammar.base.Value;
import java2typescript.jackson.module.visitors.TSJsonFormatVisitorWrapper;

import java.lang.reflect.Field;
import java.util.List;

public class StaticFieldExporter {
    private static final String CLASS_NAME_EXTENSION = "Static";

    private final Module module;
    private final TSJsonFormatVisitorWrapper tsJsonFormatVisitorWrapper;

    public StaticFieldExporter(Module module, Configuration conf) {
        this.module = module;
        if (conf == null) {
            conf = new Configuration();
        }
        tsJsonFormatVisitorWrapper = new TSJsonFormatVisitorWrapper(module, conf);
    }

    /**
     * @deprecated - use constructor and instance method instead
     */
    @Deprecated
    public static void export(Module module, List<Class<?>> classesToConvert) {
        new StaticFieldExporter(module, null).export(classesToConvert);
    }

    public void export(List<Class<?>> classesToConvert)
            throws IllegalArgumentException {
        for (Class<?> clazz : classesToConvert) {
            if (clazz.isEnum()) {
                continue;
            }
            StaticClassType staticClass = new StaticClassType(clazz.getSimpleName()
                    + CLASS_NAME_EXTENSION, module.getName());

            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                if (isPublicStaticFinal(field.getModifiers())) {
                    Value value;
                    try {
                        value = constructValue(module, field.getType(), field.get(null));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to get value of field " + field, e);
                    }
                    if (value != null) {
                        staticClass.getStaticFields().put(field.getName(), value);
                    }
                }
            }
            if (staticClass.getStaticFields().size() > 0) {
                module.getNamedTypes().put(staticClass.getName(), staticClass);
            }
        }
    }

    private boolean isPublicStaticFinal(final int modifiers) {
        return java.lang.reflect.Modifier.isPublic(modifiers)
                && java.lang.reflect.Modifier.isStatic(modifiers)
                && java.lang.reflect.Modifier.isFinal(modifiers);
    }

    private Value constructValue(Module module, Class<?> type, Object rawValue)
            throws IllegalArgumentException, IllegalAccessException {
        if (type == boolean.class) {
            return new Value(BooleanType.getInstance(), rawValue);
        } else if (type == int.class) {
            return new Value(NumberType.getInstance(), rawValue);
        } else if (type == double.class) {
            return new Value(NumberType.getInstance(), rawValue);
        } else if (type == String.class) {
            return new Value(StringType.getInstance(), "'" + (String) rawValue + "'");
        } else if (type.isEnum()) {
            final EnumType enumType = tsJsonFormatVisitorWrapper.parseEnumOrGetFromCache(module,
                    SimpleType.construct(type));
            return new Value(enumType, enumType.getName() + "." + rawValue);
        } else if (type.isArray()) {
            final Class<?> componentType = type.getComponentType();
            final Object[] array;
            if (componentType == boolean.class) {
                boolean[] tmpArray = (boolean[]) rawValue;
                array = new Boolean[tmpArray.length];
                for (int i = 0; i < array.length; i++) {
                    array[i] = Boolean.valueOf(tmpArray[i]);
                }
            } else if (componentType == int.class) {
                int[] tmpArray = (int[]) rawValue;
                array = new Integer[tmpArray.length];
                for (int i = 0; i < array.length; i++) {
                    array[i] = Integer.valueOf(tmpArray[i]);
                }
            } else if (componentType == double.class) {
                double[] tmpArray = (double[]) rawValue;
                array = new Double[tmpArray.length];
                for (int i = 0; i < array.length; i++) {
                    array[i] = Double.valueOf(tmpArray[i]);
                }
            } else {
                array = (Object[]) rawValue;
            }
            final StringBuilder arrayValues = new StringBuilder();
            arrayValues.append("[ ");
            for (int i = 0; i < array.length; i++) {
                arrayValues.append(constructValue(module, componentType, array[i]).getValue());
                if (i < array.length - 1) {
                    arrayValues.append(", ");
                }
            }
            arrayValues.append(" ]");
            return new Value(new ArrayType(typeScriptTypeFromJavaType(module, componentType)),
                    arrayValues.toString());
        }
        return null;
    }

    private AbstractType typeScriptTypeFromJavaType(Module module, Class<?> type) {
        if (type == boolean.class) {
            return BooleanType.getInstance();
        } else if (type == int.class) {
            return NumberType.getInstance();
        } else if (type == double.class) {
            return NumberType.getInstance();
        } else if (type == String.class) {
            return StringType.getInstance();
        } else if (type.isEnum()) {
            return tsJsonFormatVisitorWrapper.parseEnumOrGetFromCache(module, SimpleType
                    .construct(type));
        } else if (type.isArray()) {
            return new ArrayType(AnyType.getInstance());
        }
        throw new UnsupportedOperationException();
    }
}
