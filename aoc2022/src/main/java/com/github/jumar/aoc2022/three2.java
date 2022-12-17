package com.github.jumar.aoc2022;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class three2 {

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

		// part 2
		// elves in group or 3.
		// all elves in a group carries only one item type
		// group badge is item type carried by all elves
		// 1 or 2 elves may be carrying another type additionally
		//
		// All badges need to be pulled out of sacks to add authenticity sticker
		// Don't know which badge is for which item -> need to find common item for all
		// 3 elves to get badge->item link
		// Every 3 line is a group
		//
		// What is the sum of priorities of badge items

		// solution:
		// read sack content for group
		// search for common item
		// accum its prio

		Path data = Path.of("./data/3.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader(data.toFile()))) {
			String line = reader.readLine();
			int accum = 0;
			int count = 0;

			Map<Integer, String> elfToSackMap = new HashMap<>();
			while (line != null) {
				int elfId = count++ % 3;
				elfToSackMap.put(elfId, line);
				line = reader.readLine();
				if (elfId == 2) {
					// we have a complete group
					String elf1 = elfToSackMap.get(0);
					String elf2 = elfToSackMap.get(1);
					String elf3 = elfToSackMap.get(2);
					System.out.print(elf1 + "\n" + elf2 + "\n" + elf3 + "\n\n");
					boolean match = false;
					for (int i = 0; i < elf1.length(); i++) {
						char c1 = elf1.charAt(i);
						for (int j = 0; j < elf2.length(); j++) {
							char c2 = elf2.charAt(j);
							if (c2 == c1) {
								for (int k = 0; k < elf3.length(); k++) {
									char c3 = elf3.charAt(k);
									if (c2 == c3) {
										match = true;
										accum += getPrioFo(c2);
										break;
									}
								}
							}
							if (match)
								break;
						}
						if (match)
							break;
					}
				}
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

	// First try again, YAY!!!!
}
