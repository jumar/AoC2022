package com.github.jumar.aoc2022;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class six {

	public static void main(String[] args) {
		// malfunctioning communication system
		// need to lock on to the elves signal
		// signal is series of chars one by one
		//
		// need subroutine to detect start of packet
		// seq of 4 different chars is SOP
		//
		// input is datastream. Detect SOP (amount of char between start of buffer and
		// end of SOP) at least 3+4 from start
		//

		Path data = Path.of("./data/6.txt");

		try (BufferedReader reader = new BufferedReader(new FileReader(data.toFile()))) {
			int pos = 0;
			int c = reader.read(); // c is int representation of char
			// Set<Integer> packet = new HashSet<>(); // will not increase its size if
			// already contains the value
			List<Integer> buff = new ArrayList<>();
			List<Integer> packet = new ArrayList<>();
			int size = 0;
			while (c != -1) {
				size = packet.size();
				buff.add(c);
				System.out.println();
				System.out.println(buff);

				if (!packet.contains(c)) {
					packet.add(c);
					System.out.println(packet);
				} else {
					// shift packet to occurence of c + 1
					int index = 0;
					for (int i = 0; i < packet.size(); i++) {
						if (packet.get(i) == c) {
							index = i;
							break;
						}
					}
					packet = packet.subList(index + 1, packet.size());
					packet.add(c);
					System.out.println("updating packet: " + packet);
				}
				if (packet.size() == 4)
					break;
				// next char
				c = reader.read();
				pos++;
			}
			System.out.println("Start of frame pos: " + (pos + 1));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// This one took some debugging!!
	}

	private static void printStack(List<List<String>> stacks) {
		for (int i = 0; i < stacks.size(); i++) {
			String s = i + " " + stacks.get(i).stream().collect(Collectors.joining(" ", "[", "]"));
			System.out.println(s);
		}
		System.out.println();
	}

	private static List<List<String>> moveFromTo(List<List<String>> stacks, int from, int to) {
		var stackFrom = stacks.get(from);
		var stackTo = stacks.get(to);
		stackTo.add(stackFrom.remove(stackFrom.size() - 1));
		return stacks;
	}

}
