package com.jumar.aoc.twentytow.thirteen;

public class Pair<P> {
	public P left;
	public P right;

	public Pair(P lft, P rgt) {
		left = lft;
		right = rgt;
	}

	@Override
	public String toString() {
		return left + "~" + right;
	}
}
