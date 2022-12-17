package com.github.jumar.aoc2022.ten;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class ten {

	private static final boolean debug = false;

	public static void main(String[] args) {

//		Path data = Path.of("./data/10test1.txt");
//		Path data = Path.of("./data/10test.txt");
		Path data = Path.of("./data/10.txt");
		int count = 0;
		int cycle = 1;
		List<Op> ops = loadData(data);
		List<Integer> strength = new ArrayList<>();
		Op currOp = null;
		Register r = new Register(1);
		while (!ops.isEmpty()) {
			if (debug)
				System.out.println("Starting cycle:---> " + cycle + " <---X= [" + r.val + "]");
			if (currOp == null)
				currOp = ops.remove(0);
			if (debug)
				System.out.println("currOP: " + currOp);
			// we are during cycle number "cycle"
			if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
				System.out.println("Cycle  :---> " + cycle + " <---X= [" + r.val + "]");
				strength.add(cycle * r.val);
			}
			currOp = currOp.execute(r);
			// we are at end of cycle number "cycle"
			if (debug)
				System.out.println("End of cycle  :---> " + cycle + " <---X= [" + r.val + "]");
			cycle++;
		}
		System.out.println("Sum of strength: " + strength.stream().collect(Collectors.summingInt(x -> x)));
		// 4783 too high
	}

	private static List<Op> loadData(Path data) {
		// read all rows in a list
		List<Op> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(data.toFile()))) {
			String line = reader.readLine();
			while (line != null) {
				StringTokenizer tok = new StringTokenizer(line, " ");
				var opType = enOPType.valueOf(tok.nextToken());
				var op = new Op(opType);
				if (opType.hasParam()) {
					op.param = Integer.parseInt(tok.nextToken());
				}
				lines.add(op);
				// next line
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}

}
