package com.jumar.aoc.twentytow.thirteen;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Packet implements Comparable<Packet> {
	public String line;
	public List<Elem> elems = new ArrayList<>();

	public Packet(String text) {
		line = text;
//		// remove leading and trailing []
//		line = text.substring(1);
//		line = line.substring(0, line.length() - 1);

	}

	@Override
	public String toString() {
		return line;
	}

	/**
	 * - If both values are integers, the lower integer should come first. If the
	 * left integer is lower than the right integer, the inputs are in the right
	 * order. If the left integer is higher than the right integer, the inputs are
	 * not in the right order. Otherwise, the inputs are the same integer; continue
	 * checking the next part of the input.
	 * 
	 * - If both values are lists, compare the first value of each list, then the
	 * second value, and so on. If the left list runs out of items first, the inputs
	 * are in the right order. If the right list runs out of items first, the inputs
	 * are not in the right order. If the lists are the same length and no
	 * comparison makes a decision about the order, continue checking the next part
	 * of the input.d
	 * 
	 * - If exactly one value is an integer, convert the integer to a list which
	 * contains that integer as its only value, then retry the comparison. For
	 * example, if comparing [0,0,0] and 2, convert the right value to [2] (a list
	 * containing 2); the result is then found by instead comparing [0,0,0] and [2].
	 * 
	 * can factorize by changing all integers to list of single integer!!
	 */
	@Override
	public int compareTo(Packet o) {
		// check if both value int
		// or both are lists
		// or mixed
		isBothInt(this, o);
		return 0;
	}

	private boolean isBothInt(Packet line, Packet other) {
		return isInt(line) && isInt(other);
	}

	private boolean isInt(Packet line) {
		Pattern pattern = Pattern.compile("[(.*?)]");
		Matcher matcher = pattern.matcher(line.line);
		if (matcher.find()) {
			System.out.println(matcher.group(1));
		}
		return false;
	}

//	public static void main(String[] args) {
//		String mydata = "[1,1,3,1,1]";
////		String mydata = "[[1],[2,3,4]]";
//		mydata = mydata.substring(1);
//		mydata = mydata.substring(0, mydata.length() - 1);
//		// replace lone integers by list of 1 integer
//		mydata = mydata.replaceAll("([0-9]+,)", "$1");
////		Pattern pattern = Pattern.compile("([0-9]+,)");
//		Pattern pattern = Pattern.compile("[0-9]+,");
//		Matcher matcher = pattern.matcher(mydata);
////		System.out.println(matcher.group(1)); -> marche pas
//		new Packet(mydata).elems = parseElems(mydata);
//
//		var data = mydata.split(",");
//		System.out.println(Arrays.toString(data));
//		pattern = Pattern.compile("\\[(.*?)\\]"); // Get what's between []
//		matcher = pattern.matcher(mydata);
//		if (matcher.find()) {
//			System.out.println(matcher.group(1));
//		}
//	}

}
