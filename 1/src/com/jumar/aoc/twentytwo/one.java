package com.jumar.aoc.twentytwo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class one {
	public static void main(String[] args) {

		// number of calories each elf is carrying
		// one item's calorie per line
		// grouped by who's carrying it
		/*
		 * 1000 2000 3000
		 * 
		 * 4000
		 * 
		 * 5000 6000
		 * 
		 * 7000 8000 9000
		 * 
		 * 10000
		 */
		//

		Path data = Path.of("./data/1.txt");
		List<Integer> calSum = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(data.toFile()))) {
			String line = reader.readLine();
			int sum = 0;
			while (line != null) {
				// System.out.println("line: " + line);
				if (!line.trim().isEmpty()) {
					sum += Integer.parseInt(line);
				} else {
					calSum.add(sum);
					sum = 0;
				}
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(calSum);
		calSum.forEach(val -> System.out.println(val));
		System.out.println("Elf with the most cal has: " + calSum.get(calSum.size() - 1) + " cal");
		System.out.println("Top 3 Elves have a total of: "
				+ (calSum.get(calSum.size() - 1) + calSum.get(calSum.size() - 2) + calSum.get(calSum.size() - 3))
				+ " cal");
	}
}
