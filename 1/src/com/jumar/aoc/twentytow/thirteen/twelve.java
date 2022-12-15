package com.jumar.aoc.twentytow.thirteen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.jumar.aoc.twentytow.TreeNode;

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

	private static final boolean debug = true;

	public static void main(String[] args) {

		Path data = Path.of("./data/13test.txt");
//		Path data = Path.of("./data/13.txt");
		List<Pair<JsonElement>> pairs = loadData(data);
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
//			var v0 = parseElems(pair.left.line);
//			v0.iterator().forEachRemaining(n -> System.out.println(n));
//			var v1 = parseElems(pair.right.line);
//			v1.iterator().forEachRemaining(n -> System.out.println(n));
			System.out.println("-------------------");
			int compareTo = 0;
			boolean res = true;
			for (int j = 0; j < leftJsonArray.size() && j < rightJsonArray.size(); j++) {
				JsonElement left = leftJsonArray.get(j);
				JsonElement right = rightJsonArray.get(j);
				System.out.println("cmp " + left + "~" + right);
				if (i == 7)
					System.out.println();
				compareTo = compare(left, right);
				if (compareTo <= 0) {
					System.out.println("yes");
					res &= true;
				} else {
					System.out.println("no");
					res &= false;
					break;
				}
			}
//			boolean res = true;
//			if (i == 6)
//				System.out.println();
//			int compareTo = 0;
//			for (int j = 0; j < v0.size() && j < v1.size(); j++) {
//				System.out.println("cmp " + v0.get(j) + "~" + v1.get(j));
//				compareTo = v0.get(j).compareTo(v1.get(j));
//				if (compareTo <= 0) {
//					System.out.println("yes");
//					res &= true;
//				} else {
//					System.out.println("no");
//					res &= false;
//					break;
//				}
//			}
			if (res && leftJsonArray.size() > rightJsonArray.size())
				res = false;
			if (res) {
				System.out.println("--------------------------->    adding " + (i));
				idexList.add(i);
			}
		}
		System.out.println("Added " + idexList);
		Optional<Integer> sum = idexList.stream().reduce((x, y) -> x + y);
		sum.ifPresentOrElse(val -> System.out.println("Sum= " + val), () -> System.out.println("Rien"));
		// 836 too low
	}

	static int compare(JsonElement left, JsonElement right) {
		int compareTo = 0;
		if (left instanceof JsonArray && right instanceof JsonPrimitive) {
			var val = right;
			right = new JsonArray();
			((JsonArray) right).add(val);
		}
		if (left instanceof JsonPrimitive && right instanceof JsonArray) {
			var val = left;
			left = new JsonArray();
			((JsonArray) left).add(val);
		}
		if (left instanceof JsonArray && right instanceof JsonArray) {
//			left=((JsonArray)left).
			compareTo = left.toString().compareTo(right.toString());
			TODO
		}
		if (left instanceof JsonPrimitive && right instanceof JsonPrimitive) {
			compareTo = left.toString().compareTo(right.toString());
		}
		return compareTo;
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
//		// find all [
////		Stack<Integer> openBracketsId = new Stack<>();
//		List<Integer> openBracketsId = new ArrayList<>();
//		for (int i = 0; i < mydata.length(); i++) {
//			char c = mydata.charAt(i);
//			if (c == '[') {
//				openBracketsId.add(i);
//			}
//		}
//		// find all ]
//		Queue<Integer> closedBracketsId = new LinkedList<>();
////		List<Integer> closedBracketsId = new ArrayList<>();
//		for (int i = 0; i < mydata.length(); i++) {
//			char c = mydata.charAt(i);
//			if (c == ']') {
//				closedBracketsId.add(i);
//			}
//		}
//		// start with the most enclosed elem [] (before the first ] down to the closest
//		// [
//		int firstCloseBracket = closedBracketsId.remove();
//		int closestOpenBracket = openBracketsId.get(0);
//		for (int i = 0; i < openBracketsId.size(); i++) {
//			if (openBracketsId.get(i) > firstCloseBracket) {
//				if (i == 0)
//					throw new UnsupportedOperationException("cannot have ] before [");
//				closestOpenBracket = i - 1;
//			}
//		}
//		var deepestElem = mydata.substring(closestOpenBracket + 1, firstCloseBracket);
//		System.out.println("Deepest: " + deepestElem);

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
//					currElem.append(numberBuff.toString());
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
//		return rootTreeElem;
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
