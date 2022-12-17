package com.github.jumar.aoc2022.eleven;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Comparators;

public class eleven {

	// predict where monkey will throw iterms
	// 4 monkeys
	// each monkey has:
	// *starting list of items (worry level)
	// *operation: worry level change after monkey inspection
	// *test: Monkey tests your worry level. true/false send to one or the other
	// monkey
	//
	// after inspection but before test, worry level divided by 3 and rounded
	// in a round, Monkeys inspect and throw turn by turn. At each turn inspect and
	// throw all item in order.
	//
	// When reciving an item, it goes to the END of the reciver list
	//
	// q: count the total number of times each monkey inspects items in 20 rounds.
	// compute monkey business level (mul of 2 most active monkeys incpection
	// counts)
	//
	// part 2:
	// no more divide by 3
	// 10000 rounds
	// 8417508198588996433
	// 9223372036854775807
	private static final boolean debug = false;

	public static void main(String[] args) {

//		Path data = Path.of("./data/11test.txt");
		Path data = Path.of("./data/11.txt");
		List<Monkey> monkeys = loadData(data);
		System.out.println(monkeys.toString().replace("],", "]\n"));
		for (int i = 1; i <= 20; i++) {
			monkeys.forEach(m -> {
				int size = m.items.size();
				for (int j = 0; j < size; j++) {
					m.inspect();
					var item = m.items.remove(0);
					BigInteger val = m.operation.apply(item).divide(BigInteger.valueOf(3)); // <--
					if (m.test.test(val)) {
						if (debug)
							System.out.println("Addidn " + val + " to " + m.targetTrue);
						monkeys.get(m.targetTrue).items.add(val);
					} else {
						if (debug)
							System.out.println("Addidn " + val + " to " + m.targetFalse);
						monkeys.get(m.targetFalse).items.add(val);
					}
				}
			});
			if (debug || i == 1 || i == 2 || i == 1000) {
				System.out.println(i + "\n" + monkeys.toString().replace("],", "]\n"));
				monkeys.forEach(m -> System.out.println(m.id + " instpected " + m.count));
			}
			System.out.println(i);
		}
		System.out.println("\n" + monkeys.toString().replace("],", "]\n"));
		monkeys.forEach(m -> System.out.println(m.id + " instpected " + m.count));
		var list = monkeys.stream().map(m -> m.count).collect(Comparators.greatest(2, Integer::compareTo));
		System.out.println(list.get(0) * list.get(1));
	}

	private static List<Monkey> loadData(Path data) {
		// read all rows in a list
		List<Monkey> monkeys = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(data.toFile()))) {
			String line = reader.readLine();
			while (line != null) {
				if (line.startsWith("Monkey")) {
					Monkey monkey = new Monkey(monkeys.size());
					monkeys.add(monkey);
					line = reader.readLine();
					line = line.replace("Starting items:", "").trim();
					String[] split0 = line.split(",");
					for (int i = 0; i < split0.length; i++) {
						monkey.items.add(BigInteger.valueOf(Long.parseLong(split0[i].trim())));
					}
					line = reader.readLine();
					line = line.replace("Operation: new = old ", "").trim();
					var operator = line.split(" ")[0].trim();
					var split = line.split(" ")[1].trim();
					if (operator.equals("+"))
						monkey.operation = (x) -> x.add(BigInteger.valueOf(Integer.parseInt(split)));
					else {
						if (line.split(" ")[1].trim().equals("old"))
							monkey.operation = (x) -> x.multiply(x);
						else
							monkey.operation = (x) -> x.multiply(BigInteger.valueOf(Integer.parseInt(split)));
					}
					line = reader.readLine();
					line = line.replace("Test: divisible by ", "").trim();
					int parseInt = Integer.parseInt(line);
					monkey.test = (x) -> {
						// return x.remainder(BigInteger.valueOf(parseInt)) == BigInteger.valueOf(0);
						var res = x.divideAndRemainder(BigInteger.valueOf(parseInt));
						return res[1] == BigInteger.valueOf(0);
					};
					line = reader.readLine();
					line = line.replace("If true: throw to monkey ", "").trim();
					int targetTrue = Integer.parseInt(line);
					line = reader.readLine();
					line = line.replace("If false: throw to monkey ", "").trim();
					int targetFalse = Integer.parseInt(line);
					monkey.targetTrue = targetTrue;
					monkey.targetFalse = targetFalse;
				}
				// next line
				line = reader.readLine();
			}

		} catch (

		FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return monkeys;
	}

}
