package com.sopra.battlecode.bc2014.geometry;

public class Point {
	public double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}