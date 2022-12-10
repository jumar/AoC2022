package com.jumar.aoc.twentytwo.nine;

class Pos {
	int tailVisitCount;
	int r;
	int c;

	public Pos(int r, int c) {
		this.r = r;
		this.c = c;
	}

	public Pos(Pos p) {
		this(p.r, p.c);
	}

	public void moveUp() {
		r--;
//		System.out.println(this);
	}

	public void moveDown() {
		r++;
//		System.out.println(this);
	}

	public void moveRight() {
		c++;
//		System.out.println(this);
	}

	public void moveLeft() {
		c--;
//		System.out.println(this);
	}

	public boolean isNeighbor(Pos other) {
		// XXX
		// X0X
		// XXX
		return Math.abs(other.r - r) < 2 && Math.abs(other.c - c) < 2;
	}

	public String toString() {
		return "[" + r + "," + c + "]visit(" + tailVisitCount + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pos)
			return (r == ((Pos) obj).r && c == ((Pos) obj).c);
		return false;
	}
}