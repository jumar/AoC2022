package com.github.jumar.aoc2022.fourteen;

public class Point {
	public static final int xoffset = 400;

	public Point(String xy) {
		x = Integer.parseInt(xy.split(",")[0]) - xoffset;
		y = Integer.parseInt(xy.split(",")[1]);
	}

	public Point(Point loc) {
		set(loc);
	}

	public int x;
	public int y;

	public void set(Point loc) {
		x = loc.x;
		y = loc.y;
	}

	void moveDown() {
		y++;
	}

	public void moveDownRight() {
		moveDown();
		moveRight();
	}

	public void moveDownLeft() {
		moveDown();
		moveLeft();
	}

	void moveRight() {
		x++;
	}

	void moveLeft() {
		x--;
	}
}
