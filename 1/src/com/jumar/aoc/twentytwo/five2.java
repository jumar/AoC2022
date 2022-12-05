package com.jumar.aoc.twentytwo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class five2 {

	public static void main(String[] args) {
		// need to rearrancge dcrates stacks
		// Desired crates at the top of the stacks
		//
		// drawing of the starting stacks
		// rearrangement procedure
		//
		// crates are moved one at a time
		//
		// q: which crate will end up on top of each stack?
		//
		// solution:
		// create the stacks and fill them with their init state
		// in loop per line
		// move 6 from 2 to 1 -> call 6 times the movefromto() method
		// print top of stacks

		// part 2
		// multiple crates at once can be moved
		// moving 1 crate -> same as before
		// moving more -> they keep their order

		Path initdata = Path.of("./data/5-init.txt");

		String[] st1arr = new String[] { "N", "Q", "L", "S", "C", "Z", "P", "T" };
		String[] st2arr = new String[] { "G", "C", "H", "V", "T", "P", "L" };
		String[] st3arr = new String[] { "F", "Z", "C", "D" };
		String[] st4arr = new String[] { "C", "V", "M", "L", "D", "T", "W", "G" };
		String[] st5arr = new String[] { "C", "W", "P" };
		String[] st6arr = new String[] { "Z", "S", "T", "C", "D", "J", "F", "P" };
		String[] st7arr = new String[] { "D", "B", "G", "W", "V" };
		String[] st8arr = new String[] { "W", "H", "Q", "S", "J", "N" };
		String[] st9arr = new String[] { "V", "L", "S", "F", "Q", "C", "R" };

		List<ArrayList<String>> stacks = new ArrayList<>();
		ArrayList<String> l1 = new ArrayList<>(Arrays.asList(st1arr));
		ArrayList<String> l2 = new ArrayList<>(Arrays.asList(st2arr));
		ArrayList<String> l3 = new ArrayList<>(Arrays.asList(st3arr));
		ArrayList<String> l4 = new ArrayList<>(Arrays.asList(st4arr));
		ArrayList<String> l5 = new ArrayList<>(Arrays.asList(st5arr));
		ArrayList<String> l6 = new ArrayList<>(Arrays.asList(st6arr));
		ArrayList<String> l7 = new ArrayList<>(Arrays.asList(st7arr));
		ArrayList<String> l8 = new ArrayList<>(Arrays.asList(st8arr));
		ArrayList<String> l9 = new ArrayList<>(Arrays.asList(st9arr));
		stacks.add(l1);
		stacks.add(l2);
		stacks.add(l3);
		stacks.add(l4);
		stacks.add(l5);
		stacks.add(l6);
		stacks.add(l7);
		stacks.add(l8);
		stacks.add(l9);
		Collections.reverse(l9);
		Collections.reverse(l8);
		Collections.reverse(l7);
		Collections.reverse(l6);
		Collections.reverse(l5);
		Collections.reverse(l4);
		Collections.reverse(l3);
		Collections.reverse(l2);
		Collections.reverse(l1);
		printStack(stacks);
		Path data = Path.of("./data/5.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader(data.toFile()))) {
			String line = reader.readLine();

			while (line != null) {
				// parse line
				StringTokenizer tok = new StringTokenizer(line, " ");
				tok.nextToken(); // move
				var countToMove = Integer.parseInt(tok.nextToken());
				tok.nextToken(); // from
				var from = Integer.parseInt(tok.nextToken()) - 1;
				tok.nextToken(); // to
				var to = Integer.parseInt(tok.nextToken()) - 1;
				// move requested crates one at a time

				stacks = moveFromTo(stacks, countToMove, from, to);
				printStack(stacks);
				// next line
				line = reader.readLine();
			}
			String res = stacks.stream().map(st -> st.get(st.size() - 1)).collect(Collectors.joining());
			// .collect(Collectors.joining("")));
			System.out.println("Stacks tops: " + res);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// This one took some debugging!!
	}

	private static List<ArrayList<String>> moveFromTo(List<ArrayList<String>> stacks, int countToMove, int from,
			int to) {
		var stackFrom = stacks.get(from);
		var stackTo = stacks.get(to);
		if (countToMove == 1) {
			stackTo.add(stackFrom.remove(stackFrom.size() - 1));
		} else {
			var batch = stackFrom.subList(stackFrom.size() - countToMove, stackFrom.size());
			stackTo.addAll(batch);
			for (int i = 0; i < countToMove; i++) {
				stackFrom.remove(stackFrom.size() - 1);
			}
		}
		return stacks;
	}

	private static void printStack(List<ArrayList<String>> stacks) {
		for (int i = 0; i < stacks.size(); i++) {
			String s = i + " " + stacks.get(i).stream().collect(Collectors.joining(" ", "[", "]"));
			System.out.println(s);
		}
		System.out.println();
	}
}
