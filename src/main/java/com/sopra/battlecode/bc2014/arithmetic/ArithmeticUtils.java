package com.sopra.battlecode.bc2014.arithmetic;

import com.google.common.math.IntMath;

public class ArithmeticUtils {

	/* Least Common Multiple */
	public static int lcm(int... ints) {
		return lcm_rec(1, 0, ints);
	}

	private static int lcm_rec(int acc, int pos, int[] ints) {
		if (pos >= ints.length - 1) {
			return acc;
		} else {
			int newAcc = acc * ints[pos] / gcd(acc, ints[pos]);
			return lcm_rec(newAcc, pos + 1, ints);
		}
	}

	public static int gcd(int a, int b) {
		return IntMath.gcd(a, b);
	}

}
