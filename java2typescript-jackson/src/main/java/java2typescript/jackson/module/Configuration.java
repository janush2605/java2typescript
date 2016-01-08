package java2typescript.jackson.module;

import java.beans.Transient;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java2typescript.jackson.module.grammar.ArrayType;
import java2typescript.jackson.module.grammar.base.AbstractType;

public class Configuration {
	private Map<String, AbstractType> customTypes = Collections.emptyMap();
	private List<String> ignoredMethodNames = new ArrayList<String>();

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

}
