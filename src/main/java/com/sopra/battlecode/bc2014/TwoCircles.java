package com.sopra.battlecode.bc2014;

import java.math.BigDecimal;

// Two circle intersect if distance between the centers is less that the sum of radiuses
// If one circle is inside the other, two circle intersect if distance between centers is higher than radiuses delta
public class TwoCircles {

	enum Intersection {
		ZERO, ONE, TWO, INFINITY
	}

	static class Point {
		// We want to be precise when checking for equality in case of tangent
		// circles
		BigDecimal x, y;

		public Point(BigDecimal x, BigDecimal y) {
			this.x = x;
			this.y = y;
		}

		public Point(int x, int y) {
			this(new BigDecimal(x), new BigDecimal(y));
		}

		BigDecimal squareDistanceTo(Point p) {
			return x.subtract(p.x).pow(2).add(y.subtract(p.y).pow(2));
		}

		// Lightweight "equals" without hashcode here
		boolean isSame(Point p) {
			return p.x.equals(x) && p.y.equals(y);
		}

	}

	static class Circle {
		Point center;
		BigDecimal radius;

		public Circle(Point center, BigDecimal radius) {
			this.center = center;
			this.radius = radius;
		}

		public Circle(Point center, int radius) {
			this(center, new BigDecimal(radius));
		}

		Intersection getIntersections(Circle c) {
			BigDecimal squaredDistance = center.squareDistanceTo(c.center);
			BigDecimal squaredRadiusDelta = radius.subtract(c.radius).pow(2);
			BigDecimal squaredRadiusSum = radius.add(c.radius).pow(2);

			int radiusDeltaGreaterThanCenterDistance = squaredRadiusDelta.compareTo(squaredDistance);
			int centerDistanceGreaterThanRadiusSum = squaredDistance.compareTo(squaredRadiusSum);

			if (radiusDeltaGreaterThanCenterDistance > 0 || centerDistanceGreaterThanRadiusSum > 0) {
				return Intersection.ZERO;
			}
			if (radiusDeltaGreaterThanCenterDistance == 0 || centerDistanceGreaterThanRadiusSum == 0) {
				return center.isSame(c.center) ? Intersection.INFINITY : Intersection.ONE;
			}
			return Intersection.TWO;
		}
	}

	public static void main(String[] args) {
		Circle c1 = new Circle(new Point(3, 3), 2);
		Circle c2 = new Circle(new Point(3, 3), 2);
		System.out.format("%s intersection(s)", c1.getIntersections(c2));
	}
}
