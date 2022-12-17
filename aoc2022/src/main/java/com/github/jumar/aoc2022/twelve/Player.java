package com.github.jumar.aoc2022.twelve;

import java.util.ArrayList;
import java.util.List;

import com.github.jumar.aoc2022.TreeNode;

public class Player {
	public Tile pos;
	public Tile startPos;
	public List<Tile> path = new ArrayList<>();

	public Player(Tile start) {
		startPos = start;
		pos = start;
		path.add(start);
	}

	public void moveTo(Tile t) {
		path.add(t);
		pos = t;
	}

	public void buildGraph(Tile[][] terrain) {
		// for each note, link it to its reachable neighbors
		for (int r = 0; r < terrain.length; r++) {
			for (int c = 0; c < terrain[0].length; c++) {
				Tile t = terrain[r][c];
				var neighbors = t.getAvailableDirections(terrain);
			}
		}
	}

	public enum enExplorationStatus {
		deadEnd, allDirsExplored, exploring;
	}

	public void findPathTo(Tile target, Tile[][] terrain) {
		TreeNode<Tile> startNode = new TreeNode<Tile>(startPos);
		// on part du start
		// on regarde où on peut aller
		// on avance dans la direction preferencielle
		// on construit l'arbre
		// quand on est arrivé Youpi
		// quand on est bloqué on repart l'arbre à 0
		// on cherche le premier noeud non fully exploré, on choisit la seconde dir pref
		enExplorationStatus[] status = new enExplorationStatus[1];
		// find a non fully explored node
		TreeNode<Tile> node = findNonFullyExploredNode(terrain, startNode);
		do {
			do {
				node = moveOne(terrain, node, status);
				if (node != null) {
					System.out.println("Exploring: " + node.data);
					twelve.printTerrain(terrain, node.data);
				} else {
					System.out.println("Status: " + status[0]);
				}
			} while (node != null);
			node = findNonFullyExploredNode(terrain, startNode);
		} while (node != null);

	}

	private TreeNode<Tile> findNonFullyExploredNode(Tile[][] terrain, TreeNode<Tile> startNode) {
		TreeNode<Tile>[] newNodeHolder = new TreeNode[1];
		startNode.iterator().forEachRemaining(n -> {
			if (newNodeHolder[0] == null) { // stop at first found
				List<Tile> dirs = n.data.getAvailableDirections(terrain);
				// remove dirs of already explored children
				for (var c : n.children) {
					dirs.remove(c.data);
				}
				if (!dirs.isEmpty()) {
					// Node with at least one dir non explored
					newNodeHolder[0] = n;
				}
			}
		});
		return newNodeHolder[0];
	}

	private TreeNode<Tile> moveOne(Tile[][] terrain, TreeNode<Tile> node, enExplorationStatus[] status) {
		List<Tile> dirs = node.data.getAvailableDirections(terrain);
		if (!dirs.isEmpty()) {
			// remove parent from dirs
			if (node.parent != null)
				dirs.remove(node.parent.data);
			for (var child : node.children) {
				// check if dirs are children
				if (dirs.contains(child.data)) {
					// already visited
					dirs.remove(child.data);
				}
			}
			// remove dirs of already explored children
			for (var c : node.children) {
				dirs.remove(c.data);
			}
			// remove already
			List<Tile> alreadyExplored = new ArrayList<>();
			for (var d : dirs) {
				var parent = node.parent;
				while (parent != null) {
					if (parent.data == d)
						alreadyExplored.add(d);
					parent = parent.parent;
				}
			}
			// avoid loops
			dirs.removeAll(alreadyExplored);
			if (!dirs.isEmpty()) {
				status[0] = enExplorationStatus.exploring;
				return node.addChild(dirs.get(0));
			} else {
				// all options explored at this node
				status[0] = enExplorationStatus.allDirsExplored;
			}
		} else {
			// dead end
			status[0] = enExplorationStatus.deadEnd;
		}
		return null;
	}
}
