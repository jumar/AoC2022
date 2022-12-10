package com.jumar.aoc.twentytwo.nine;

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

//		Path data = Path.of("./data/9test.txt");
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
		RopeEnd head = new RopeEnd(start);
		RopeEnd tail = new RopeEnd(start);
		updateTrail(board, tail);
		// printBoard(head, tail);
		lines.forEach(mov -> {
			switch (mov.dir) {
			case "U":
				for (int i = 0; i < mov.count; i++) {
					head.moveUp();
					tail.follow(head);
					updateTrail(board, tail);
//					printBoard(head, tail);
				}
				break;
			case "D":
				for (int i = 0; i < mov.count; i++) {
					head.moveDown();
					tail.follow(head);
					updateTrail(board, tail);
//					printBoard(head, tail);
				}
				break;
			case "L":
				for (int i = 0; i < mov.count; i++) {
					head.moveLeft();
					tail.follow(head);
					updateTrail(board, tail);
//					printBoard(head, tail);
				}
				break;
			case "R":
				for (int i = 0; i < mov.count; i++) {
					head.moveRight();
					tail.follow(head);
					updateTrail(board, tail);
//					printBoard(head, tail);
				}
				break;
			}

		});

		// find pos visited by tail
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				if (board[r][c].tailVisitCount > 0)
					count++;
			}
		}
		printBoard(head, tail);
		System.out.println("Nb of visited pos: " + count);

		// This one took some debugging!!
	}

	private static void printBoard(Pos head, Pos tail) {
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				if (board[r][c].equals(tail))
					System.out.print("T");
				else if (new Pos(r, c) == head)
					System.out.print("H");
				else
					System.out.print(board[r][c].tailVisitCount > 0 ? board[r][c].tailVisitCount : ".");
			}
			System.out.println("");
		}
	}

	private static void updateTrail(Pos[][] board, RopeEnd tail) {
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
