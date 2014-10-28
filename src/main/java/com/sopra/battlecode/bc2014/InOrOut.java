package com.sopra.battlecode.bc2014;

import java.util.ArrayList;
import java.util.List;

// To know if a point (a,b) is inside a polygon, draw the horizontal line passing through that point (of equation: y = b)
// and count how often it intersects the polygon sides but count only intersections that happen on the left (or right) side of the point (Area where x < a)
// If this intersection count is odd (1,3,5...) the point is inside the polygon, else the point is outside
public class InOrOut {

	static class Point {
		double x, y;

		Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}

	static class Segment {
		Point bottom, up;

		public Segment(Point p1, Point p2) {
			if (p1.y < p2.y) {
				this.bottom = p1;
				this.up = p2;
			} else {
				this.bottom = p2;
				this.up = p1;
			}
		}

		// Given a point p draw the horizontal line D passing by p
		// This function tells if there is an intersection between D and current
		// segment and if this intersection happens on the left side of p
		boolean intersectsHorizontalLineOnTheLeft(Point p) {
			return isPassingAtHeight(p.y) && getHorizontalPositionWhenPassingAtHeight(p.y) < p.x;
		}

		// Does this segment pass at height "height"
		boolean isPassingAtHeight(double height) {
			// Notice that horizontal segments never intersect
			return bottom.y < height && height <= up.y;
		}

		// Tells where on he X axis this segment has height "height"
		double getHorizontalPositionWhenPassingAtHeight(double height) {
			// Compute line equation, with origin point placed at O(p1.x,p1.y),
			// so that eq. has form: y = a.x
			// Notice that vertical segments have a=Infinite
			double a = (up.y - bottom.y) / (up.x - bottom.x);
			// translate "height" relative to the new origin O(p1.x,p1.y)
			double y = height - bottom.y;
			// Inject y into line equation to compute horizontal position when
			// height is y
			double x = y / a;
			// translate back to origin (0,0)
			return bottom.x + x;
		}

		@Override
		public String toString() {
			return "Segment ]" + bottom + ", " + up + "]";
		}

	}

	static class Polygon {
		List<Point> points = new ArrayList<>();

		void addPoint(Point p) {
			points.add(p);
		}

		void addPoint(double x, double y) {
			addPoint(new Point(x, y));
		}

		List<Segment> getSides() {
			int nbVertex = points.size();
			List<Segment> segments = new ArrayList<>();
			for (int i = 0; i < nbVertex; i++) {
				int j = Integer.remainderUnsigned(i + 1, nbVertex);
				segments.add(new Segment(points.get(i), points.get(j)));
			}
			return segments;
		}

		boolean contains(Point p) {
			long intersections = getSides().stream()//
					.filter(s -> s.intersectsHorizontalLineOnTheLeft(p)) //
					.peek(s -> System.out.println("Intersection on the left with " + s)) // debug
					.count();
			return intersections % 2 != 0;
		}
	}

	public static void main(String[] args) {

		Polygon p = new Polygon();
		p.addPoint(0, 0);
		p.addPoint(5, 1);
		p.addPoint(6, 0);
		p.addPoint(3, 2);
		p.addPoint(3, 5);
		p.addPoint(2, 5);
		p.addPoint(1, 4);
		p.addPoint(0, 3);
		Point x = new Point(4, 2);
		System.out.format("Point is %s polygon", p.contains(x) ? "INSIDE" : "OUTSIDE");
	}
}
