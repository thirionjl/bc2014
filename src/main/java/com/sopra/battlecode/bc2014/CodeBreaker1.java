package com.sopra.battlecode.bc2014;

import com.sopra.battlecode.bc2014.cipher.CipherText;

import static com.sopra.battlecode.bc2014.output.OutputUtils.printout;

public class CodeBreaker1 {

	private static final String MOST_FREQUENT_ENGLISH_WORD = "the";

	public static void main(String[] args) throws Exception {
		CipherText text = new CipherText("/textCypher1.txt");

		// With such a short text impossible to get enough redundancy. Sole
		// solution is a Cesar cipher (shifting character codes)
		int cipherTextAverageChar = (int) text.getCharacterCodes().average().getAsDouble();
		printout(">>> Ciphertext text average char code: " + cipherTextAverageChar);
		int englishTextAverageChar = (int) 'e';
		printout(">>> English text average char code: " + englishTextAverageChar);
		int averageDistance = cipherTextAverageChar - englishTextAverageChar;
		printout(">>> Average character distance is %d: " + averageDistance);

		printout(">>> Decode by trying all possible shifts");
		for (int testedKey = averageDistance - 10; testedKey < averageDistance + 10; testedKey++) {
			String clearTextCandidate = shiftCharacterCodes(text, testedKey);
			if (isLookingLikeEnglish(clearTextCandidate)) {
				printout(clearTextCandidate);
			}
		}

	}

	static boolean isLookingLikeEnglish(String clearTextCandidate) {
		return clearTextCandidate.contains(MOST_FREQUENT_ENGLISH_WORD);
	}

	static String shiftCharacterCodes(CipherText text, int shift) throws Exception {
		int[] decodeChars = text.getCharacterCodes().map(c -> c - shift).toArray();
		return characterCodesToString(decodeChars);
	}

	static String characterCodesToString(int[] ints) {
		char[] chars = new char[ints.length];
		for (int i = 0; i < ints.length; i++) {
			chars[i] = (char) ints[i];
		}
		return new String(chars);
	}

}
