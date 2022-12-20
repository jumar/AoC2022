package com.github.jumar.aoc2022.fourteen;

public class Point {
	public static final int xoffset = 325;
//	public static final int xoffset = 0;
	public int x;
	public int y;

	public Point(String xy) {
		x = Integer.parseInt(xy.split(",")[0]) - xoffset;
		y = Integer.parseInt(xy.split(",")[1]);
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point loc) {
		set(loc);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point p)
			return x == p.x && y == p.y;
		return false;
	}

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

	@Override
	public String toString() {

		return x + "," + y;
	}
}
