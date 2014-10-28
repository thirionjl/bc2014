package com.sopra.battlecode.bc2014;

import com.sopra.battlecode.bc2014.cipher.CipherText;

public class CodeBreaker1 {

	public static void main(String[] args) throws Exception {
		CipherText text = new CipherText("/textCypher1.txt");
		int[] decodeChars = text.getCharacterCodes().map(c -> c - 96).toArray();
		System.out.println(toString(decodeChars));
	}

	static String toString(int[] ints) {
		char[] chars = new char[ints.length];
		for (int i = 0; i < ints.length; i++) {
			chars[i] = (char) ints[i];
		}
		return new String(chars);
	}

}
