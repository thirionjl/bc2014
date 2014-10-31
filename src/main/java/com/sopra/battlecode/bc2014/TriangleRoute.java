package com.sopra.battlecode.bc2014;

import static java.lang.Math.max;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.Arrays;

import com.sopra.battlecode.bc2014.resources.ResourceUtils;

public class TriangleRoute {

	private static final int[][] ARRAY_TOKEN = new int[][] {};

	static int maxInTriangle(int[][] triangle) {
		int[] maxes = {};
		for (int[] row : triangle) {
			maxes = maxInCurrentRow(row, maxes);
		}
		return stream(maxes).max().orElse(0);
	}

	static int[] maxInCurrentRow(int[] row, int[] previousMaxes) {
		if (previousMaxes.length == 0)
			return new int[] { row[0] };

		int len = row.length;
		int[] newMaxes = new int[len];

		newMaxes[0] = row[0] + previousMaxes[0];
		newMaxes[len - 1] = row[len - 1] + previousMaxes[len - 2];
		for (int i = 1; i < len - 1; i++) {
			newMaxes[i] = row[i] + max(previousMaxes[i - 1], previousMaxes[i]);
		}
		return newMaxes;
	}

	private static int[] toInts(String[] ary) {
		return Arrays.stream(ary).mapToInt(Integer::parseInt).toArray();
	}

	public static void main(String[] args) throws IOException {
		int[][] triangle = ResourceUtils
				.getLineStream("/Triangle/enormeTriangle.txt")
				.map(line -> toInts(line.split("\\s+")))//
				.collect(toList()).toArray(ARRAY_TOKEN);

		System.out.println(maxInTriangle(triangle));
	}

}