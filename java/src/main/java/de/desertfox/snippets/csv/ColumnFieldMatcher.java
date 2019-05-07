package de.desertfox.snippets.csv;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColumnFieldMatcher<T> {

	private Map<Integer, Field> map = new HashMap<>();

	public ColumnFieldMatcher(Class<T> clazz, String... columnNames) {
		this(clazz, false, columnNames);
	}

	public ColumnFieldMatcher(Class<T> clazz, boolean ignoreCase, String... columnNames) {
		Field[] fields = clazz.getDeclaredFields();
		List<String> columns;
		if (ignoreCase) {
			columns = new ArrayList<>();
			for (String column : columnNames) {
				columns.add(column.toLowerCase());
			}
		} else {
			columns = Arrays.asList(columnNames);
		}
		for (Field field : fields) {
			map.put(columns.indexOf(field.getName()), field);
		}
	}

	public Field get(int column) {
		return map.get(column);
	}

}
