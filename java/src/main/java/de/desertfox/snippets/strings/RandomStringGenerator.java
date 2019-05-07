package de.desertfox.snippets.strings;

import java.util.Random;

public class RandomStringGenerator {

	private static Random random = new Random();

	private static int ASCII_ALPHABET_OFFSET = 65;

	public static void main(String[] args) {
		System.out.println(alphaNumeric(64));
	}

	public static String alphaNumeric(int length) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append(getRandomAlphaNumChar());
		}
		return builder.toString();
	}

	private static char getRandomAlphaNumChar() {
		if (random.nextInt(2) == 0) {
			return String.valueOf(random.nextInt(10)).charAt(0);
		}
		char c = (char)(random.nextInt(26) + ASCII_ALPHABET_OFFSET);
		if (random.nextInt(2) == 0) {
			return c;
		} else {
			return Character.toLowerCase(c);
		}
	}

}
