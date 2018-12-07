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

import java.util.Arrays;

/**
 * Utility class to perform HEX encoding/decoding of values.
 *
 * @author Enrique Zamudio
 */
public final class HexCodec {

	static final char[] HEX = "0123456789ABCDEF".toCharArray();

	private HexCodec() {
	}

	public static String hexEncode(byte[] buffer, int start, int length) {
		if (buffer.length == 0) {
			return "";
		}
		int holder;
		char[] chars = new char[length * 2];
		int pos = -1;
		for (int i = start; i < start + length; i++) {
			holder = (buffer[i] & 0xf0) >> 4;
			pos++;
			chars[pos * 2] = HEX[holder];
			holder = buffer[i] & 0x0f;
			chars[(pos * 2) + 1] = HEX[holder];
		}
		return new String(chars);
	}

	// "1234" => new byte[]{0x12, 0x34}
	public static byte[] hexDecode(String hex) {
		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}

		//A null string returns an empty array
		if (hex == null || hex.length() == 0) {
			return new byte[0];
		} else if (hex.length() < 3) {
			return new byte[] { (byte) (Integer.parseInt(hex, 16) & 0xff) };
		}
		//Adjust accordingly for odd-length strings
		int count = hex.length();
		int nibble = 0;
		byte[] buf = new byte[count / 2];
		char c = 0;
		int holder = 0;
		int pos = 0;
		for (int i = 0; i < buf.length; i++) {
			for (int z = 0; z < 2 && pos < hex.length(); z++) {
				c = hex.charAt(pos++);
				if (c >= 'A' && c <= 'F') {
					c -= 55;
				} else if (c >= '0' && c <= '9') {
					c -= 48;
				} else if (c >= 'a' && c <= 'f') {
					c -= 87;
				}
				if (nibble == 0) {
					holder = c << 4;
				} else {
					holder |= c;
					buf[i] = (byte) holder;
				}
				nibble = 1 - nibble;
			}
		}
		return buf;
	}

	public static void main(String[] args) {
		System.out.println(Arrays.equals(HexCodec.hexDecode("12"), new byte[] { 0x12 }));
		System.out.println(Arrays.equals(HexCodec.hexDecode("1234"), new byte[] { 0x12, 0x34 }));
		System.out.println(Arrays.equals(HexCodec.hexDecode("123"), new byte[] { 0x01, 0x23 }));
		System.out.println(HexCodec.hexEncode(new byte[] { 0x12 }, 0, 1).equals("12"));
		System.out.println(HexCodec.hexEncode(new byte[] { 0x12, 0x34 }, 0, 2).equals("1234"));
	}
}
