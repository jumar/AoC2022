package com.jumar.aoc.twentytow.thirteen;

public class Elem implements Comparable<Elem> {
	StringBuffer buff;

	public Elem() {
		buff = new StringBuffer();
	}

	@Override
	public String toString() {
		return "[" + buff.toString() + "]";
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

	@Override
	public int compareTo(Elem o) {
		int cmp = buff.toString().compareTo(o.buff.toString());
		if (cmp <= 0 && buff.toString().length() > o.buff.toString().length())
			return -10_000;
		return cmp;
//		// TODO Auto-generated method stub
//		if (subElems.isEmpty() && o.subElems.isEmpty()) { // both are integers
//			return buff.toString().compareTo(o.buff.toString());
//		}
//		if (subElems.isEmpty() && !o.subElems.isEmpty()) { // 1st is integer, 2nd is list
//			// change 1st to a list
//			return buff.toString().compareTo(o.buff.toString());
//		}
//		if (!subElems.isEmpty() && o.subElems.isEmpty()) { // 1st is list, 2nd is integer
//			// change 2nd to list
//			return buff.toString().compareTo(o.buff.toString());
//		}
//		if (!subElems.isEmpty() && !o.subElems.isEmpty()) { // both are lists
//			// here we recurse?
//			return buff.toString().compareTo(o.buff.toString());
//		}
//		throw new UnsupportedOperationException("Boom!");
	}
}
