package com.github.jumar.aoc2022.eleven;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Monkey {
	public int id;
	public List<BigInteger> items = new ArrayList<>();
	public Function<BigInteger, BigInteger> operation;
	public Predicate<BigInteger> test;
	public int targetTrue;
	public int targetFalse;
	public int count;
	public int divisor;

	public Monkey(int size) {
		id = size;
	}

	public void inspect() {
		count++;
	}

	@Override
	public String toString() {
		return "[" + id + "](cnt=" + items.size() + ")" + items.toString();
	}
}
