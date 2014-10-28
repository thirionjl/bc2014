package com.sopra.battlecode.bc2014;

import java.util.LinkedList;
import java.util.List;

public class Pythagore {

	static List<String> solve(int x) {
		List<String> solutions = new LinkedList<>();
		int max = x / 2 + 1;
		for (int a = 0; a < max; a++) {
			for (int b = 0; b < max; b++) {
				for (int c = 0; c < max; c++) {
					if (a + b + c == x && a * a + b * b == c * c) {
						solutions.add(String.format("{a=%d b=%d c=%d}", a, b, c));
					}
				}
			}
		}
		return solutions;
	}

	public static void main(String[] args) {
		System.out.println(String.join("\n", solve(1500)));
	}
}
