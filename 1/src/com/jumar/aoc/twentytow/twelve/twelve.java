package com.jumar.aoc.twentytow.twelve;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class twelve {

	// input: heightmap of suroundings
	// a lowest z highest
	// S current spot (a)
	// E best signal spot (z)
	//
	// need to go to E in as few steps as possible
	// step=1 square LRUP at most 1 higher but no limit down
	//
	//
	private static final boolean debug = true;

	public static void main(String[] args) {

		Path data = Path.of("./data/12test.txt");
//		Path data = Path.of("./data/12.txt");
		Tile[][] terrain = loadData(data);
		// find planer and target tiles
		Tile start = null;
		Tile target = null;
		for (int r = 0; r < terrain.length; r++) {
			for (int c = 0; c < terrain[0].length; c++) {
				Tile tile = terrain[r][c];
				if (tile.isStart)
					start = tile;
				else if (tile.isTarget)
					target = tile;
			}
		}

		if (debug)
			printTerrain(terrain, start);
		Player p = new Player(start);
		p.findPathTo(target, terrain);

//		System.out.println(monkeys.toString().replace("],", "]\n"));
//		for (int i = 1; i <= 20; i++) {
//			monkeys.forEach(m -> {
//				int size = m.items.size();
//				for (int j = 0; j < size; j++) {
//					m.inspect();
//					var item = m.items.remove(0);
//					BigInteger val = m.operation.apply(item).divide(BigInteger.valueOf(3)); // <--
//					if (m.test.test(val)) {
//						if (debug)
//							System.out.println("Addidn " + val + " to " + m.targetTrue);
//						monkeys.get(m.targetTrue).items.add(val);
//					} else {
//						if (debug)
//							System.out.println("Addidn " + val + " to " + m.targetFalse);
//						monkeys.get(m.targetFalse).items.add(val);
//					}
//				}
//			});
//			if (debug || i == 1 || i == 2 || i == 1000) {
//				System.out.println(i + "\n" + monkeys.toString().replace("],", "]\n"));
//				monkeys.forEach(m -> System.out.println(m.id + " instpected " + m.count));
//			}
//			System.out.println(i);
//		}
//		System.out.println("\n" + monkeys.toString().replace("],", "]\n"));
//		monkeys.forEach(m -> System.out.println(m.id + " instpected " + m.count));
//		var list = monkeys.stream().map(m -> m.count).collect(Comparators.greatest(2, Integer::compareTo));
//		System.out.println(list.get(0) * list.get(1));
	}

	public static void printTerrain(Tile[][] terrain, Tile curNode) {
		for (int r = 0; r < terrain.length; r++) {
			for (int c = 0; c < terrain[0].length; c++) {
				var tile = terrain[r][c];
				String s = tile == curNode ? tile.toString().replace(';', '#').replace('{', '#').replace('}', '#')
						: tile.toString();
				System.out.print(s + " ");
			}
			System.out.println("");
		}
//		System.out.println(Arrays.deepToString(terrain).replace("],", "]\n"));
	}

	private static Tile[][] loadData(Path data) {
		// read all rows in a list
		List<String> lines = new ArrayList<>();
		int w = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(data.toFile()))) {
			String line = reader.readLine();
			while (line != null) {
				if (w == 0) {
					w = line.length();
				}
				// next line
				lines.add(line);
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Tile[][] terrain = new Tile[lines.size()][w];
		for (int r = 0; r < lines.size(); r++) {
			terrain[r] = new Tile[w];
		}
		for (int r = 0; r < lines.size(); r++) {
			for (int c = 0; c < lines.get(r).length(); c++) {
				Tile t = new Tile("" + lines.get(r).charAt(c), r, c);
				terrain[r][c] = t;
			}
		}
		return terrain;
	}

}
