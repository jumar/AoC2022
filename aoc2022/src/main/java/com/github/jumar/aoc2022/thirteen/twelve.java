package com.github.jumar.aoc2022.thirteen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import com.github.jumar.aoc2022.TreeNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class twelve {

	// received distress signal
	// packet decoded out of order
	//
	// input: list of packets
	// pair of packets
	// Need to identify how many pairs are in the right order
	//
	// q: what's the indices of the pairs that are in the right order
	// 1st pair is index 1
	// -> sum of the indices

	// part 2
	// need to put packets in right order
	// need to inser the 2 divider packets [[2]] , [[6]] at the right spot
	// q: what is the idx of the 2 divider packets multiplied together
	//
	private static final boolean debug = false;

	public static void main(String[] args) {

//		Path data = Path.of("./data/13test.txt");
		Path data = Path.of("./data/13.txt");
		List<Pair<JsonElement>> pairs = loadData(data);
//		part1(pairs);
		part2(pairs);
	}

	private static void part2(List<Pair<JsonElement>> pairs) {
		// need to sort all the packets and insert the two
		// 1 sort
		// break up pairs
		List<JsonElement> packets = new ArrayList<>();
		pairs.stream().forEach(p -> {
			packets.add(p.left);
			packets.add(p.right);
		});
		JsonArray two = new JsonArray();
		JsonArray inner = new JsonArray();
		inner.add(2);
		two.add(inner);
		packets.add(two);
		JsonArray six = new JsonArray();
		inner = new JsonArray();
		inner.add(6);
		six.add(inner);
		packets.add(six);
		Collections.sort(packets, new Comparator<JsonElement>() {
			@Override
			public int compare(JsonElement o1, JsonElement o2) {
				// TODO Auto-generated method stub
				return compareJson(o1, o2);
			}

		});
		packets.forEach(p -> {
			System.out.println(p);
		});
		System.out.println("decoder key: " + (packets.indexOf(two) + 1) * (packets.indexOf(six) + 1));
		// 2 insert
	}

	private static void part1(List<Pair<JsonElement>> pairs) {
		List<Integer> idexList = new ArrayList<>();
		for (int i = 1; i <= pairs.size(); i++) {
			Pair<JsonElement> pair = pairs.get(i - 1);
			if (debug)
				System.out.println(pair);
			System.out.println("-------------------");
			JsonArray leftJsonArray = pair.left.getAsJsonArray();
			System.out.println(leftJsonArray.asList());
			JsonArray rightJsonArray = pair.right.getAsJsonArray();
			System.out.println(rightJsonArray.asList());
			System.out.println("-------------------");
			if (compareJson(leftJsonArray, rightJsonArray) <= 0) {
				System.out.println("--------------------------->    adding " + (i));
				idexList.add(i);
			}
		}
		System.out.println("Added " + idexList);
		Optional<Integer> sum = idexList.stream().reduce((x, y) -> x + y);
		sum.ifPresentOrElse(val -> System.out.println("Sum= " + val), () -> System.out.println("Rien"));
		// 836 too low
		// 1517 too low
		// 2572 too low
		// 13063 nok
		// 5773 no
		// 6235 ok
	}

	/**
	 * 0: if (left==right) -1: if (left < right) 1: if (leftx > righty)
	 */
	static int compareJson(JsonElement left, JsonElement right) { // -1 if left smaller -> right order
		if (debug)
			System.out.println("cmp " + left + "~" + right);

		int compareTo = 0;
		if (left instanceof JsonArray && right instanceof JsonPrimitive) {
			var val = right;
			right = new JsonArray();
			((JsonArray) right).add(val);
			return compareJson(left, right);
		}
		if (left instanceof JsonPrimitive && right instanceof JsonArray) {
			var val = left;
			left = new JsonArray();
			((JsonArray) left).add(val);
			return compareJson(left, right);
		}
		if (left instanceof JsonArray && right instanceof JsonArray) {
			if (((JsonArray) left).isEmpty() && ((JsonArray) right).isEmpty())
				return 0;
			for (int i = 0; i < ((JsonArray) left).size() && i < ((JsonArray) right).size(); i++) {
				compareTo = compareJson(((JsonArray) left).get(i), ((JsonArray) right).get(i));
				if (compareTo != 0) {// we break
					if (debug)
						System.out.println(compareTo > 0 ? "no" : "yes");
					return compareTo;
				}
			}
			if (compareTo == 0) { // special rule
				if ((((JsonArray) left).size() < ((JsonArray) right).size())) { // left side ran out first
					compareTo = -1; // right order
					if (debug)
						System.out.println("yes - left side ran out first");
					return compareTo;
				} else if ((((JsonArray) left).size() > ((JsonArray) right).size())) { // right side ran out first
					compareTo = 1; // wrong order
					if (debug)
						System.out.println("no - right side ran out first");
					return compareTo;
				} else {
					// same size
					return 0;
				}
			}

		}
		if (left instanceof JsonPrimitive && right instanceof JsonPrimitive) {
			compareTo = left.getAsInt() - right.getAsInt();
			if (debug)
				System.out.println(compareTo > 0 ? "no" : (compareTo < 0 ? "yes" : "continue"));
			return compareTo;
		}

		throw new UnsupportedOperationException("ooops");
	}

	private static List<Pair<JsonElement>> loadData(Path data) {
		// read all Pairs in a list
		List<Pair<JsonElement>> pairs = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(data.toFile()))) {
			String text = reader.readLine();
			while (text != null) {
				JsonElement l = JsonParser.parseString(text);
				// next line
				text = reader.readLine();
				JsonElement r = JsonParser.parseString(text);
				Pair<JsonElement> p = new Pair<>(l, r);
				pairs.add(p);
				text = reader.readLine();
				if (text == null)
					break;
				text = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pairs;
	}

	public static List<Elem> parseElems(String mydata) {
		List<Elem> elems = new ArrayList<>();

		// Elems are StringBuffer
		// we read the string char by char
		// when you find a [ you prepare to create an elem, need to know the 1st
		// subsequent char, if int -> continue to next "," or "[", or "]" append chars
		// when you find a number you append it to the elem
		// when you fine a [ you create another elem
		// when you find a , you append to current elem
		Stack<Elem> stack = new Stack<>();
		Elem currElem = null;
		TreeNode<Elem> rootTreeElem = new TreeNode<Elem>(new Elem());
		TreeNode<Elem> newTreeElem = null;
		TreeNode<Elem> currTreeElem = rootTreeElem;
		Integer rawVal = 0;
		char ascii = '~';
		StringBuffer numberBuff = new StringBuffer();
		for (int i = 0; i < mydata.length(); i++) {
			char c = mydata.charAt(i);
			rawVal = getDigit(c);
			if (rawVal >= 0) {
				// it's a digit
				numberBuff.append(c);
			} else {
				// not an int
				if (!numberBuff.isEmpty()) {
					// we have a complete number in buff
					rawVal = Integer.parseInt(numberBuff.toString());
					if (rawVal > 25)
						throw new UnsupportedOperationException("too big");
					ascii = (char) (rawVal + 65);
					currElem.append(ascii);
					// start new number
					numberBuff = new StringBuffer();
				}
				if (c == '[') {
					// open elem
					Elem newElem = new Elem();
					currTreeElem = currTreeElem.addChild(newElem);
					stack.add(currElem);
					currElem = newElem;
				} else if (c == ',') {
					// c'est chill on reste dans le meme elem
					// on va lui ajoutter des chars
				} else if (c == ']') {
					// close elem
					if (!stack.isEmpty()) {
						elems.add(currElem);
						currElem = stack.pop();
						currTreeElem = currTreeElem.parent;
					} else if (currElem != null)
						elems.add(currElem);
				}
			}

		}
		return elems;
	}

	private static Integer getDigit(char c) {
		Integer rawVal = -1;
		try {
			rawVal = Integer.parseInt(c + "");
		} catch (NumberFormatException e) {
			// not an int
		}
		return rawVal;
	}

}
