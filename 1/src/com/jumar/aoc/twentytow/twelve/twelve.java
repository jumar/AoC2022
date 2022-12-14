package com.jumar.aoc.twentytow.twelve;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.Traverser;
import com.google.common.graph.ValueGraphBuilder;

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
	private static final boolean debug = false;

	public static void main(String[] args) {

//		Path data = Path.of("./data/12test.txt");
		Path data = Path.of("./data/12.txt");
		Tile[][] terrain = loadData(data);
		// buid graph
		MutableValueGraph<Tile, Integer> g = ValueGraphBuilder.directed().allowsSelfLoops(false).build();
		// find starter and target tiles and add nodes to graph
		Tile start = null;
		Tile target = null;
		List<Tile> aTiles = new ArrayList<>();
		for (int r = 0; r < terrain.length; r++) {
			for (int c = 0; c < terrain[0].length; c++) {
				Tile tile = terrain[r][c];
				g.addNode(tile);
				if (tile.isStart)
					start = tile;
				else if (tile.isTarget)
					target = tile;
				if (tile.h == 1)
					aTiles.add(tile);
			}
		}
		var targetF = target;
		// add edges to graph
		for (int r = 0; r < terrain.length; r++) {
			for (int c = 0; c < terrain[0].length; c++) {
				Tile tile = terrain[r][c];
				tile.getAvailableDirections(terrain).forEach(t -> {
//					if (debug)
					System.out.println("edge: " + tile + "->" + t);
					g.putEdgeValue(tile, t, 1);
				});
			}
		}
		if (debug) {
			printTerrain(terrain, start);
			System.out.println("==============Dept First==============");
			Traverser.forGraph(g).depthFirstPostOrder(start).forEach(x -> System.out.println(x));
			// Print the nodes Bread First
			System.out.println("==============Breath First==============");
			Traverser.forGraph(g).breadthFirst(start).forEach(x -> System.out.println(x));
		}
		int[] shortest = new int[] { Integer.MAX_VALUE };
		for (var t : aTiles) {

			calculateShortestPathFromSource(g, t);
			Traverser.forGraph(g).depthFirstPostOrder(t).forEach(x -> {

				if (x == targetF) {
					System.out.println(x.toLongString());
					if (x.distance < shortest[0])
						shortest[0] = x.distance;
				}
			});
		}
		System.out.println("Shortest path: " + shortest[0]);
//		Player p = new Player(start);
//		p.findPathTo(target, terrain);
	}

	public static MutableValueGraph<Tile, Integer> calculateShortestPathFromSource(MutableValueGraph<Tile, Integer> g,
			Tile source) {
		source.distance = 0;

		Set<Tile> settledNodes = new HashSet<>();
		Set<Tile> unsettledNodes = new HashSet<>();

		unsettledNodes.add(source);
		while (unsettledNodes.size() != 0) {
			Tile currentNode = getLowestDistanceNode(unsettledNodes);
			unsettledNodes.remove(currentNode);
			for (var adjacentNode : g.successors(currentNode)) {
				if (!settledNodes.contains(adjacentNode)) {
					calculateMinimumDistance(adjacentNode, g.edgeValueOrDefault(currentNode, adjacentNode, 1),
							currentNode);
					unsettledNodes.add(adjacentNode);
				}
			}
			settledNodes.add(currentNode);
		}
		return g;
	}

	private static void calculateMinimumDistance(Tile evaluationNode, Integer edgeWeigh, Tile sourceNode) {
		Integer sourceDistance = sourceNode.getDistance();
		if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
			evaluationNode.distance = sourceDistance + edgeWeigh;
			LinkedList<Tile> shortestPath = new LinkedList<>(sourceNode.shortestPath);
			shortestPath.add(sourceNode);
			evaluationNode.shortestPath = shortestPath;
		}
	}

	private static Tile getLowestDistanceNode(Set<Tile> unsettledNodes) {
		Tile lowestDistanceNode = null;
		int lowestDistance = Integer.MAX_VALUE;
		for (Tile node : unsettledNodes) {
			int nodeDistance = node.getDistance();
			if (nodeDistance < lowestDistance) {
				lowestDistance = nodeDistance;
				lowestDistanceNode = node;
			}
		}
		return lowestDistanceNode;
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
