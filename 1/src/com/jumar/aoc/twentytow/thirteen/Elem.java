package com.jumar.aoc.twentytow.thirteen;

import java.util.ArrayList;
import java.util.List;

public class Elem implements Comparable<Elem> {
	StringBuffer buff;
	List<Elem> subElems = new ArrayList<>();

	public Elem() {
		buff = new StringBuffer();
	}

	@Override
	public String toString() {
		return subElems.isEmpty() ? ("[" + buff.toString() + "]") : subElems.toString();
	}

	public void append(String s) {
		buff.append(s);
	}

	public void append(char c) {
		buff.append(c);
	}

	public static class ElemBuilder {
		Elem elem;

		public ElemBuilder create() {
			return new ElemBuilder();
		}

		public Elem open() {
			elem = new Elem();
			return elem;
		}

		public Elem append(String s) {
			elem.append(s);
			return elem;
		}

		public Elem append(char c) {
			elem.append(c);
			return elem;
		}
	}

	public void add(Elem newElem) {
		subElems.add(newElem);
	}

	@Override
	public int compareTo(Elem o) {
		// TODO Auto-generated method stub
		if (subElems.isEmpty() && o.subElems.isEmpty()) { // both are integers
			return buff.toString().compareTo(o.buff.toString());
		}
		if (subElems.isEmpty() && !o.subElems.isEmpty()) { // 1st is integer, 2nd is list
			// change 1st to a list
			return buff.toString().compareTo(o.buff.toString());

		}
		if (!subElems.isEmpty() && o.subElems.isEmpty()) { // 1st is list, 2nd is integer
			// change 2nd to list
			return buff.toString().compareTo(o.buff.toString());
		}
		if (!subElems.isEmpty() && !o.subElems.isEmpty()) { // both are lists
			// here we recurse?
			return buff.toString().compareTo(o.buff.toString());
		}
		throw new UnsupportedOperationException("Boom!");
	}

}
