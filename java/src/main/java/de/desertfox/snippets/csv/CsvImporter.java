package de.desertfox.snippets.csv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CsvImporter {

	public static void main(String[] args) throws Exception {
		File file = new File(CsvImporter.class.getResource("SomeObject.csv").toURI());
		List<SomeObject> objects = importFile(file, SomeObject.class);
		objects.forEach(System.out::println);
	}

	public static <T> List<T> importFile(File file, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException {
		List<T> list = new ArrayList<>();
		List<String> lines = Files.readAllLines(file.toPath());
		ColumnFieldMatcher<T> matcher = new ColumnFieldMatcher<>(clazz, lines.get(0).split(", *"));
		TypeSafeAssigner assigner = new TypeSafeAssigner();
		for (int i = 1; i < lines.size(); i++) {
			T obj = clazz.newInstance();
			list.add(obj);
			String[] columns = lines.get(i).split(", *");
			for (int c = 0; c < columns.length; c++) {
				assigner.assign(obj, matcher.get(c), columns[c]);
			}
		}
		return list;
	}

}
