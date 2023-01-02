package com.github.jumar.aoc2022.fifteen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import processing.core.PApplet;
import processing.core.PImage;

public class Fifteen extends PApplet {
	// Defining the image
	PImage img;
	List<List<Point>> lines = new ArrayList<>();
	List<Sensor> sensors = new ArrayList<>();
	public static final int size_w = 30;
	public static final int size_h = 30;
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	private int minBX;
	private int maxBX;
	private int minBY;
	private int maxBY;
	private boolean debug = true;

	private static int factor = 16;

	public static void main(String[] args) {
		// part 2
		// distress beacon 0 < x < 4_000_000
		// tuning freq = beacon.x*4_000_000+y

		PApplet.main(new String[] { "com.github.jumar.aoc2022.fifteen.Fifteen" });
	}

	@Override
	public void settings() {
		// Set the canvas width and height
		size(size_w * factor, size_h * factor);
		super.settings();
	}

	private Sensor parseLine(String line) {
		int sensorX = Integer.parseInt(StringUtils.substringBetween(line, "Sensor at x=", ", y="));
		int sensorY = Integer.parseInt(StringUtils.substringBetween(line, ", y=", ": closest beacon is at x="));
		int beaconX = Integer.parseInt(StringUtils.substringBetween(line, ": closest beacon is at x=", ", y="));
		String cc = line.substring(line.indexOf("closest beacon is at x=")).split("y=")[1];

		int beaconY = Integer.parseInt(cc);
		Point bPos = new Point(beaconX, beaconY);
		Beacon b = new Beacon(bPos);
		var sPos = new Point(sensorX, sensorY);
		Sensor s = new Sensor(b, sPos, computeDist(sPos, b.pos));
		return s;
	}

	@Override
	public void setup() {
		surface.setResizable(true);
		frameRate(1);
		// Set the canvas color
		background(255);
		// Set the pen color
		// stroke(0);
//		String[] lineArray = loadStrings("data/15.txt");
		String[] lineArray = loadStrings("data/15test.txt");
		minX = minBX = Integer.MAX_VALUE;
		maxX = maxBX = Integer.MIN_VALUE;
		minY = minBY = Integer.MAX_VALUE;
		maxY = maxBY = Integer.MIN_VALUE;

		for (int i = 0; i < lineArray.length; i++) {
			var s = parseLine(lineArray[i]);
			sensors.add(s);
			if (s.closestBeacon.pos.x < 0)
				System.out.println();
			float x = Math.min(s.pos.x, s.closestBeacon.pos.x);
			if (x < minX) {
				minX = (int) x;
			}
			float stoBdist = computeDist(s.pos, s.closestBeacon.pos);
			minBX = (int) Math.min(minBX, s.pos.x - stoBdist);

			int xx = (int) Math.max(s.pos.x, s.closestBeacon.pos.x);
			if (xx > maxX) {
				maxX = xx;
			}
			maxBX = (int) Math.max(maxBX, s.pos.x + stoBdist);

			int y = (int) Math.min(s.pos.y, s.closestBeacon.pos.y);
			if (y < minY) {
				minY = y;
			}
			minBY = (int) Math.min(minBY, s.pos.y - stoBdist);

			int yy = (int) Math.max(s.pos.y, s.closestBeacon.pos.y);
			if (yy > maxY) {
				maxY = yy;
			}
			maxBY = (int) Math.max(maxBY, s.pos.y + stoBdist);
		}
		System.out.println("ranges x:[" + minX + " ... " + maxX + "] y:[" + minY + " ... " + maxY + "]");
		System.out.println("ranges minBx:[" + minBX + " ... " + maxBX + "] minBy:[" + minBY + " ... " + maxBY + "]");
		// ranges x:[-2 ... 25] y:[0 ... 22]
		// ranges minBx:[-8 ... 28] minBy:[-10 ... 26]6]
		if (debug)
			printCave();
		// for each point P of the line
		// search for Sensors which dist to closest B is < than dist between P and S
		// if found -> cannot be a B here
//		int rowOfInterest = 2000000;
		int rowOfInterest = 10;
		// minBY = maxBY = rowOfInterest;
		Map<Integer, Integer> nbPosThatCannotBeBeaconsMap = new HashMap<>();
		Point p = new Point(0, 0);
		// for each point P of the line
		int nbPosThatCannotBeBeacons = 0;
		if (debug)
			for (int r = minBY; r <= maxBY; r++) {
				if (debug)
					System.out.print(String.format("%03d ", r));
				for (int c = minBX; c <= maxBX; c++) {
//				if (c % 10_000 == 0)
//					System.out.println(c);
					p.setLocation((float) c, (float) r);

					boolean canBeaconNotBePresent = canBeaconNotBePresent(p);
					if (canBeaconNotBePresent) {
						nbPosThatCannotBeBeacons++;
						boolean found = false;
						for (int i = 0; i < sensors.size(); i++) {
							if (sensors.get(i).pos.equals(p)) {
								if (debug)
									System.out.print("S");
								found = true;
								break;
							} else if (sensors.get(i).closestBeacon.pos.equals(p)) {
								if (debug)
									System.out.print("B");
								found = true;
								break;
							}
						}
						if (!found) {
							if (debug)
								System.out.print("#");
						}
					} else {
						boolean found = false;
						for (int i = 0; i < sensors.size(); i++) {
							if (sensors.get(i).pos.equals(p)) {
								if (debug)
									System.out.print("S");
								found = true;
								break;
							} else if (sensors.get(i).closestBeacon.pos.equals(p)) {
								if (debug)
									System.out.print("B");
								found = true;
								break;
							}
						}
						if (!found) {
							if (debug)
								System.out.print(".");
						}
					}
				}
				nbPosThatCannotBeBeaconsMap.put(r, nbPosThatCannotBeBeacons);
				nbPosThatCannotBeBeacons = 0;
				if (debug)
					System.out.println();
			}
		System.out.println("At line " + rowOfInterest + " the nb of Pos That Cannot Be Beacons="
				+ nbPosThatCannotBeBeaconsMap.get(rowOfInterest));
		// 8_531_658 too high
		// 4_055_737 too low
		// 5_108_096
		//
		// part 2
		// 1. for each position, check if inside all the beacons polygons
		boolean inSensorReach = false;
		minBY = minBX = 0;
		maxBY = maxBX = 20;// 4_000_000;
		for (int r = minBY; r <= maxBY; r++) {
			for (int c = minBX; c <= maxBX; c++) {
				inSensorReach = false;
				p.setLocation(c, r);
				if (c == 14 && r == 11)
					System.out.println("");
				for (int i = 0; i < sensors.size(); i++) {
					if (isPointInPolygon(p, sensors.get(i).poly)) {
						inSensorReach = true;
						break;
					}
				}
				if (!inSensorReach) {
//					System.out.println("Found! " + p);
					if (p.x >= 0 && p.x <= 20 && p.y >= 0 && p.y <= 20) {
						System.out.println("Confirmed: " + p);
					}
				}
			}
		}
		super.setup();
	}

	public boolean isPointInPolygon(Point p, Point[] polygon) {
		double minX = polygon[0].x;
		double maxX = polygon[0].x;
		double minY = polygon[0].y;
		double maxY = polygon[0].y;
		for (int i = 1; i < polygon.length; i++) {
			Point q = polygon[i];
			minX = Math.min(q.x, minX);
			maxX = Math.max(q.x, maxX);
			minY = Math.min(q.y, minY);
			maxY = Math.max(q.y, maxY);
		}

		if (p.x < minX || p.x > maxX || p.y < minY || p.y > maxY) {
			return false;
		}

		// https://wrf.ecse.rpi.edu/Research/Short_Notes/pnpoly.html
		boolean inside = false;
		for (int i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
			if ((polygon[i].y > p.y) != (polygon[j].y > p.y)
					&& p.x < (polygon[j].x - polygon[i].x) * (p.y - polygon[i].y) / (polygon[j].y - polygon[i].y)
							+ polygon[i].x) {
				inside = !inside;
			}
		}

		return inside;
	}

	private boolean canBeaconNotBePresent(Point p) {
		boolean[] b = new boolean[] { false };
		// search for Sensors which dist to closest B is < than dist between P and S
		sensors.forEach(s -> {
			var distToClosestB = computeDist(s.pos, s.closestBeacon.pos);
			var distToSensor = computeDist(p, s.pos);
			if (distToClosestB >= distToSensor && !s.closestBeacon.pos.equals(p)) {
				b[0] = true;
			}
		});
		return b[0];
	}

	private int computeDist(Point p, Point pp) {
		return Math.abs(pp.x - p.x) + Math.abs(pp.y - p.y);
	}

	@Override
	public void draw() {
		scale(factor);
		if (debug)
			drawCave();
		var completed = true;
		if (completed) {
			System.out.println("Done");
			super.draw();
		}
	}

	private void drawCave() {
		strokeWeight(1);
		for (int r = minY; r <= maxY; r++) {
			for (int c = minX; c <= maxX; c++) {
				Point p = new Point(c, r);
				boolean found = false;
				for (int i = 0; i < sensors.size(); i++) {
					if (sensors.get(i).pos.equals(p)) {
						found = true;
						break;
					} else if (sensors.get(i).closestBeacon.pos.equals(p)) {
						found = true;
						break;
					}
				}
				if (!found) {
					stroke(0, 0, 255);
					if (c == 14 && r == 11)
						stroke(0);
					point(c, r);
				}
			}
		}
		for (int r = minY; r <= maxY; r++) {
			for (int c = minX; c <= maxX; c++) {
				Point p = new Point(c, r);
				for (int i = 0; i < sensors.size(); i++) {
					if (sensors.get(i).pos.equals(p)) {
//						System.out.print("S");
						stroke(random(255), random(255), random(255));
						var poly = sensors.get(i).buildPolygon();
//						fill(255, 0);
						quad(poly[0].x, poly[0].y, poly[1].x, poly[1].y, poly[2].x, poly[2].y, poly[3].x, poly[3].y);
						point(c, r);
						break;
					} else if (sensors.get(i).closestBeacon.pos.equals(p)) {
						// System.out.print("B");
						break;
					}
				}
			}
		}
		for (int r = minY; r <= maxY; r++) {
			for (int c = minX; c <= maxX; c++) {
				Point p = new Point(c, r);
				for (int i = 0; i < sensors.size(); i++) {
					if (sensors.get(i).pos.equals(p)) {
//						System.out.print("S");
						break;
					} else if (sensors.get(i).closestBeacon.pos.equals(p)) {
						// System.out.print("B");
						stroke(0, 255, 0);
						point(c, r);
						break;
					}
				}
			}
		}
	}

	private void printCave() {
		for (int r = minY; r <= maxY; r++) {
			for (int c = minX; c <= maxX; c++) {
				Point p = new Point(c, r);
				boolean found = false;
				for (int i = 0; i < sensors.size(); i++) {
					if (sensors.get(i).pos.equals(p)) {
						System.out.print("S");
						found = true;
						break;
					} else if (sensors.get(i).closestBeacon.pos.equals(p)) {
						System.out.print("B");
						found = true;
						break;
					}
				}
				if (!found) {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}
}
