package com.github.jumar.aoc2022.eight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class eight {

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
		//
//		Path data = Path.of("./data/8test.txt");
		Path data = Path.of("./data/8.txt");
		int count = 0;
		List<String> lines = loadData(data);
		int cols = lines.get(0).length();
		int rows = lines.size();
		int[][] grid = new int[cols][rows];
		for (int r = 0; r < lines.size(); r++) {
			String rowStr = lines.get(r);
			for (int c = 0; c < rowStr.length(); c++) {
				int treeHeight = Integer.parseInt("" + rowStr.charAt(c));
				grid[r][c] = treeHeight;
			}
		}
		count += rows * 2 + (cols - 2) * 2;
		System.out.println(Arrays.deepToString(grid).replace("],", "]\n"));
		Set<String> visible = new HashSet<>();
		for (int r = 1; r < rows - 1; r++) {
			for (int c = 1; c < cols - 1; c++) {
				// we are at each tree here
				boolean hidden = false;
				// check [0..c-1]
				for (int clow = 0; clow < c; clow++) {
					if (grid[r][clow] >= grid[r][c]) {
						hidden = true;
						break;
					}
				}
				String tree = "visible: [" + r + "," + c + "](" + grid[r][c] + ")";
				if (!hidden) {
					System.out.println(tree);
					visible.add(tree);
				}
				hidden = false;
				// check [c+i..size]
				for (int chigh = c + 1; chigh < cols; chigh++) {
					if (grid[r][chigh] >= grid[r][c]) {
						hidden = true;
						break;
					}
				}
				if (!hidden) {
					System.out.println(tree);
					visible.add(tree);
				}
				hidden = false;
				// check [0..r-1]
				for (int rl = 0; rl < r; rl++) {
					if (grid[rl][c] >= grid[r][c]) {
						hidden = true;
						break;
					}
				}
				if (!hidden) {
					System.out.println(tree);
					visible.add(tree);
				}
				hidden = false;
				// check [r+i..size]
				for (int rh = r + 1; rh < rows; rh++) {
					if (grid[rh][c] >= grid[r][c]) {
						hidden = true;
						break;
					}
				}
				if (!hidden) {
					System.out.println(tree);
					visible.add(tree);
				}
			}
		}
		System.out.println("Nb of visible trees: " + (count + visible.size()));

		// This one took some debugging!!
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
