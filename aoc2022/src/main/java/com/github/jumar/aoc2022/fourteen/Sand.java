package com.github.jumar.aoc2022.fourteen;

public class Sand {
	Point loc = new Point("500,0");
	Point temp = new Point("0,0");

	public void moveDown() {
		loc.moveDown();
	}

	public Point tryMoveDown() {
		temp.set(loc);
		temp.moveDown();
		return temp;
	}

	public void moveDownRight() {
		moveDown();
		moveRight();
	}

	public Point trymoveDownRight() {
		temp.set(loc);
		temp.moveDownRight();
		return temp;
	}

	public void moveDownLeft() {
		moveDown();
		moveLeft();
	}

	public Point trymoveDownLeft() {
		temp.set(loc);
		temp.moveDownLeft();
		return temp;
	}

	private void moveRight() {
		loc.moveRight();
	}

	public Point trymoveRight() {
		temp.set(loc);
		temp.moveRight();
		return temp;
	}

	private void moveLeft() {
		loc.moveLeft();
	}

	public Point trymoveLef() {
		temp.set(loc);
		temp.moveLeft();
		return temp;
	}
//	public boolean isNeighbor(Sand other) {
//		return Math.abs(other.loc.x - loc.x) < 2 && Math.abs(other.loc.y - loc.y) < 2;
//	}

	public void fall() {
		// TODO Auto-generated method stub

	}
}
