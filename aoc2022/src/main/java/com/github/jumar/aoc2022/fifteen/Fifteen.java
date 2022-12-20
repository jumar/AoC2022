package com.github.jumar.aoc2022.fifteen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.jumar.aoc2022.fourteen.CaveTile;

import processing.core.PApplet;
import processing.core.PImage;

public class Fifteen extends PApplet {
	// Defining the image
	PImage img;
	List<List<Point>> lines = new ArrayList<>();
	List<Sensor> sensors = new ArrayList<>();
	public static final int size_w = 400;
	public static final int size_h = 400;
	CaveTile[][] cave = new CaveTile[size_h][size_w];
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	private int minBX;
	private int maxBX;
	private int minBY;
	private int maxBY;
	private boolean debug = false;

	private static int factor = 4;

	public static void main(String[] args) {
		//
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
		Beacon b = new Beacon(new Point(beaconX, beaconY));
		Sensor s = new Sensor(b, new Point(sensorX, sensorY));
		return s;
	}

	@Override
	public void setup() {
		surface.setResizable(true);
		frameRate(1);
		// Set the canvas color
		background(255);
		// Set the pen color
		stroke(0);
		String[] lineArray = loadStrings("data/15.txt");
//		String[] lineArray = loadStrings("data/15test.txt");
		minX = minBX = Integer.MAX_VALUE;
		maxX = maxBX = Integer.MIN_VALUE;
		minY = minBY = Integer.MAX_VALUE;
		maxY = maxBY = Integer.MIN_VALUE;
		for (int i = 0; i < lineArray.length; i++) {
			var s = parseLine(lineArray[i]);
			sensors.add(s);
			int x = Math.min(s.pos.x, s.closestBeacon.pos.x);
			if (x < minX) {
				minX = x;
			}
			int stoBdist = computeDist(s.pos, s.closestBeacon.pos);
			minBX = Math.min(minBX, s.pos.x - stoBdist);

			int xx = Math.max(s.pos.x, s.closestBeacon.pos.x);
			if (xx > maxX) {
				maxX = xx;
			}
			maxBX = Math.max(maxBX, s.pos.x + stoBdist);

			int y = Math.min(s.pos.y, s.closestBeacon.pos.y);
			if (y < minY) {
				minY = y;
			}
			minBY = Math.min(minBY, s.pos.y - stoBdist);

			int yy = Math.max(s.pos.y, s.closestBeacon.pos.y);
			if (yy > maxY) {
				maxY = yy;
			}
			maxBY = Math.max(maxBY, s.pos.y + stoBdist);
		}
		System.out.println("ranges x:[" + minX + " ... " + maxX + "] y:[" + minY + " ... " + maxY + "]");
		System.out.println("ranges minBx:[" + minBX + " ... " + maxBX + "] minBy:[" + minBY + " ... " + maxBY + "]");
//		printCave();
		// for each point P of the line
		// search for Sensors which dist to closest B is < than dist between P and S
		// if found -> cannot be a B here
		int rowOfInterest = 2000000;
//		int rowOfInterest = 10;
		minBY = maxBY = rowOfInterest;
		Map<Integer, Integer> nbPosThatCannotBeBeaconsMap = new HashMap<>();
		Point p = new Point(0, 0);
		// for each point P of the line
		int nbPosThatCannotBeBeacons = 0;
		for (int r = minBY; r <= maxBY; r++) {
			if (debug)
				System.out.print(String.format("%02d ", r));
			for (int c = minBX; c <= maxBX; c++) {
				if (c % 10_000 == 0)
					System.out.println(c);
				p.setLocation(c, r);

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
		super.setup();
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
		var completed = true;
		if (completed) {
			System.out.println("Done");
			super.draw();
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
