package de.desertfox.snippets.csv;

import java.lang.reflect.Field;

public class TypeSafeAssigner {

	public void assign(Object obj, Field field, String value) {
		Class<?> type = field.getType();
		if (value == null || type != String.class && value.isEmpty()) {
			return;
		}
		try {
			if (field.isAccessible()) {
				field.set(obj, parseValue(field.getType(), value));
			} else {
				field.setAccessible(true);
				field.set(obj, parseValue(field.getType(), value));
				field.setAccessible(false);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.err.println("Input value (" + value + ") of field '" + field.getName() + "' does not match type '" + type.getName() + "'");
			e.printStackTrace();
		}
	}

	private Object parseValue(Class<?> type, String value) throws IllegalAccessException {
		Object objectValue;
		if (type == Integer.class || type == int.class) {
			objectValue = Integer.valueOf(value);
		} else if (type == Long.class || type == long.class) {
			objectValue = Long.valueOf(value);
		} else if (type == Float.class || type == float.class) {
			objectValue = Float.valueOf(value);
		} else if (type == Short.class || type == short.class) {
			objectValue = Short.valueOf(value);
		} else if (type == Double.class || type == double.class) {
			objectValue = Double.valueOf(value);
		} else {
			objectValue = value;
		}
		return objectValue;
	}

}
