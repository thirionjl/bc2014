package com.sopra.battlecode.bc2014;

public class Tangents {

	static class Circle {
		double x, y, r;

		Circle(double x, double y, double r) {
			this.x = x;
			this.y = y;
			this.r = r;
		}

		double distanceTo(Circle c) {
			return Math.sqrt(Math.pow(c.y - y, 2) + Math.pow(c.x - x, 2));
		}
		
		double sizeRatio(Circle c) {
			return r / (c.r - r);
		}

	}

	public static void main(String[] args) {
		// c1.x>c2.x
		Circle c2 = new Circle(2, 2, 2);
		Circle c1 = new Circle(4, 2, 2);

		double t = c1.sizeRatio(c2) * c1.distanceTo(c2);

		double a = (c2.y - c1.y) / (c2.x - c1.x);

		if (t < 0) {
			System.out.format("Solution: %f %f", c2.x + t, c2.y + a * t);
		} else {
			System.out.format("Solution: %f %f", c1.x + t, c1.y + a * t);
		}
	}
}
