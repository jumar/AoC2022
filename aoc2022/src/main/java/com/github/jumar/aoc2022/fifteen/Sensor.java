package com.github.jumar.aoc2022.fifteen;

public class Sensor {
	Beacon closestBeacon;
	Pointf pos;
	float distToClosestBeacon;
	Pointf[] poly;

	public Sensor(Beacon b, Pointf point, float f) {
		closestBeacon = b;
		pos = point;
		distToClosestBeacon = f;
		poly = buildPolygon();
	}

	public Pointf[] buildPolygon() {
		Pointf[] poly = new Pointf[4];
		var len = distToClosestBeacon;
		var center = pos;
		poly[0] = new Pointf(center.x - len - 0.05f, center.y);
		poly[1] = new Pointf(center.x, center.y - len - 0.05f);
		poly[2] = new Pointf(center.x + len + 0.05f, center.y);
		poly[3] = new Pointf(center.x, center.y + len + 0.05f);
		return poly;
	}
}
