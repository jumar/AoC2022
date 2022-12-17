package com.github.jumar.aoc2022.fourteen;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import processing.core.PApplet;
import processing.core.PImage;

public class Fourteen extends PApplet {
	// Defining the image
	PImage img;
	private int depth;
	List<List<Point>> lines = new ArrayList<>();
	List<Sand> sand = new ArrayList<>();
	private Sand currSand;
	CaveTile[][] cave = new CaveTile[400][400];

	public static void main(String[] args) {
		// input: 2D scan of vertical slice of the cave above (air and rock)
		// scan is path of solid rock structure
		// x,y-> x distance to right, y distance down
		// each line in the file is a path
		// path points define start and end of a line
		// Sand starts pouring at 500,0
		//
		// Sand produced one unit at a time. one unit -> one tile.
		// new unit is produce only when previous is at rest
		//
		// Sand falls down until it hits rock (or sand), then goes left diagonally or
		// right if blocked (target tile is rock or sand)
		// if all dirs blocked, it comes to rest.
		//
		PApplet.main(new String[] { "com.github.jumar.aoc2022.fourteen.Fourteen" });
	}

	@Override
	public void settings() {
		// Set the canvas width and height
		size(400, 400);
		super.settings();
	}

	@Override
	public void setup() {
		surface.setResizable(true);
		// Set the canvas color
		background(255);
		// Set the pen color
		stroke(0);
		String[] lines = loadStrings("data/14.txt");
		depth = lines.length;
		for (int i = 0; i < lines.length; i++) {
			println(lines[i]);
			parseLine(lines[i]);
		}
		super.setup();
	}

	private void parseLine(String string) {
		List<Point> line = new ArrayList<>();
		StringTokenizer toc = new StringTokenizer(string, " -> ");

		while (toc.hasMoreElements()) {
			Point p = new Point(toc.nextToken());
			line.add(p);
		}
		lines.add(line);
	}

	void scaleUp(int factor) {
		loadPixels();
		for (int i = width - 1; i >= 0; i--) {
			for (int j = height - 1; j >= 0; j--) {
				pixels[i + j * width] = pixels[(i / factor) + (j / factor) * width];
			}
		}
		updatePixels();
	}

	@Override
	public void draw() {
		drawCave();
		if (sand.isEmpty() || moveSand() == 0)
			generateSand();
		super.draw();
	}

	private int moveSand() {
		currSand.fall();
		return 0; // blocked
	}

	private void generateSand() {
		currSand = new Sand();
		sand.add(currSand);
	}

	private void drawCave() {
		fill(0);
		beginShape(LINES);
		lines.forEach(line -> {
			for (int i = 0; i < line.size() - 1; i++) {
				vertex(line.get(i).x, line.get(i).y);
				vertex(line.get(i + 1).x, line.get(i + 1).y);
			}
		});
		endShape();
	}

}
