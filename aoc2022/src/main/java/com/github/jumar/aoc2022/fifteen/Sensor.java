package com.github.jumar.aoc2022.fifteen;

import java.awt.Point;

public class Sensor {
	Beacon closestBeacon;
	Point pos;

	public Sensor(Beacon b, Point point) {
		closestBeacon = b;
		pos = point;
	}
}
