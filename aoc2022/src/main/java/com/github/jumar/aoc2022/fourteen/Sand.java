package com.github.jumar.aoc2022.fourteen;

import com.github.jumar.aoc2022.fourteen.CaveTile.enTileType;

public class Sand {
	Point pos = new Point(500 - Point.xoffset, 0);
	Point temp = new Point("0,0");
	public boolean isAtRest;

	public Sand() {
	}

	public Sand(Point pos) {
		this.pos.set(pos);
	}

	@Override
	public String toString() {
		return pos.toString() + (isAtRest ? "R" : "");
	}

	public void moveDown() {
		pos.moveDown();
	}

	public Point tryMoveDown() {
		temp.set(pos);
		temp.moveDown();
		return temp;
	}

	public void moveDownRight() {
		moveDown();
		moveRight();
	}

	public Point tryMoveDownRight() {
		temp.set(pos);
		temp.moveDownRight();
		return temp;
	}

	public void moveDownLeft() {
		moveDown();
		moveLeft();
	}

	public Point tryMoveDownLeft() {
		temp.set(pos);
		temp.moveDownLeft();
		return temp;
	}

	private void moveRight() {
		pos.moveRight();
	}

	public Point trymoveRight() {
		temp.set(pos);
		temp.moveRight();
		return temp;
	}

	private void moveLeft() {
		pos.moveLeft();
	}

	public Point trymoveLef() {
		temp.set(pos);
		temp.moveLeft();
		return temp;
	}

//	public boolean isNeighbor(Sand other) {
//		return Math.abs(other.loc.x - loc.x) < 2 && Math.abs(other.loc.y - loc.y) < 2;
//	}
	/**
	 * 
	 * @param cave
	 * @return true if it falled down. false if it's at rest
	 */
	public boolean fall(CaveTile[][] cave) {
		var down = tryMoveDown();
//		if (down.y >= cave[0].length) {
//			System.out.println("free fall! Yay!!");
//			return true;
//		}
		if (down.y >= Fourteen.maxDepth + 1) {
			System.out.println("reached cave floor");
			isAtRest = true;
		}
		if (cave[down.y][down.x].type == enTileType.empty)
			moveDown();
		else {
			down = tryMoveDownLeft();
			if (cave[down.y][down.x].type == enTileType.empty)
				moveDownLeft();
			else {
				down = tryMoveDownRight();
				if (cave[down.y][down.x].type == enTileType.empty)
					moveDownRight();
				else {// rest
					isAtRest = true;
				}
			}
		}
		return !isAtRest;
	}
}
