package com.sopra.battlecode.bc2014;

import java.util.Arrays;

import static com.sopra.battlecode.bc2014.arithmetic.ArithmeticUtils.lcm;

public class Marathon {

	private static final int MINUTES_PER_HOUR = 60;

	private static int[] pairProducts(int[] speeds) {
		int[] products = new int[speeds.length];
		int k = 0;
		for (int i = 0; i < speeds.length; i++) {
			for (int j = i + 1; j < speeds.length; j++) {
				products[k] = speeds[i] * speeds[j];
				k++;
			}
		}
		return products;
	}

	private static int product(int[] speeds) {
		return Arrays.stream(speeds).reduce(1, (i, j) -> i * j);
	}

	public static void main(String[] args) {
		int[] speedsInKmh = { 12, 14, 5 };

		int[] pairProducts = pairProducts(speedsInKmh);
		double durationInMinutes = MINUTES_PER_HOUR * lcm(pairProducts) / product(speedsInKmh);

		System.out.format("Runners will all cross the line at same time after %.1f minutes", durationInMinutes);
	}

}
