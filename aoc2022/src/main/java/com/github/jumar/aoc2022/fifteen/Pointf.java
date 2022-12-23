package com.github.jumar.aoc2022.fifteen;

public class Pointf {
	public Pointf(float f, float y2) {
		x = f;
		y = y2;
	}

	float x;
	float y;

	public void setLocation(float c, float r) {
		x = c;
		y = r;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pointf p) {
			return nearlyEqual(x, p.x, 0.0001f) && nearlyEqual(y, p.y, 0.0001f);
		}
		return super.equals(obj);
	}

	public static boolean nearlyEqual(float a, float b, float epsilon) {
		final float absA = Math.abs(a);
		final float absB = Math.abs(b);
		final float diff = Math.abs(a - b);

		if (a == b) { // shortcut, handles infinities
			return true;
		} else if (a == 0 || b == 0 || diff < Float.MIN_NORMAL) {
			// a or b is zero or both are extremely close to it
			// relative error is less meaningful here
			return diff < (epsilon * Float.MIN_NORMAL);
		} else { // use relative error
			return diff / (absA + absB) < epsilon;
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[" + x + "," + y + "]";
	}
}
