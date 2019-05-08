package de.desertfox.snippets.strings;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.function.Supplier;

import de.desertfox.snippets.random.RandomUtil;
import javafx.util.Pair;

public class StringUtil {

	public static final String UTF_8 = "UTF-8";

	private static final String EMPTY_STRING = "";

	public static boolean equals(String s1, String s2) {
		return equals(s1, s2, false);
	}

	public static boolean equals(String s1, String s2, boolean trim) {
		if (s1 == s2) {
			return true;
		}
		if (s1 == null || s2 == null) {
			return false;
		}
		if (trim) {
			s1 = s1.trim();
			s2 = s2.trim();
		}
		return s1.equals(s2);
	}

	public static String concat(String separator, String... strings) {
		if (strings == null || strings.length == 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer(strings[0]);
		for (int i = 1; i < strings.length; i++) {
			buffer.append(separator).append(strings[i]);
		}
		return buffer.toString();
	}

	public static String random(int length) {
		byte[] array = new byte[length];
		new Random().nextBytes(array);
		return new String(array, Charset.forName(UTF_8));
	}

	public static String randomAlpha(int length) {
		return randomStringFromCharsInSpecificRanges(length, range('a', 'z'), range('A', 'Z'));
	}

	public static String randomAlphaNumeric(int length) {
		return randomStringFromCharsInSpecificRanges(length, range('a', 'z'), range('A', 'Z'), range('0', '9'));
	}

	@SafeVarargs
	public static String randomStringFromCharsInSpecificRanges(int length, Pair<Integer, Integer>... ranges) {
		Supplier<Integer> random = RandomUtil.ranged(ranges);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append((char)(int)random.get());
		}
		return buffer.toString();
	}

	private static Pair<Integer, Integer> range(char from, char to) {
		return new Pair<>((int)from, (int)to);
	}

}
