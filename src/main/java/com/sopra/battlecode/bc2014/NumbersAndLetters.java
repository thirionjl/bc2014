package com.sopra.battlecode.bc2014;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class NumbersAndLetters {

	final String draw;

	NumbersAndLetters(String draw) {
		this.draw = draw;
	}

	List<String> getLongestWords() throws Exception {
		Function<String, Integer> length = s -> s.replaceAll("-", "").length();
		Map<Integer, List<String>> matchesPerSize = dictionnary().filter(w -> matches(w)).collect(groupingBy(length));
		if (matchesPerSize.isEmpty()) {
			return Collections.emptyList();
		} else {
			int maxLength = Collections.max(matchesPerSize.keySet());
			return matchesPerSize.get(maxLength);
		}
	}

	boolean matches(String word) {
		String lcWord = word.toLowerCase();
		String remainingChars = this.draw;
		for (char rawChar : lcWord.toCharArray()) {
			if (!isIgnored(rawChar)) {
				char normalizedChar = noAccent(rawChar);
				if (!contains(remainingChars, normalizedChar)) {
					return false;
				} else {
					remainingChars = removeUsedChar(remainingChars, normalizedChar);
				}
			}
		}
		return true;
	}

	static Stream<String> dictionnary() throws Exception {
		Path path = Paths.get(NumbersAndLetters.class.getResource("/dictionnary.txt").toURI());
		return Files.lines(path, Charset.forName("ISO-8859-1"));
	}

	static String removeUsedChar(String remainingChars, char usedChar) {
		return remainingChars.replaceFirst(Character.toString(usedChar), "");
	}

	static boolean isIgnored(char c) {
		return "-".indexOf(c) >= 0;
	}

	static boolean contains(String remainingChars, char c) {
		return remainingChars.indexOf(c) >= 0;
	}

	static char noAccent(char c) {
		if ("äâä".indexOf(c) >= 0) {
			return 'a';
		} else if ("ç".indexOf(c) >= 0) {
			return 'c';
		} else if ("èéêë".indexOf(c) >= 0) {
			return 'e';
		} else if ("îï".indexOf(c) >= 0) {
			return 'i';
		} else if ("ôö".indexOf(c) >= 0) {
			return 'o';
		} else if ("ùûü".indexOf(c) >= 0) {
			return 'u';
		} else {
			return c;
		}
	}

	public static void main(String[] args) throws Exception {
		List<String> longestWords = new NumbersAndLetters("aetdurejlm").getLongestWords();
		System.out.println(String.join("\n", longestWords));

	}
}
