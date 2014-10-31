package com.sopra.battlecode.bc2014;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import static java.util.stream.Collectors.groupingBy;

public class SopraPhone {

	static class IndexNode {
		Character c;
		List<IndexNode> subNodes;

		IndexNode(Character c) {
			this.c = c;
			this.subNodes = new ArrayList<>();
		}

		void addSubnode(IndexNode n) {
			this.subNodes.add(n);
		}

		int count() {
			int subCount = 0;
			for (IndexNode node : subNodes) {
				subCount += node.count();
			}
			return 1 + subCount;
		}

		@Override
		public String toString() {
			StringWriter sw = new StringWriter();
			toStringRec("", true, new PrintWriter(sw));
			return sw.toString();
		}

		void toStringRec(String prefix, boolean isTail, PrintWriter writer) {
			writer.println(prefix + (isTail ? "└── " : "├── ") + c);
			for (int i = 0; i < subNodes.size() - 1; i++) {
				subNodes.get(i).toStringRec(
						prefix + (isTail ? "    " : "│   "), false, writer);
			}
			if (subNodes.size() > 0) {
				subNodes.get(subNodes.size() - 1).toStringRec(
						prefix + (isTail ? "    " : "│   "), true, writer);
			}
		}
	}

	IndexNode indexRootNode;

	SopraPhone(String... phoneNumbers) {
		this.indexRootNode = new IndexNode('+');
		attachSubnodes(this.indexRootNode, Arrays.asList(phoneNumbers));
	}

	void attachSubnodes(IndexNode parentNode, List<String> phoneNumbers) {
		Map<Character, List<String>> phoneNumbersByFirstDigit = phoneNumbers
				.stream().filter(StringUtils::isNotEmpty)
				.collect(groupingBy(s -> s.charAt(0)));

		phoneNumbersByFirstDigit.entrySet().stream().forEach(e -> {
			IndexNode n = new IndexNode(e.getKey());
			attachSubnodes(n, removeFirstDigits(e.getValue()));
			parentNode.addSubnode(n);
		});
	}

	List<String> removeFirstDigits(List<String> phoneNumbers) {
		List<String> substrings = new ArrayList<String>();
		for (String s : phoneNumbers) {
			substrings.add(s.substring(1));
		}
		return substrings;
	}

	int count() {
		return this.indexRootNode.count() - 1;
	}

	@Override
	public String toString() {
		return this.indexRootNode.toString();
	}

	public static void main(String[] args) {
		SopraPhone phone = new SopraPhone("0412578440", "0412199803",
				"0468892011", "112", "15");

		System.out.println(String.format("There are %d nodes in index",
				phone.count()));
		System.out.println("Structure is:");
		System.out.println(phone);

	}

}
