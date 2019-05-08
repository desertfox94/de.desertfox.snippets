package de.desertfox.snippets.random;

import java.util.Random;
import java.util.function.Supplier;

import javafx.util.Pair;

public class RandomUtil {

	private static Random random = new Random();

	@SafeVarargs
	public static Supplier<Integer> ranged(Pair<Integer, Integer>... ranges) {
		if (ranges == null) {
			return () -> random.nextInt();
		}
		return () -> {
			Pair<Integer, Integer> range = ranges[random.nextInt(ranges.length)];
			return range.getKey() + (int)(random.nextFloat() * (range.getValue() - range.getKey() + 1));
		};
	}

}
