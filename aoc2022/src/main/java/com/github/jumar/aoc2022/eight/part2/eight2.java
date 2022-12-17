package com.github.jumar.aoc2022.eight.part2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class eight2 {

	public static void main(String[] args) {
		// grid of trees
		// could they build a tree house
		//
		// Need enough tree cover to keep house hidden
		// Count number of trees visible from outside the grid when looking directly
		// along a row or column
		//
		// Height map of trees available
		// A tree is visible if all trees from there to the edge are shorter.
		// Only look up/down/left/right for each tree
		// All trees on the edge are visible -> only look at inner trees and add
		// perimeter to
		// the count
		//
		// q: how many trees are visible from the outside?
		//
		// steps:
		// 1 load grid in table[][]
		// 2 for each tree not in perimeter
		// check [0..c-1] and [c+i..size] -> if no taller tree -> count++
		// check [0..r-1] and [r+i..size] -> if no taller tree -> count++
		// 3 add edge trees to count
		// !! need to use a set to not count trees twice
		//
		// part2
		// Need to find best spot -> where you see a lot of trees
		// Measure viewing dist : dist to tree as high or higher or to edge
		//
		// Scenic score=multiply viewing dist in 4 directions
		// q: what's the tree with the highest scenic score
		//
		// steps
		// 1 load grid
		// 2 for each tree
		// measure viewing dist in each col dir [0..c-1] and [c+i..size]
		// measure viewing dist in each row dir [0..r-1] and [r+i..size]
		// compute score
		// 3 find highest score
		// store Tree Object in the grid

		class Tree {
			private static final int NOSCORE = -1;
			public final int r;
			public final int c;
			public int h;
			private int score = NOSCORE;
			public final List<Integer> viewDists = new ArrayList<>();

			public Tree(int r, int c, int h) {
				this.r = r;
				this.c = c;
				this.h = h;
			}

			public int getScore() {
				if (score == NOSCORE && viewDists.size() == 4) {
					score = viewDists.stream().reduce(1, (a, b) -> a * b);
				}
				return score;
			}

			public String toString() {
				return "[" + r + "," + c + "](" + h + "){" + viewDists.toString() + "}-score:" + getScore();
				// return getScore() >= 0 ? getScore() + "" : h + "";
			}
		}

//		Path data = Path.of("./data/8test.txt");
		Path data = Path.of("./data/8.txt");
		List<String> lines = loadData(data);
		int cols = lines.get(0).length();
		int rows = lines.size();
		Tree[][] grid = new Tree[cols][rows];
		for (int r = 0; r < lines.size(); r++) {
			String rowStr = lines.get(r);
			for (int c = 0; c < rowStr.length(); c++) {
				int treeHeight = Integer.parseInt("" + rowStr.charAt(c));
				grid[r][c] = new Tree(r, c, treeHeight);
			}
		}
		System.out.println(Arrays.deepToString(grid).replace("],", "]\n"));
		for (int r = 1; r < rows - 1; r++) {
			for (int c = 1; c < cols - 1; c++) {
				// we are at each tree here
				if (r == 84 && c == 56) {
					System.out.println();
				}
				Tree tree = grid[r][c];
				int dirViewDist = 0;
				boolean hidden = false;
				// check [c-1..0]
				for (int clow = c - 1; clow >= 0; clow--) {
					if (r == 15 && c == 43)
						System.out.println(grid[r][clow].h + " " + grid[r][c].h);
					if (grid[r][clow].h >= grid[r][c].h) {
						hidden = true;
						dirViewDist = c - clow;
						grid[r][c].viewDists.add(dirViewDist);
						break;
					}
				}
				if (!hidden) {
					grid[r][c].viewDists.add(c);
				}
				hidden = false;
				// check [c+i..size]
				for (int chigh = c + 1; chigh < cols; chigh++) {
					if (grid[r][chigh].h >= grid[r][c].h) {
						hidden = true;
						dirViewDist = chigh - c;
						grid[r][c].viewDists.add(dirViewDist);
						break;
					}
				}
				if (!hidden) {
					grid[r][c].viewDists.add(cols - 1 - c);
				}
				hidden = false;
				// check [r-1..0]
				for (int rl = r - 1; rl > 0; rl--) {
					if (grid[rl][c].h >= grid[r][c].h) {
						hidden = true;
						dirViewDist = r - rl;
						grid[r][c].viewDists.add(dirViewDist);
						break;
					}
				}
				if (!hidden) {
					grid[r][c].viewDists.add(r);
				}
				hidden = false;
				// check [r+i..size]
				for (int rh = r + 1; rh < rows; rh++) {
					if (grid[rh][c].h >= grid[r][c].h) {
						hidden = true;
						dirViewDist = rh - r;
						grid[r][c].viewDists.add(dirViewDist);
						break;
					}
				}
				if (!hidden) {
					grid[r][c].viewDists.add(rows - 1 - r);
				}
				hidden = false;
			}
		}

//		System.out.println("Nb of visible trees: " + (count + visible.size()));
		System.out.println(Arrays.deepToString(grid).replace("],", "]\n"));
		Tree best = new Tree(-1, -1, -1);
		for (int r = 1; r < rows - 1; r++) {
			for (int c = 1; c < cols - 1; c++) {
				Tree tree = grid[r][c];
				if (tree.getScore() > best.getScore()) {
					best = tree;
				}
			}
		}
		System.out.println("Best tree: " + best);
		// 2206176 not good result, we want the highest possible score, not the highest
		// actual score
		// 5764801 center tree @ h=1000 . not goot result, too high Best tree:
		// [84,56](8){[56, 42, 67, 14]}-score:2206176
		// Best tree: [15,43](8){[42, 55, 14, 14]}-score:452760 too low
		// Best tree: [15,43](8){[43, 55, 15, 14]}-score:496650 -> good
	}

	private static List<String> loadData(Path data) {
		// read all rows in a list
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(data.toFile()))) {
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				// next line
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}
}
