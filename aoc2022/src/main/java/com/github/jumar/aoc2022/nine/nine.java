package com.github.jumar.aoc2022.nine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class nine {

	private static final int SIZE = 500;
	private static final boolean debug = false;
	private static Pos[][] board;

	public static void main(String[] args) {
		// Rope with knot at head and tail
		// If you pull head long enough tail will move forward
		//
		// model the pos of the knots on a 2d grid
		// input: series of motions for the head -> compute motion of tail
		//
		// H and T must be touching (adjacent, diagonally, overlapping)
		// If H after its move is still in square around T, T does not move, otherwise T
		// takes the former place of H
		//
		// Q: how many pos T visited at least once?
		//
		// steps:
		// 0 load data
		// 1 have a Pos object for the grid pos
		// 2 have a isNeighbor() method
		// 3 have a prevTail pos
		// 4 inc counter in Pos each time T enters it
		// 5 create a big-ass board and start at the center
		// 6 at the end of each move plus first location, update visited Pos

		// part 2 -> extend to a rope with 10 knots

//		Path data = Path.of("./data/9test.txt");
//		Path data = Path.of("./data/9test1.txt");
		Path data = Path.of("./data/9.txt");
		int count = 0;
		List<Movement> lines = loadData(data);
		System.out.println("movements: " + lines);
		// create big ass board
		board = new Pos[SIZE][SIZE];
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				board[r][c] = new Pos(r, c);
			}
		}
		Pos start = new Pos(SIZE / 2, SIZE / 2);
		// create rope
		List<RopeKnot> rope = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			rope.add(new RopeKnot(i, start));
		}
		RopeKnot head = rope.get(0);
		RopeKnot tail = rope.get(rope.size() - 1);
		updateTrail(board, tail);
		printBoard(rope);
		lines.forEach(mov -> {
			if (debug)
				System.out.println(mov.dir + " " + mov.count);
			switch (mov.dir) {
			case "U":
				for (int i = 0; i < mov.count; i++) {
					head.moveUp();
					if (debug)
						printBoard(rope);
					followHead(rope);
					updateTrail(board, tail);
					if (debug)
						printBoard(rope);
				}
				break;
			case "D":
				for (int i = 0; i < mov.count; i++) {
					head.moveDown();
					if (debug)
						printBoard(rope);
					followHead(rope);
					updateTrail(board, tail);
					if (debug)
						printBoard(rope);
				}
				break;
			case "L":
				for (int i = 0; i < mov.count; i++) {
					head.moveLeft();
					if (debug)
						printBoard(rope);
					followHead(rope);
					updateTrail(board, tail);
					if (debug)
						printBoard(rope);
				}
				break;
			case "R":
				for (int i = 0; i < mov.count; i++) {
					head.moveRight();
					if (debug)
						printBoard(rope);
					followHead(rope);
					updateTrail(board, tail);
					if (debug)
						printBoard(rope);
				}
				break;
			}
			if (debug)
				printBoard(rope);
		});

		// find pos visited by tail
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				if (board[r][c].tailVisitCount > 0)
					count++;
			}
		}
		printBoard(rope);
		System.out.println("Nb of visited pos: " + count);
		// 4783 too high
	}

	private static void followHead(List<RopeKnot> rope) {
		for (int j = 1; j < rope.size(); j++) {
			rope.get(j).follow(rope.get(j - 1));
		}
	}

	private static void printBoard(List<RopeKnot> rope) {
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				boolean ok = false;
				for (int k = 0; k < rope.size(); k++) {
					RopeKnot knot = rope.get(k);
					if (knot.r == r && knot.c == c) {
						if (!ok) {
							System.out.print(knot);
							ok = true;
						}
					}
				}
				if (!ok)
					System.out.print(board[r][c].tailVisitCount > 0 ? "#" : ".");
			}
			System.out.println("");
		}
		System.out.println("");
	}

	private static void updateTrail(Pos[][] board, RopeKnot tail) {
		board[tail.r][tail.c].tailVisitCount++;
	}

	private static List<Movement> loadData(Path data) {
		// read all rows in a list
		List<Movement> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(data.toFile()))) {
			String line = reader.readLine();
			while (line != null) {
				StringTokenizer tok = new StringTokenizer(line, " ");
				lines.add(new Movement(tok.nextToken(), Integer.parseInt(tok.nextToken())));
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
