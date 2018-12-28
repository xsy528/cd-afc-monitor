/*
 * j8583 A Java implementation of the ISO8583 protocol
 * Copyright (C) 2007 Enrique Zamudio Lopez
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */
package com.insigma.commons.util;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Routines for Binary Coded Digits.
 *
 * @author Enrique Zamudio
 *         Date: 23/04/13 11:24
 */
public final class Bcd {

	public enum Align {
		/**
		 * 左靠BCD
		 */
		Left,

		/**
		 * 右靠BCD
		 */
		Right
	}

	private Bcd() {
	}

	/**
	 * Decodes a BCD-encoded number as a long.
	 *
	 * @param buf         The byte buffer containing the BCD data.
	 * @param pos         The starting position in the buffer.
	 * @param digitLength The number of DIGITS (not bytes) to read.
	 */
	public static long decodeToLong(byte[] buf, final int pos, final int digitLength, Align align)
			throws IndexOutOfBoundsException {
		if (digitLength > 18) {
			throw new IndexOutOfBoundsException("Buffer too big to decode as long");
		}
		BigInteger value = decodeToBigInteger(buf, pos, digitLength, align);
		return value.longValue();
	}

	/**
	 * Decodes a BCD-encoded number as a BigInteger.
	 *
	 * @param buf         The byte buffer containing the BCD data.
	 * @param pos         The starting position in the buffer.
	 * @param digitLength The number of DIGITS (not bytes) to read.
	 */
	public static BigInteger decodeToBigInteger(byte[] buf, final int pos, final int digitLength, Align align)
			throws IndexOutOfBoundsException {
		int digitIdx = 0;
		int byteCount = (digitLength / 2) + (digitLength % 2);
		char[] digits = new char[byteCount * 2];
		for (int i = pos; i < pos + byteCount; i++) {
			digits[digitIdx++] = (char) (((buf[i] & 0xf0) >> 4) + 48);
			digits[digitIdx++] = (char) ((buf[i] & 0x0f) + 48);
		}

		boolean isOddLength = digitLength % 2 != 0;
		String digitText;
		if (isOddLength && align == Align.Left) {
			digitText = new String(digits, 0, digits.length - 1);
		} else if (isOddLength && align == Align.Right) {
			digitText = new String(digits, 1, digits.length - 1);
		} else {
			digitText = new String(digits);
		}
		return new BigInteger(new String(digitText));
	}

	/**
	 * Encode the value as BCD and put it in the buffer. The buffer must be big enough
	 * to store the digits in the original value (half the length of the string).
	 */
	public static void encode(String value, byte[] buf, Align align) {
		if (value.length() % 2 == 1) {
			if (align == Align.Left) {
				value = value + '0';
			} else {
				value = '0' + value;
			}
		}
		//encode the rest of the string
		int charPos = 0; //char where we start
		int bufPos = 0;
		while (charPos < value.length()) {
			buf[bufPos] = (byte) (((value.charAt(charPos) - 48) << 4) | (value.charAt(charPos + 1) - 48));
			charPos += 2;
			bufPos++;
		}
	}

	public static void main(String[] args) {
		{
			byte[] raw = new byte[] { 0x12, 0x34 };
			System.out.println(Bcd.decodeToLong(raw, 0, 1, Align.Left) == 1);
			System.out.println(Bcd.decodeToLong(raw, 0, 2, Align.Left) == 12);
			System.out.println(Bcd.decodeToLong(raw, 0, 3, Align.Left) == 123);
			System.out.println(Bcd.decodeToLong(raw, 0, 4, Align.Left) == 1234);
			System.out.println(Bcd.decodeToLong(raw, 0, 1, Align.Right) == 2);
			System.out.println(Bcd.decodeToLong(raw, 0, 2, Align.Right) == 12);
			System.out.println(Bcd.decodeToLong(raw, 0, 3, Align.Right) == 234);
			System.out.println(Bcd.decodeToLong(raw, 0, 4, Align.Right) == 1234);
		}
		{
			byte[] raw = new byte[] { 0x12, 0x34, 0x56, 0x78, 0x31, 0x42, 0x53, 0x64, 0x13 };
			System.out.println(Bcd.decodeToLong(raw, 0, 17, Align.Left) == 12345678314253641L);
			System.out.println(Bcd.decodeToLong(raw, 0, 18, Align.Left) == 123456783142536413L);
			System.out.println(Bcd.decodeToLong(raw, 0, 17, Align.Right) == 23456783142536413L);
			System.out.println(Bcd.decodeToLong(raw, 0, 18, Align.Right) == 123456783142536413L);
		}
		{
			byte[] raw = new byte[2];
			Bcd.encode("1234", raw, Align.Left);
			System.out.println(Arrays.equals(new byte[] { 0x12, 0x34 }, raw));
		}
		{
			byte[] raw = new byte[2];
			Bcd.encode("1234", raw, Align.Right);
			System.out.println(Arrays.equals(new byte[] { 0x12, 0x34 }, raw));
		}
		{
			byte[] raw = new byte[2];
			Bcd.encode("123", raw, Align.Left);
			System.out.println(Arrays.equals(new byte[] { 0x12, 0x30 }, raw));
		}
		{
			byte[] raw = new byte[2];
			Bcd.encode("123", raw, Align.Right);
			System.out.println(Arrays.equals(new byte[] { 0x01, 0x23 }, raw));
		}
	}
}
