package de.desertfox.snippets.csv;

import java.lang.reflect.Field;

public class AssignTypeSafe {

	public void assign(Object obj, Field field, String value) {
		Class<?> type = field.getType();
		if (value == null || type != String.class && value.isEmpty()) {
			return;
		}
		try {
			if (isPrimitive(type)) {
				assignPrimitive(obj, field, value);
			} else {
				assignObject(obj, field, value);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.err.println("Input value (" + value + ") of field '" + field.getName() + "' does not match type '" + type.getName() + "'");
			e.printStackTrace();
		}
	}

	private void assignObject(Object obj, Field field, String value) throws IllegalAccessException {
		Object objectValue;
		Class<?> type = field.getType();
		if (type == Integer.class) {
			objectValue = Integer.valueOf(value);
		} else if (type == Long.class) {
			objectValue = Integer.valueOf(value);
		} else if (type == Float.class) {
			objectValue = Integer.valueOf(value);
		} else if (type == Short.class) {
			objectValue = Integer.valueOf(value);
		} else if (type == Double.class) {
			objectValue = Integer.valueOf(value);
		} else {
			objectValue = value;
		}
		field.set(obj, objectValue);
	}

	private void assignPrimitive(Object obj, Field field, String value) throws IllegalAccessException {
		Class<?> type = field.getType();
		if (type == int.class) {
			field.setInt(obj, Integer.valueOf(value));
		} else if (type == long.class) {
			field.setLong(obj, Long.valueOf(value));
		} else if (type == float.class) {
			field.setFloat(obj, Float.valueOf(value));
		} else if (type == short.class) {
			field.setShort(obj, Short.valueOf(value));
		} else if (type == double.class) {
			field.setDouble(obj, Double.valueOf(value));
		}
	}

	private boolean isPrimitive(Class<?> type) {
		return type == int.class || type == long.class || type == float.class || type == short.class
				|| type == double.class;
	}

}
