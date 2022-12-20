package com.github.jumar.aoc2022.fourteen;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import com.github.jumar.aoc2022.fourteen.CaveTile.enTileType;

import processing.core.PApplet;
import processing.core.PImage;

public class Fourteen extends PApplet {
	// Defining the image
	PImage img;
	List<List<Point>> lines = new ArrayList<>();
	List<Sand> sand = new ArrayList<>();
	private Sand currSand;
	public static final int size_w = 400;
	public static final int size_h = 174;
	CaveTile[][] cave = new CaveTile[size_h][size_w];
	static private Stack<Point> prevFallenFromStack = new Stack<>();

	private static int factor = 4;
	public static int maxDepth;

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
		// q: how many at rest unit of sand when 1st unit falls into the abyss
		//
		// part 2
		// floor at the bottom at y=maxDepth+2
		// q: how many at rest unit of sand when source become blocked
		//
		PApplet.main(new String[] { "com.github.jumar.aoc2022.fourteen.Fourteen" });
	}

	@Override
	public void settings() {
		// Set the canvas width and height
		size(size_w * factor, size_h * factor);
		super.settings();
	}

	@Override
	public void setup() {
		surface.setResizable(true);
		frameRate(5000);
		// Set the canvas color
		background(255);
		// Set the pen color
		stroke(0);
		initCave();
		String[] lineArray = loadStrings("data/14.txt");
		for (int i = 0; i < lineArray.length; i++) {
			parseLine(lineArray[i]);
		}
		List<Point> floor = new ArrayList<Point>();
		floor.add(new Point(0, 173));
		floor.add(new Point(size_w - 1, 173));
		lines.add(floor);
		buildCave();
		System.out.println("max depth = " + maxDepth);
//		printCave();
		super.setup();
	}

	@Override
	public void draw() {
		scale(factor);
		if (sand.isEmpty() || !moveSand()) {
			boolean completed = currSand != null && (currSand.pos.equals(new Sand().pos));
			generateSand();
			System.out.println("sand units" + sand.size());
			if ((sand.size() - 1) % 200 == 0 || completed) {
				drawCave();
				drawSand();
				saveFrame("data/out/14-2/sand-#####.png");
				if (completed) {
					System.out.println("Sand Input blocked. Number or rested sand units=" + (sand.size() - 1));
					super.draw();
				}
			}
		}

//		if (currSand.pos.y > maxDepth) {
//			System.out.println("Max depth reached. Number or rested sand units=" + (sand.size() - 1));
//			// 1002 too high
//			// 1001 ok
//			super.draw();
//		}
//		saveFrame("data/out/14/sand-#####.png");
	}

// 27643 too low
	// 27976
	private void drawSand() {
		prevFallenFromStack.forEach(s -> {
			stroke(155, 0, 255);
			point(s.x, s.y);
		});
		sand.forEach(s -> {
			if (s == currSand) {
				stroke(255, 0, 0);
			} else if (!prevFallenFromStack.isEmpty() && (s.pos == prevFallenFromStack.peek())) {
				stroke(0, 255, 0);
			} else
				stroke(155, 0, 255);
			point(s.pos.x, s.pos.y);
		});
	}

	private void drawCave() {
		stroke(0);
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

	private void initCave() {
		for (int row = 0; row < cave.length; row++) {
			for (int col = 0; col < cave[0].length; col++) {
				cave[row][col] = new CaveTile(enTileType.empty, new Point(row, col));
			}
		}

	}

	private void printCave() {
		for (int row = 0; row < cave.length; row++) {
			for (int col = 0; col < cave[0].length; col++) {
				switch (cave[row][col].type) {
				case rock:
					System.out.print("#");
					break;
				case sand: {
					if (currSand.pos.x == col && currSand.pos.y == row)
						System.out.print("o");
					else
						System.out.print("x");
					break;
				}
				case empty:
					System.out.print(" ");
					break;
				default:
					throw new UnsupportedOperationException();
				}
			}
			System.out.println();
		}
	}

	private void buildCave() {
		for (int i = 0; i < lines.size(); i++) {
			var line = lines.get(i);
			for (int j = 0; j < line.size() - 1; j++) {
				var start = line.get(j);
				var end = line.get(j + 1);
				// update max depth
				if (start.y > maxDepth)
					maxDepth = start.y;
				if (end.y > maxDepth)
					maxDepth = end.y;
				if (start.x == end.x) { // vertical line
					if (start.y < end.y)
						for (int row = start.y; row <= end.y; row++) {
							cave[row][start.x].type = enTileType.rock;
						}
					else
						for (int row = end.y; row <= start.y; row++) {
							cave[row][start.x].type = enTileType.rock;
						}
				} else if (start.y == end.y) {
					// horizontal
					if (start.x < end.x)
						for (int col = start.x; col <= end.x; col++) {
							cave[start.y][col].type = enTileType.rock;
						}
					else
						for (int col = end.x; col <= start.x; col++) {
							cave[start.y][col].type = enTileType.rock;
						}
				} else {
					throw new UnsupportedOperationException();
				}
			}
		}
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

	/**
	 * 
	 * @return true if has fallen. false if at rest.
	 */
	private boolean moveSand() {
		var beforeMovePos = new Point(currSand.pos);
		boolean hasFallen = currSand.fall(cave);
		if (hasFallen) {
			cave[currSand.pos.y][currSand.pos.x].type = enTileType.sand;
			cave[beforeMovePos.y][beforeMovePos.x].type = enTileType.empty;
			if (prevFallenFromStack.isEmpty()
					|| (!prevFallenFromStack.isEmpty() && !prevFallenFromStack.peek().equals(beforeMovePos)))
				prevFallenFromStack.push(beforeMovePos);
		} else {
			if (!prevFallenFromStack.isEmpty()) {
				if (currSand.pos.equals(prevFallenFromStack.peek())) {
					prevFallenFromStack.pop();
				}
			} else
				System.out.println("prevFallenFromStack.isEmpty()");
		}
		return hasFallen;
	}

	private void generateSand() {
		if (prevFallenFromStack.isEmpty())
			currSand = new Sand();
		else
			currSand = new Sand(prevFallenFromStack.peek());
		sand.add(currSand);
		cave[currSand.pos.y][currSand.pos.x].type = enTileType.sand;
	}

}
