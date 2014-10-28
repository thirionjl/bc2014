package com.sopra.battlecode.bc2014;

import java.util.stream.LongStream;

public class SmallestInteger {

	static boolean isDivisible(long x, long n) {
		// There is less chances to be divisible by higher numbers so reverse
		// iteration order to be faster
		for (long i = x; i >= 2; i--) {
			if (n % i != 0)
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		int x = 20;
		long start = System.currentTimeMillis();
		long solution = LongStream.iterate(1, n -> n + 1).parallel().filter(n -> isDivisible(x, n)).findFirst().getAsLong();
		long stop = System.currentTimeMillis();
		System.out.format("Solution is %,d found in %d millis", solution, stop - start);
	}
}
