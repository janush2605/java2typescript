package java2typescript.jackson.module;

import java2typescript.jackson.module.conf.typename.SimpleJacksonTSTypeNamingStrategy;
import java2typescript.jackson.module.conf.typename.TSTypeNamingStrategy;
import java2typescript.jackson.module.grammar.ArrayType;
import java2typescript.jackson.module.grammar.base.AbstractType;

import java.beans.Transient;
import java.lang.reflect.Method;
import java.util.*;

public class Configuration {

    private Map<String, AbstractType> customTypes = Collections.emptyMap();
    private List<String> ignoredMethodNames = new ArrayList<String>();
    private TSTypeNamingStrategy namingStrategy = new SimpleJacksonTSTypeNamingStrategy();
    private Boolean generatePublicMethods = true;

    public Map<String, AbstractType> getCustomTypes() {
        return customTypes;
    }

    public Configuration addType(Class<?> klass, AbstractType tsType) {
        addType(klass.getName(), tsType);
        addArrayType(klass, tsType);
        return this;
    }

    public void addArrayType(Class<?> klass, AbstractType tsType) {
        addType("[L" + klass.getName() + ";", new ArrayType(tsType));
    }

    public void addType(String className, AbstractType tsType) {
        Map<String, AbstractType> tmp = new HashMap<String, AbstractType>();
        tmp.putAll(customTypes);
        tmp.put(className, tsType);
        customTypes = Collections.unmodifiableMap(tmp);
    }

    public void addIngoredMethod(String name) {
        ignoredMethodNames.add(name);
    }

    public boolean isIgnoredMethod(Method method) {
        if (method.getAnnotation(Transient.class) != null) {
            return true;
        }
        return isIgnoredMethod(method.getName());
    }

    private boolean isIgnoredMethod(String name) {
        return ignoredMethodNames.contains(name);
    }

    public TSTypeNamingStrategy getNamingStrategy() {
        return namingStrategy;
    }

    public void setNamingStrategy(TSTypeNamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
    }

    public Boolean getGeneratePublicMethods() {
        return generatePublicMethods;
    }

    public void setGeneratePublicMethods(Boolean generatePublicMethods) {
        this.generatePublicMethods = generatePublicMethods;
    }
}
