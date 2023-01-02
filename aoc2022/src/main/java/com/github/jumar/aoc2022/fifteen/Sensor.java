package com.github.jumar.aoc2022.fifteen;

import java.awt.Point;

public class Sensor {
	Beacon closestBeacon;
	Point pos;
	int distToClosestBeacon;
	Point[] poly;

	public Sensor(Beacon b, Point point, int f) {
		closestBeacon = b;
		pos = point;
		distToClosestBeacon = f;
		poly = buildPolygon();
	}

	public Point[] buildPolygon() {
		Point[] poly = new Point[4];
		var len = distToClosestBeacon;
		var center = pos;
		poly[0] = new Point(center.x - len, center.y);
		poly[1] = new Point(center.x, center.y - len);
		poly[2] = new Point(center.x + len, center.y);
		poly[3] = new Point(center.x, center.y + len);
		return poly;
	}
}
