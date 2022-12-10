package com.jumar.aoc.twentytwo.nine;

class RopeKnot extends Pos {
	int indexInRope;
	Pos lastPos = new Pos(-1, -1);

	public RopeKnot(int index, int r, int c) {
		super(r, c);
		indexInRope = index;
	}

	public RopeKnot(Pos p) {
		super(p);
	}

	public RopeKnot(int index, Pos p) {
		super(p);
		indexInRope = index;
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

	public void follow(RopeKnot other) {
		if (other.c == c + 2 && other.r == r) {
			c += 1;
		} else if (other.c == c - 2 && other.r == r) {
			c -= 1;
		} else if (other.r == r + 2 && other.c == c) {
			r += 1;
		} else if (other.r == r - 2 && other.c == c) {
			r -= 1;
		} else {
			if (!isNeighbor(other)) {
				if (other.c >= c + 1 && other.r >= r + 1) {
					c += 1;
					r += 1;
				} else if (other.c >= c + 1 && other.r <= r - 1) {
					c += 1;
					r -= 1;
				} else if (other.c <= c - 1 && other.r >= r + 1) {
					c -= 1;
					r += 1;
				} else if (other.c <= c - 1 && other.r <= r - 1) {
					c -= 1;
					r -= 1;
				}
			}
		}

//		if (!isNeighbor(other)) {
//			// take the place the other had
//			lastPos.r = r;
//			lastPos.c = c;
//			r = other.lastPos.r;
//			c = other.lastPos.c;
//		}
		// System.out.println(this);
	}

	@Override
	public String toString() {
//		return "K" + indexInRope + "-" + "[" + r + "," + c + "]";
		return indexInRope + "";
	}
}