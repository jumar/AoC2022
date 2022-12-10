package com.jumar.aoc.twentytwo.nine;

public class Movement {
	public final String dir;
	public final Integer count;

	public Movement(String dir, Integer count) {
		this.dir = dir;
		this.count = count;
	}

	public String toString() {
		return dir + " " + count;
	}
}
