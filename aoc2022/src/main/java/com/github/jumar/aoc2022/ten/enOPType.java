package com.github.jumar.aoc2022.ten;

public enum enOPType {
	noop(1), addx(2);

	public final int neededCycles;

	private enOPType(int nbCycles) {
		this.neededCycles = nbCycles;
	}

	public boolean hasParam() {
		return switch (this) {
		case addx: {

			yield true;
		}
		case noop: {
			yield false;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + this);
		};
	}
}
