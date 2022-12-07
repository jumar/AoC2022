package com.jumar.aoc.twentytwo.seven;

public class Data {
	public String aName;
	public int aSize;
	public static final int UNKNOWN_SIZE = -1;

	public Data(String name, int size) {
		aName = name;
		aSize = size;
	}

	public Data(String name) {
		this(name, UNKNOWN_SIZE);
	}

	@Override
	public String toString() {
		return aName + " {" + aSize + "}";
	}
}
