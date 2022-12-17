package com.github.jumar.aoc2022.ten.part2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import com.github.jumar.aoc2022.ten.Op;
import com.github.jumar.aoc2022.ten.Register;
import com.github.jumar.aoc2022.ten.enOPType;

public class ten2 {
	// reg X controls the x pos of a sprite
	// 3 pix wide sprite. X is mid pos
	// sprite is drawn on current line
	//
	// screen is 40x6 [0....39]
	// one pixel drawn per cycle
	// each pix is a # if lit and . if dark
	//
	// as screen pix are drawn, determine if sprite is there and draw it
	// steps:
	// 1. have array for the screen pixels
	// 2. draw one line per cycle

	private static final boolean debug = false;

	public static void main(String[] args) {

//		Path data = Path.of("./data/10test1.txt");
//		Path data = Path.of("./data/10test.txt");
		Path data = Path.of("./data/10.txt");
		int cycle = 0;
		List<Op> ops = loadData(data);
		List<Integer> strength = new ArrayList<>();
		String[][] screen = new String[6][40];
		Op currOp = null;
		Register r = new Register(1);
		int spritePos = 0;
		int pixCol = 0;
		int pixRow = 0;
		while (!ops.isEmpty()) {
//			if (debug)
//				System.out.println("Starting cycle:---> " + cycle + " <---X= [" + r.val + "]");
			if (currOp == null)
				currOp = ops.remove(0);
//			if (debug)
//				System.out.println("currOP: " + currOp);
			// we are during cycle number "cycle"
			spritePos = r.val;
			drawPix(screen, pixCol, pixRow, spritePos);
			pixCol++;
			if (cycle != 0 && ((cycle + 1) % 40 == 0)) {
				pixCol = 0;
				pixRow++;
				if ((cycle + 1) % 240 == 0) {
					drawScreen(screen);
				}
			}
//			if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
//				System.out.println("Cycle  :---> " + cycle + " <---X= [" + r.val + "]");
//				strength.add(cycle * r.val);
//				spritePos = r.val;
//			}
			currOp = currOp.execute(r);
			// we are at end of cycle number "cycle"
//			if (debug)
//				System.out.println("End of cycle  :---> " + cycle + " <---X= [" + r.val + "]");
			cycle++;
		}
		System.out.println("Sum of strength: " + strength.stream().collect(Collectors.summingInt(x -> x)));
		// 4783 too high
	}

	private static void drawScreen(String[][] screen) {
		System.out.print("  ");
		for (int i = 0; i < 40; i++) {
			System.out.print(i % 10 + " ");
		}
		System.out.println();
		System.out.println(Arrays.deepToString(screen).replace("],", "]\n").replace(",", ""));
		System.out.println();
	}

	private static void drawPix(String[][] screen, int pixCol, int pixRow, int spritePos) {
		// sprite spans [spritePos-1...spritePos+1]
		// detect if sprite is at this col
		String pix = ".";
		if (pixCol == spritePos - 1 || pixCol == spritePos || pixCol == spritePos + 1) {
			pix = "#";
		}
		screen[pixRow][pixCol] = pix;
		if (debug)
			drawScreen(screen);
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
