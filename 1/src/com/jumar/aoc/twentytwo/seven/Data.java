package com.jumar.aoc.twentytwo.seven;

public class Data {
	public String name;
	public int size;
	public static final int UNKNOWN_SIZE = 0;

	public Data(String name, int size) {
		this.name = name;
		this.size = size;
	}

	public Data(String name) {
		this(name, UNKNOWN_SIZE);
	}

	@Override
	public String toString() {
		return name + " {" + size + "}";
	}
}
