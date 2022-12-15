package com.jumar.aoc.twentytow.twelve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tile implements Comparable<Tile> {

	static Map<String, Integer> heightMap = new HashMap<>();
	static {
		heightMap.put("S", Integer.valueOf(1));
		heightMap.put("a", Integer.valueOf(1));
		heightMap.put("b", Integer.valueOf(2));
		heightMap.put("c", Integer.valueOf(3));
		heightMap.put("d", Integer.valueOf(4));
		heightMap.put("e", Integer.valueOf(5));
		heightMap.put("f", Integer.valueOf(6));
		heightMap.put("g", Integer.valueOf(7));
		heightMap.put("h", Integer.valueOf(8));
		heightMap.put("i", Integer.valueOf(9));
		heightMap.put("j", Integer.valueOf(10));
		heightMap.put("k", Integer.valueOf(11));
		heightMap.put("l", Integer.valueOf(12));
		heightMap.put("m", Integer.valueOf(13));
		heightMap.put("n", Integer.valueOf(14));
		heightMap.put("o", Integer.valueOf(15));
		heightMap.put("p", Integer.valueOf(16));
		heightMap.put("q", Integer.valueOf(17));
		heightMap.put("r", Integer.valueOf(18));
		heightMap.put("s", Integer.valueOf(19));
		heightMap.put("t", Integer.valueOf(20));
		heightMap.put("u", Integer.valueOf(21));
		heightMap.put("v", Integer.valueOf(22));
		heightMap.put("w", Integer.valueOf(23));
		heightMap.put("x", Integer.valueOf(24));
		heightMap.put("y", Integer.valueOf(25));
		heightMap.put("z", Integer.valueOf(26));
		heightMap.put("E", Integer.valueOf(26));
	}

	public int c;
	public int r;
	public final int h;
	public final String hs;
	public boolean isStart;
	public boolean isTarget;
	public List<Tile> shortestPath = new LinkedList<>();

	Integer distance = Integer.MAX_VALUE;

	public Tile(String s, int r2, int c2) {
		hs = s;
		h = heightMap.get(s);
		if (s.equals("S")) {
			isStart = true;
		} else if (s.equals("E")) {
			isTarget = true;
		}
		r = r2;
		c = c2;
	}

	private boolean isTooSteep(Tile t) {
		return Math.abs(t.h - h) > 1;
	}

	private boolean isReachable(Tile t) {
		return (t.h == h + 1) || t.h == h || t.h < h;
	}

	public boolean canGoto(Tile t) {
		return t.isNeighbor(this) && isReachable(t);// !isTooSteep(t);
	}

	public List<Tile> getAvailableDirections(Tile[][] terrain) {
		List<Tile> dirs = getNeighbors(terrain).stream().filter(t -> canGoto(t)).collect(Collectors.toList());
		return dirs;
	}

	public List<Tile> getNeighbors(Tile[][] terrain) {
		List<Tile> n = new ArrayList<>();
		Tile left = c > 0 ? terrain[r][c - 1] : null;
		Tile right = c < terrain[0].length - 1 ? terrain[r][c + 1] : null;
		Tile up = r > 0 ? terrain[r - 1][c] : null;
		Tile down = r < terrain.length - 1 ? terrain[r + 1][c] : null;
		// always favor right
		if (right != null)
			n.add(right);
		// then up
		if (up != null)
			n.add(up);
		// then left
		if (left != null)
			n.add(left);
		// then down
		if (down != null)
			n.add(down);
		return n;
	}

	@Override
	public String toString() {
//		return hs + "(" + String.format("%02d", h) + ")";
		return hs + "{" + String.format("%02d", r) + ";" + String.format("%02d", c) + "}";
//		return hs + "(" + String.format("%02d", h) + ")[" + String.format("%02d", r) + "," + String.format("%02d", c)
//				+ "]";
	}

	public String toLongString() {
		return hs + "{" + String.format("%02d", r) + ";" + String.format("%02d", c) + "}-d=" + distance
				+ "-shortestPath:" + shortestPath;
	}

	public boolean isNeighbor(Tile other) {
		return (other.r == r && (Math.abs(other.c - c) == 1)) || (other.c == c && (Math.abs(other.r - r) == 1));
	}

	public int getDistance() {
		return distance;
	}

	@Override
	public int compareTo(Tile o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
