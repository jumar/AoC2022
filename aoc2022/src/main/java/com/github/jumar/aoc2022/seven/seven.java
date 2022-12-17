package com.github.jumar.aoc2022.seven;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.github.jumar.aoc2022.TreeNode;

public class seven {

	static TreeNode<Data> root = new TreeNode<>(new Data("root"));
	static TreeNode<Data> curr;
	static DataReader reader;

	static enum enCMD {
		CD, LS, NONE;

		public String param; // param for cd command

		public void execute() {
			switch (this) {
			case CD:
				executeCD();
				break;
			case LS:
				executeLS();
				break;
			default:
			case NONE:
				break;
			}
		}

		void executeLS() {
			var output = param;
			// need to parse line by line
			String[] entries = output.split("\n");
			Arrays.asList(entries).forEach(f -> {
				processLSLine(f);
			});
		}

		void processLSLine(String f) {
			if (f.startsWith("dir")) {
				addNewDir(f.split(" ")[1]);
			} else {
				// file
				String[] split = f.split(" ");
				addNewFile(Integer.parseInt(split[0]), split[1]);
			}
		}

		void addNewFile(int size, String name) {
			curr.addChild(new Data(name, size));
		}

		void addNewDir(String name) {
			curr.addChild(new Data(name));
		}

		private void executeCD() {
			var targetDir = param;
			if (targetDir.equals("/")) {
				curr = root;
			} else {
				if (targetDir.equals("..")) {
					if (curr != root) {
						curr = curr.parent;
					}
				} else {
					// move in child folder
					curr.children.forEach(child -> {
						if (child.data.name.equals(targetDir)) {
							curr = child;
						}
					});
				}
			}
		}
	};

	public static void main(String[] args) throws FileNotFoundException {
		// need to delete some files to make space for update
		// input: terminal output of ls at root dir
		//
		// tree of files and dirs
		// "/" is the root
		// can navigate up and down and ls in curr dir
		// commands starts with $
		// commands :
		// cd (cd x, cd .., cd /)
		// ls -> output 123 abc (size name) or dir abc (dir name)
		//
		// given output of console, can retroengineer the dir structure
		// Need to ID the dirs with the largest files

		// problem: find all dirs with size < 100_000. Compute the total size of these
		//

		// part 2:
		// available disk space 70_000_000
		// need 30_000_000 for update
		// find dir to delete to get enough space (8_381_165)

		Path data = Path.of("./data/7.txt");
//		Path data = Path.of("./data/7test.txt");
		reader = new DataReader(data);
		var cmd = reader.readCmd();
		while (cmd != enCMD.NONE) {
			cmd.execute();
			cmd = reader.readCmd();
		}
		// printTree();
		computeSize();
		printTree(); // Yay!
		var nodes = identifySmallerThan(100_000);
		Integer size = nodes.stream().map(n -> n.data.size).collect(Collectors.summingInt(val -> (Integer) val));
		System.out.println("Total size of elems with size at most 100_000: " + size);
		int updateSpace = 30_000_000;
		int totalSpace = 70_000_000;
		int usedSpace = root.data.size;
		int freeSpace = totalSpace - usedSpace;
		int neededSpace = Math.abs(freeSpace - updateSpace);
		System.out.println("Elems with size at least " + neededSpace);
		nodes = identifyLargerThan(neededSpace);
		Collections.sort(nodes, new Comparator<TreeNode<Data>>() {
			@Override
			public int compare(TreeNode<Data> o1, TreeNode<Data> o2) {
				return ((Integer) o1.data.size).compareTo((Integer) o2.data.size);
			}
		});
		nodes.stream().filter(n -> n.data.size >= neededSpace).forEach(c -> System.out.println("cc: " + c));// .collect(Collectors.toList());
		// wcmwrtjn <- no
	}

	private static List<TreeNode<Data>> identifySmallerThan(int higherLimit) {
		List<TreeNode<Data>> nodes = new ArrayList<>();
		for (TreeNode<Data> node : root) {
			if (node.data.size <= higherLimit && !node.isLeaf()) { // only count the dirs
				nodes.add(node);
			}
		}
		return nodes;
	}

	private static List<TreeNode<Data>> identifyLargerThan(int higherLimit) {
		List<TreeNode<Data>> nodes = new ArrayList<>();
		for (TreeNode<Data> node : root) {
			if (node.data.size >= higherLimit && !node.isLeaf()) { // only count the dirs
				nodes.add(node);
			}
		}
		return nodes;
	}

	private static void computeSize() {
		for (TreeNode<Data> node : root) {
			if (node.isLeaf()) {
				// either a file or an empty dir
				int nodeSize = node.data.size;
				if (nodeSize != Data.UNKNOWN_SIZE) {
					// it's a file we already have its size
					// lets propagate its size to its parents
					propagateSizeToParents(node, nodeSize);
				}
			} else {
				// it's a non empty dir
				// do nothing
			}
			// System.out.println(indent + node.data);
		}

	}

	private static void propagateSizeToParents(TreeNode<Data> node, int nodeSize) {
		node.parent.data.size += nodeSize;
		if (!node.parent.isRoot())
			propagateSizeToParents(node.parent, nodeSize);
	}

	private static void printTree() {
		for (TreeNode<Data> node : root) {
			String indent = createIndent(node.getLevel());
			System.out.println(indent + node.data);
		}
	}

	private static String createIndent(int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			sb.append(' ');
		}
		return sb.toString();
	}
}
