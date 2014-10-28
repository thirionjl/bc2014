package com.sopra.battlecode.bc2014;

public class Pyramid {

	final long cntBarrelsInPyramid;
	final long cntConfigurations;

	Pyramid(int barrels) {
		long n = getPyramidBaseSize(0, barrels);
		this.cntBarrelsInPyramid = n * (n + 1) / 2;
		this.cntConfigurations = countConfigurations(cntBarrelsInPyramid, barrels);
	}

	static int getPyramidBaseSize(int baseSize, int barrels) {
		int newBaseSize = baseSize + 1;
		int candidateSize = newBaseSize * (newBaseSize + 1) / 2;
		return candidateSize > barrels ? baseSize : getPyramidBaseSize(newBaseSize, barrels);
	}

	static long countConfigurations(long remainingPlacesInPiramid, long remainingBarrels) {
		return remainingPlacesInPiramid == 0 ? 1 : remainingBarrels * countConfigurations(remainingPlacesInPiramid - 1, remainingBarrels - 1);
	}

	public static void main(String[] args) {
		int n = 13;
		Pyramid p = new Pyramid(n);
		System.out.format("For %d barrels, pyramid size is %d, there are %,d configurations", n, p.cntBarrelsInPyramid, p.cntConfigurations);
	}

}
