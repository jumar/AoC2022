package com.github.jumar.aoc2022.seven;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.StringTokenizer;

import com.github.jumar.aoc2022.seven.seven.enCMD;

public class DataReader {
	private BufferedReader reader;
	private String nextCmdLine;

	public DataReader(Path file) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(file.toFile()));
	}

	public enCMD readCmd() {
		String line;
		try {
			if (nextCmdLine != null) {
				line = nextCmdLine;
				nextCmdLine = null;
			} else {
				line = reader.readLine();
			}
			if (line != null) {
				// parse line
				StringTokenizer tok = new StringTokenizer(line, " ");
				var first = tok.nextToken();
				if (first.equals("$")) {
					// we have a command
					return parseCommand(tok);
				} else
					throw new UnsupportedOperationException("Not a command");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enCMD.NONE;
	}

	private enCMD parseCommand(StringTokenizer tok) throws IOException {
		var cmd = tok.nextToken();
		if (cmd.equals("cd")) {
			enCMD.CD.param = tok.nextToken();
			return enCMD.CD;
		}
		// LS
		enCMD.LS.param = readLSCmdOutput();
		return enCMD.LS;
	}

	public String readLSCmdOutput() throws IOException {
		String out = "";
		String line = reader.readLine();
		while (line != null && !line.startsWith("$")) {
			out += line;
			out += "\n";
			line = reader.readLine();
		}
		nextCmdLine = line;
		return out;
	}

	public Object nextToken() {
		// TODO Auto-generated method stub
		return null;
	}
}
