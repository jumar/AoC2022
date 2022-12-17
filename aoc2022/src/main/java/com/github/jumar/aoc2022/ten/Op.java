package com.github.jumar.aoc2022.ten;

public class Op {
	enOPType opType;
	public int param;
	public int ranCycles;

	public Op(enOPType opType2) {
		opType = opType2;
	}

	public Op execute(Register r) {
		return switch (opType) {
		case noop: {
			ranCycles++;
			if (opType.neededCycles == ranCycles)
				yield null;
			else
				yield this;
		}
		case addx: {
			ranCycles++;
			if (opType.neededCycles == ranCycles) {
				r.val += param;
				yield null;
			} else
				yield this;
		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + this);
		};
	}

	@Override
	public String toString() {
		return opType + " " + (opType.hasParam() ? param : "");
	}
}
