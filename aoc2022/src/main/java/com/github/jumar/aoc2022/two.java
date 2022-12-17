package com.github.jumar.aoc2022;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.StringTokenizer;

public class two {

	static enum rpc {
		X(1), Y(2), Z(3), A(1), B(2), C(3),;

		public final int val;

		rpc(int val) {
			this.val = val;
		};

		public int against(rpc other) {
			switch (this) {
			case A:
			case X:
				switch (other) {
				case A:
				case X:
					return 3;
				case B:
				case Y:
					return 0;
				default:
					System.out.println("ouch!");
				case C:
				case Z:
					return 6;
				}
			case Y:
			case B:
				switch (other) {
				case A:
				case X:
					return 6;
				case B:
				case Y:
					return 3;
				default:
					System.out.println("ouch!");
				case C:
				case Z:
					return 0;
				}
			default:
				System.out.println("ouch!");
			case C:
			case Z:
				switch (other) {
				case A:
				case X:
					return 0;
				case B:
				case Y:
					return 6;
				default:
					System.out.println("ouch!");
				case C:
				case Z:
					return 3;
				}
			}
		}
	}

	public static void main(String[] args) {
		// Rock paper scissors game
		// 2 players, multi rounds
		// R>S, S>P, P>R or tie
		// encrypted strat guide to help win
		// first column opponent answer
		// A->R
		// B->P
		// C->C
		// Second column ... Mine?
		// X->R
		// Y->P
		// Z->C

		// round socre is
		// R is 1 pt
		// P is 2 pts
		// C is 3 pts
		// +
		// 0 pts for loss
		// 3 pts draw
		// 6 pts win
		Path data = Path.of("./data/2.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader(data.toFile()))) {
			String line = reader.readLine();
			int score = 0;
			while (line != null) {
				StringTokenizer tok = new StringTokenizer(line, " ");
				var his = rpc.valueOf(tok.nextToken());
				var mine = rpc.valueOf(tok.nextToken());
				System.out.println("line: " + line + " / mine=" + mine + "-his=" + his);
				var winScore = mine.against(his);
				score += winScore + mine.val;
				line = reader.readLine();
			}
			System.out.print("My score would be " + score);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
