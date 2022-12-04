package com.jumar.aoc.twentytwo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.StringTokenizer;

import com.google.common.collect.Range;

public class four {

	public static void main(String[] args) {
		// cleaning sections of the camp
		// each section has a UID
		// each elf assigned a range of sections
		//
		// assignments overlaps between elves
		// list of assignment for pairs of elves
		//
		// How many pairs has onre range fully covering the other?
		//
		// solution:
		// load range 0
		// load range 1
		// check if range 1 fully contains range 2
		// check opposite
		// one range fully contains the other if its start number is smaller or eq and
		// end number larger or eq

		// part 2
		// How many paors have overlapping range?

		Path data = Path.of("./data/4.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader(data.toFile()))) {
			String line = reader.readLine();
			int enclosedCount = 0;
			int overlapCount = 0;

			while (line != null) {
				StringTokenizer tok = new StringTokenizer(line, ",");
				var one = tok.nextToken();
				var two = tok.nextToken();
				tok = new StringTokenizer(one, "-");
				Range<Integer> elf0 = Range.closed(Integer.parseInt(tok.nextToken()),
						Integer.parseInt(tok.nextToken()));
				tok = new StringTokenizer(two, "-");
				Range<Integer> elf1 = Range.closed(Integer.parseInt(tok.nextToken()),
						Integer.parseInt(tok.nextToken()));
				if (elf0.encloses(elf1) || elf1.encloses(elf0)) {
					enclosedCount++;
					System.out.println(elf0 + " / " + elf1);
				}
				if (elf0.isConnected(elf1))
					overlapCount++;
				line = reader.readLine();
			}
			System.out.println("Nb of pairs with enclosed range " + enclosedCount);
			System.out.println("Nb of pairs with overlapping ranges " + overlapCount);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// First try all the way, yayayayaya !!
	}

}
