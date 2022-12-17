package com.github.jumar.aoc2022;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.StringTokenizer;

public class two2 {

	static enum rpc {
		A(1), B(2), C(3);

		public final int val;

		rpc(int val) {
			this.val = val;
		};

		public int getScoreAgainst(rpc other) {
			switch (this) {
			case A:
				switch (other) {
				case A:
					return 3;
				case B:
					return 0;
				case C:
					return 6;
				default:
					throw new IllegalArgumentException("Unexpected value: " + other);
				}
			case B:
				switch (other) {
				case A:
					return 6;
				case B:
					return 3;
				case C:
					return 0;
				default:
					throw new IllegalArgumentException("Unexpected value: " + other);
				}
			case C:
				switch (other) {
				case A:
					return 0;
				case B:
					return 6;
				case C:
					return 3;
				default:
					throw new IllegalArgumentException("Unexpected value: " + other);
				}
			default:
				throw new IllegalArgumentException("Unexpected value: " + this);

			}
		}
	}

	static enum outcome {
		X(0), Y(3), Z(6);

		public final int val;

		outcome(int val) {
			this.val = val;
		};

		/**
		 * 
		 * @param his his choice
		 * @return my choice to get to the outcome when he chooses "his"
		 */
		public rpc getMineAgainst(rpc his) {
			switch (this) { // this is the outcome
			case X: // 0 - I loose
				switch (his) {
				case A:
					return rpc.C;
				case B:
					return rpc.A;
				case C:
					return rpc.B;
				default:
					throw new IllegalArgumentException("Unexpected value: " + his);
				}
			case Y: // 3 tie
				switch (his) {
				case A:
					return rpc.A;
				case B:
					return rpc.B;
				case C:
					return rpc.C;
				}
			case Z: // 6 I win
				switch (his) {
				case A:
					return rpc.B;
				case B:
					return rpc.C;
				case C:
					return rpc.A;
				}
			default:
				throw new IllegalArgumentException("Unexpected value: " + this);

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
				var outcome_ = outcome.valueOf(tok.nextToken());
				System.out.println("line: " + line + " / outcome=" + outcome_ + "-his=" + his);
				var mine = outcome_.getMineAgainst(his);
				var winScore = outcome_.val;
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
