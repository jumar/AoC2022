package com.jumar.aoc.twentytwo.nine;

class RopeEnd extends Pos {
	Pos lastPos = new Pos(-1, -1);

	public RopeEnd(int r, int c) {
		super(r, c);
	}

	public RopeEnd(Pos p) {
		super(p);
	}

	private void updateLastPos() {
		lastPos.r = r;
		lastPos.c = c;
	}

	public void moveUp() {
		updateLastPos();
		super.moveUp();
	}

	public void moveDown() {
		updateLastPos();
		super.moveDown();
	}

	public void moveRight() {
		updateLastPos();
		super.moveRight();
	}

	public void moveLeft() {
		updateLastPos();
		super.moveLeft();
	}

	public void follow(RopeEnd other) {
		if (!isNeighbor(other)) {
			// take the place the other had
			r = other.lastPos.r;
			c = other.lastPos.c;
		}
		System.out.println("T:" + this);
	}
}