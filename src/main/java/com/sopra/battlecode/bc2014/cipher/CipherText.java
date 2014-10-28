package com.sopra.battlecode.bc2014.cipher;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.sopra.battlecode.bc2014.CodeBreaker2;

public class CipherText {
	private static final String WORD_SEPARATORS = "[^a-zA-Z]";
	final Path pathToCipheredText;

	public CipherText(String classpathLocation) {
		this.pathToCipheredText = getPath(classpathLocation);
	}

	public Stream<String> getLines() throws Exception {
		return Files.lines(pathToCipheredText, Charset.forName("ISO-8859-1"));
	}

	public Stream<String> getWords() throws Exception {
		return getLines().map(line -> line.split(WORD_SEPARATORS)).flatMap(Arrays::stream).filter(StringUtils::isNotBlank);
	}

	public Stream<Character> getCharacters(Predicate<Character> filter) throws Exception {
		return getLines().map(line -> line.split("")).flatMap(Arrays::stream).filter(s -> s.length() > 0).map(s -> Character.valueOf(s.charAt(0)))
				.filter(filter);
	}

	public IntStream getCharacterCodes() throws Exception {
		return getCharacters(x -> true).mapToInt(c -> (int) c.charValue());
	}

	private static Path getPath(String classpathLocation) {
		try {
			return Paths.get(CodeBreaker2.class.getResource(classpathLocation).toURI());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
