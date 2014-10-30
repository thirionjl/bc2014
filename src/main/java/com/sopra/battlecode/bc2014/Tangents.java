package com.sopra.battlecode.bc2014;

import com.sopra.battlecode.bc2014.geometry.Point;

import static java.lang.Math.pow;
import static java.lang.Math.signum;
import static java.lang.Math.sqrt;

/**
 * Let's define:
 * <ul>
 * <li>C1 be the smallest of both circle with radius r1 and center o1</li>
 * <li>C2 be the largest of both circle with radius r2 and center o2</li>
 * <li>Point I as the intersection between external tangents</li>
 * <li>d the distance between centers</li>
 * <li>x distance between o1 and I
 * </ul>
 * as in figure below:
 * 
 * <pre>
 *                                   +
 *                                   |
 *                  +                |
 *                  |                | r2
 *                  | r1             |
 *  +---------------+----------------+
 *  I  <--- x --->  o1  <--- d --->  o2
 * </pre>
 * 
 * Then by Thales' theorem<code> x = d.r1 / (r2 - r1) </code>
 * 
 * <p>
 * Finally, we compute direction of o2-o1 so to get coordinates of vector o1->I
 * (of length x). The final step is to apply the vector o1->I to point o1 to
 * find I's coordinates.
 * </p>
 */
public class Tangents {

	static class Circle {
		final Point center;
		final double radius;

		public Circle(Point center, double radius) {
			this.center = center;
			this.radius = radius;
		}

		double distanceTo(Circle c) {
			return sqrt(pow(c.center.y - center.y, 2) + pow(c.center.x - center.x, 2));
		}
	}

	static class Direction {
		Vector2D unitVector;

		Direction(Point start, Point stop) {
			double deltaX = stop.x - start.x;
			double deltaY = stop.y - start.y;
			double signX = signum(deltaX);
			double signY = signum(deltaY);
			this.unitVector = deltaX != 0 ? new Vector2D(signX, signY * deltaY / deltaX) : new Vector2D(0, signY);
		}
	}

	static class Vector2D {
		double deltaX, deltaY;

		Vector2D(double deltaX, double deltaY) {
			this.deltaX = deltaX;
			this.deltaY = deltaY;
		}

		Vector2D(Direction dir, double length) {
			Vector2D uv = dir.unitVector;
			this.deltaX = uv.deltaX * length;
			this.deltaY = uv.deltaY * length;
		}

		Point applyTo(Point p) {
			return new Point(p.x + deltaX, p.y + deltaY);
		}
	}

	public static void main(String[] args) {
		Circle a = new Circle(new Point(0, 0), 2);
		Circle b = new Circle(new Point(2, 0), 1);

		if (a.radius == b.radius) {
			System.out.println("Tangents do not intersect");
		} else {
			Circle c1 = a.radius < b.radius ? a : b;
			Circle c2 = a.radius < b.radius ? b : a;

			double intersectionDistanceToCenter1 = c1.distanceTo(c2) * c1.radius / (c2.radius - c1.radius);
			Point intersection = new Vector2D(new Direction(c2.center, c1.center), intersectionDistanceToCenter1).applyTo(c1.center);
			System.out.format("Tangents intersect at %s", intersection);
		}

	}
}
