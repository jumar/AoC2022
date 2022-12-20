package com.github.jumar.aoc2022.fourteen;

public class CaveTile {
	public static enum enTileType {
		empty, rock, sand;
	}

	enTileType type = enTileType.empty;
	public Point pos;

	public CaveTile(enTileType t, Point p) {
		type = t;
		pos = p;
	}

	public boolean isVoid() {
		return type == enTileType.empty;
	}
}
