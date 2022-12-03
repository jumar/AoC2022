package com.jumar.aoc.twentytwo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class three {

	public static void main(String[] args) {

		// Job: loading the rucksacks - badly done, need rearangement
		// each sack made of 2 compartments.
		// All items of one type should always be in the same compartment
		// Rule not followed for exactly 1 item type per sack
		//
		// Input: list of all item in each sack
		// Need to find errors
		// Item type a id by a letter upper or lower case
		//
		// One sack content per line
		// Always the same amount of item per compartment
		// First half line -> 1st compartment / 2nd -> 2nd
		//
		// Item priorities a-z : 1-26
		// A-Z : 27-52
		//
		// Find the itme type that got misplaced for each sack
		// Sum of the priorities of those

		// solution
		// id the type shared by both half, add its prio value to an accumulator

		// read line
		// split in 2
		// search for each half matching item in the other half
		// break
		// add item prio to accum

		Path data = Path.of("./data/3.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader(data.toFile()))) {
			String line = reader.readLine();
			int accum = 0;
			while (line != null) {
				var len = line.length();
				String first = line.substring(0, len / 2);
				String sec = line.substring(len / 2);
				System.out.print(first + "/" + sec + "\n");
				boolean match = false;
				for (int i = 0; i < first.length(); i++) {
					char c1 = first.charAt(i);
					for (int j = 0; j < sec.length(); j++) {
						char c2 = sec.charAt(j);
						if (c2 == c1) {
							match = true;
							accum += getPrioFo(c2);
							break;
						}
					}
					if (match)
						break;
				}
				line = reader.readLine();
			}
			System.out.print("Prio sum " + accum);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static int getPrioFo(char c) {
		// (int)'A' -> 65 'Z' -> 90 / 27 - 52
		// (int)'a' -> 97 'z' -> 122 / 1 - 26
		final int AZDelta = -(65 - 27);
		final int azDelta = -(97 - 1);
		final int AZUpperBound = (int) 'Z'; // char above or equal to this limit gets azDelta, below get AZDelta

		int val = (int) c;
		if (val <= AZUpperBound)
			return val + AZDelta;
		return val + azDelta;
	}

	// First try, YAY!!!!
}
