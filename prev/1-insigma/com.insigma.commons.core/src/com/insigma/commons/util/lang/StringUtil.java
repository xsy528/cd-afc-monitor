/*
 * 项目:      AFC通信组件
 * 代码文件:   StringUtil.java
 * 版本: 1.0
 * 日期: 2007-10-18 下午11:31:40
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.commons.util.lang;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jiangxf
 */
public class StringUtil {

	private static Log logger = LogFactory.getLog(StringUtil.class);

	public static String upperFirst(String string) {
		if (string == null) {
			return null;
		}
		String firstChar = String.valueOf(Character.toUpperCase(string.charAt(0)));
		return firstChar + string.substring(1);
	}

	public static String lowerFirst(String string) {
		if (string == null) {
			return null;
		}
		String firstChar = String.valueOf(Character.toLowerCase(string.charAt(0)));
		return firstChar + string.substring(1);
	}

	public static String valueOf(Integer value) {
		if (value != null) {
			return value.toString();
		}
		return "";
	}

	public static String valueOf(int value) {
		return Integer.toString(value);
	}

	public static String valueOf(Short value) {
		if (value != null) {
			return value.toString();
		}
		return "";
	}

	public static String valueOf(short value) {
		return Short.toString(value);
	}

	public static String valueOf(Byte value) {
		if (value != null) {
			return value.toString();
		}
		return "";
	}

	public static String valueOf(Double value) {
		if (value != null) {
			return value.toString();
		}
		return "";
	}

	public static String valueOf(Long value) {
		if (value != null) {
			return value.toString();
		}
		return "";
	}

	public static String valueOf(Character value) {
		if (value != null) {
			return value.toString();
		}
		return "";
	}

	/**
	 * change the object <b>obj</b> which is instance of Integer(int),Long(long), Short(short) to
	 * the String <b>str</b> which is filled with zero according the <b>length</b><blockquote>
	 * 
	 * <pre>
	 * int obj = 123456;
	 * String str = ChangeNumberToStringWithZero(obj, 8);
	 * str = &quot;00123456&quot;;
	 * &lt;p&gt;
	 * int obj = 1234567890;
	 * String str = ChangeNumberToStringWithZero(obj, 8);
	 * str = &quot;12345678&quot;;
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param obj
	 * @param length
	 * @return str
	 */
	public static String ChangeNumberToStringWithZero(Object obj, int length) {
		String str = obj.toString();
		if (str.length() > length) {
			return str.substring(0, str.length() - length + 1);
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length - str.length(); i++) {
			buffer.append("0");
		}
		buffer.append(str);
		return buffer.toString().trim();
	}

	/**
	 * change short[](actually,it's a char[] Array) to String
	 * 
	 * @param array
	 * @return a String
	 */
	public static String changeShortArrayToString(short[] array) {
		StringBuffer buffer = new StringBuffer();
		for (short element : array) {
			char ch = (char) element;
			buffer.append(ch);
		}
		return buffer.toString().trim();
	}

	public static String formateByteArrayToString(byte[] array) {
		StringBuffer buffer = new StringBuffer();
		for (byte element : array) {
			buffer.append(String.format("%02X", element));
		}
		return buffer.toString().trim();
	}

	/**
	 * change int[](actually,it's a char[] Array) to String
	 * 
	 * @param array
	 * @return a String
	 */
	public static String changeIntegerArrayToString(int[] array) {
		StringBuffer buffer = new StringBuffer();
		for (int element : array) {
			char ch = (char) element;
			buffer.append(ch);
		}
		return buffer.toString().trim();
	}

	/**
	 * change a String to an short[] Array(actually,it's filled with chars)
	 * 
	 * @param value
	 *            a String
	 * @param length
	 *            the length of the short[] array
	 * @return short[] array
	 */
	public static short[] changeStringToShortArray(String value, int length) {
		short[] array = new short[length];
		String str;
		if (value.length() > length) {
			str = value.substring(0, length);
		} else {
			str = value;
		}
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			array[i] = (short) ch;
		}
		if (str.length() < length) {
			for (int i = str.length(); i < length; i++) {
				char ch = ' ';
				array[i] = (short) ch;
			}
		}
		return array;
	}

	/**
	 * change a String to an int[] Array(actually,it's filled with chars)
	 * 
	 * @param value
	 *            a String
	 * @param length
	 *            the length of the short[] array
	 * @return short[] array
	 */
	public static int[] changeStringToIntegerArray(String value, int length) {
		int[] array = new int[length];
		String str;
		if (value.length() > length) {
			str = value.substring(0, length);
		} else {
			str = value;
		}
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			array[i] = (int) ch;
		}
		if (str.length() < length) {
			for (int i = str.length(); i < length; i++) {
				char ch = ' ';
				array[i] = (int) ch;
			}
		}
		return array;
	}

	/**
	 * change a Integer to an int[] Array(actually,it's filled with chars)
	 * 
	 * @param value
	 *            a Integer
	 * @param length
	 *            the size of the Integer
	 * @return int[] array
	 */
	public static int[] changeIntegerToIntegerArray(Integer value, int length) {
		int[] array = new int[length];
		for (int i = 0; i < Integer.SIZE; i++) {
			array[i] = Integer.getInteger(value.toString(), i);
		}
		return array;
	}

	static Pattern collectionPattern = null;

	static final int li_SecPosValue[] = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635,
			3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249 };

	static final String lc_FirstLetter[] = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "O", "P",
			"Q", "R", "S", "T", "W", "X", "Y", "Z" };

	static final String ls_SecondSecTable = 4096
			+ "CJWGNSPGCGNE[Y[BTYYZDXYKYGT[JNNJQMBSGZSCYJSYY[PGKBZGY[YWJKGKLJYWKPJQHY[W[DZLSGMRYPYWWCCKZNKYYGTT"
			+ "NJJNYKKZYTCJNMCYLQLYPYQFQRPZSLWBTGKJFYXJWZLTBNCXJJJJTXDTTSQZYCDXXHGCK[PHFFSS[YBGXLPPBYLL[HLXS[ZM"
			+ "[JHSOJNGHDZQYKLGJHSGQZHXQGKEZZWYSCSCJXYEYXADZPMDSSMZJZQJYZC[J[WQJBYZPXGZNZCPWHKXHQKMWFBPBYDTJZZK"
			+ "QHYLYGXFPTYJYYZPSZLFCHMQSHGMXXSXJ[[DCSBBQBEFSJYHXWGZKPYLQBGLDLCCTNMAYDDKSSNGYCSGXLYZAYBNPTSDKDYLH"
			+ "GYMYLCXPY[JNDQJWXQXFYYFJLEJPZRXCCQWQQSBNKYMGPLBMJRQCFLNYMYQMSQYRBCJTHZTQFRXQHXMJJCJLXQGJMSHZKBSWYE"
			+ "MYLTXFSYDSWLYCJQXSJNQBSCTYHBFTDCYZDJWYGHQFRXWCKQKXEBPTLPXJZSRMEBWHJLBJSLYYSMDXLCLQKXLHXJRZJMFQHXHW"
			+ "YWSBHTRXXGLHQHFNM[YKLDYXZPYLGG[MTCFPAJJZYLJTYANJGBJPLQGDZYQYAXBKYSECJSZNSLYZHSXLZCGHPXZHZNYTDSBCJK"
			+ "DLZAYFMYDLEBBGQYZKXGLDNDNYSKJSHDLYXBCGHXYPKDJMMZNGMMCLGWZSZXZJFZNMLZZTHCSYDBDLLSCDDNLKJYKJSYCJLKWH"
			+ "QASDKNHCSGANHDAASHTCPLCPQYBSDMPJLPZJOQLCDHJJYSPRCHN[NNLHLYYQYHWZPTCZGWWMZFFJQQQQYXACLBHKDJXDGMMYDJ"
			+ "XZLLSYGXGKJRYWZWYCLZMSSJZLDBYD[FCXYHLXCHYZJQ[[QAGMNYXPFRKSSBJLYXYSYGLNSCMHZWWMNZJJLXXHCHSY[[TTXRYC"
			+ "YXBYHCSMXJSZNPWGPXXTAYBGAJCXLY[DCCWZOCWKCCSBNHCPDYZNFCYYTYCKXKYBSQKKYTQQXFCWCHCYKELZQBSQYJQCCLMTHS"
			+ "YWHMKTLKJLYCXWHEQQHTQH[PQ[QSCFYMNDMGBWHWLGSLLYSDLMLXPTHMJHWLJZYHZJXHTXJLHXRSWLWZJCBXMHZQXSDZPMGFCS"
			+ "GLSXYMJSHXPJXWMYQKSMYPLRTHBXFTPMHYXLCHLHLZYLXGSSSSTCLSLDCLRPBHZHXYYFHB[GDMYCNQQWLQHJJ[YWJZYEJJDHPB"
			+ "LQXTQKWHLCHQXAGTLXLJXMSL[HTZKZJECXJCJNMFBY[SFYWYBJZGNYSDZSQYRSLJPCLPWXSDWEJBJCBCNAYTWGMPAPCLYQPCLZ"
			+ "XSBNMSGGFNZJJBZSFZYNDXHPLQKZCZWALSBCCJX[YZGWKYPSGXFZFCDKHJGXDLQFSGDSLQWZKXTMHSBGZMJZRGLYJBPMLMSXLZ"
			+ "JQQHZYJCZYDJWBMYKLDDPMJEGXYHYLXHLQYQHKYCWCJMYYXNATJHYCCXZPCQLBZWWYTWBQCMLPMYRJCCCXFPZNZZLJPLXXYZTZ"
			+ "LGDLDCKLYRZZGQTGJHHGJLJAXFGFJZSLCFDQZLCLGJDJCSNZLLJPJQDCCLCJXMYZFTSXGCGSBRZXJQQCTZHGYQTJQQLZXJYLYL"
			+ "BCYAMCSTYLPDJBYREGKLZYZHLYSZQLZNWCZCLLWJQJJJKDGJZOLBBZPPGLGHTGZXYGHZMYCNQSYCYHBHGXKAMTXYXNBSKYZZGJ"
			+ "ZLQJDFCJXDYGJQJJPMGWGJJJPKQSBGBMMCJSSCLPQPDXCDYYKY[CJDDYYGYWRHJRTGZNYQLDKLJSZZGZQZJGDYKSHPZMTLCPWN"
			+ "JAFYZDJCNMWESCYGLBTZCGMSSLLYXQSXSBSJSBBSGGHFJLYPMZJNLYYWDQSHZXTYYWHMZYHYWDBXBTLMSYYYFSXJC[DXXLHJHF"
			+ "[SXZQHFZMZCZTQCXZXRTTDJHNNYZQQMNQDMMG[YDXMJGDHCDYZBFFALLZTDLTFXMXQZDNGWQDBDCZJDXBZGSQQDDJCMBKZFFXM"
			+ "KDMDSYYSZCMLJDSYNSBRSKMKMPCKLGDBQTFZSWTFGGLYPLLJZHGJ[GYPZLTCSMCNBTJBQFKTHBYZGKPBBYMTDSSXTBNPDKLEYC"
			+ "JNYDDYKZDDHQHSDZSCTARLLTKZLGECLLKJLQJAQNBDKKGHPJTZQKSECSHALQFMMGJNLYJBBTMLYZXDCJPLDLPCQDHZYCBZSCZB"
			+ "ZMSLJFLKRZJSNFRGJHXPDHYJYBZGDLQCSEZGXLBLGYXTWMABCHECMWYJYZLLJJYHLG[DJLSLYGKDZPZXJYYZLWCXSZFGWYYDLY"
			+ "HCLJSCMBJHBLYZLYCBLYDPDQYSXQZBYTDKYXJY[CNRJMPDJGKLCLJBCTBJDDBBLBLCZQRPPXJCJLZCSHLTOLJNMDDDLNGKAQHQ"
			+ "HJGYKHEZNMSHRP[QQJCHGMFPRXHJGDYCHGHLYRZQLCYQJNZSQTKQJYMSZSWLCFQQQXYFGGYPTQWLMCRNFKKFSYYLQBMQAMMMYX"
			+ "CTPSHCPTXXZZSMPHPSHMCLMLDQFYQXSZYYDYJZZHQPDSZGLSTJBCKBXYQZJSGPSXQZQZRQTBDKYXZKHHGFLBCSMDLDGDZDBLZY"
			+ "YCXNNCSYBZBFGLZZXSWMSCCMQNJQSBDQSJTXXMBLTXZCLZSHZCXRQJGJYLXZFJPHYMZQQYDFQJJLZZNZJCDGZYGCTXMZYSCTLK"
			+ "PHTXHTLBJXJLXSCDQXCBBTJFQZFSLTJBTKQBXXJJLJCHCZDBZJDCZJDCPRNPQCJPFCZLCLZXZDMXMPHJSGZGSZZQLYLWTJPFSY"
			+ "ASMCJBTZKYCWMYTCSJJLJCQLWZMALBXYFBPNLSFHTGJWEJJXXGLLJSTGSHJQLZFKCGNNNSZFDEQFHBSAQTGYLBXMMYGSZLDYDQ"
			+ "MJJRGBJTKGDHGKBLQKBDMBYLXWCXYTTYBKMRTJZXQJBHLMHMJJZMQASLDCYXYQDLQCAFYWYXQHZ";

	public static String showNull2Empty(Object obj) {
		if (obj == null)
			return "";
		else
			// return ((String) (obj));
			return String.valueOf(obj);
	}

	public static String replace(String str, String oldStr, String newStr) {
		String ret = "";
		String tStr = str;
		int i = -1;
		int oldStrLen = oldStr.length();
		if (str == null || str.equals("") || oldStr == null || oldStr.equals("") || newStr == null)
			return str;

		do {
			i = tStr.indexOf(oldStr);
			if (i != -1) {
				ret += tStr.substring(0, i) + newStr;
				if (i + oldStrLen >= tStr.length()) {
					tStr = "";
					break;
				} else
					tStr = tStr.substring(i + oldStrLen);
			} else {
				ret += tStr;
			}
		} while (i != -1);

		return ret;
	}

	public static String MnemonicWords(String strinput) throws Exception {
		String ls_ReturnStr = "";
		try {
			for (int i = 0; i < strinput.length(); i++) {
				String s = strinput.substring(i, i + 1);
				byte b[] = s.getBytes();
				if (b.length == 1) {
					ls_ReturnStr = ls_ReturnStr + strinput.substring(i, i + 1).toUpperCase();
				} else {
					int li_SectorCode = (256 + b[0]) - 160;
					int li_PositionCode = (256 + b[1]) - 160;
					int li_SecPosCode = li_SectorCode * 100 + li_PositionCode;
					if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
						for (int j = 22; j >= 0; j--) {
							if (li_SecPosCode < li_SecPosValue[j])
								continue;
							ls_ReturnStr = ls_ReturnStr + lc_FirstLetter[j];
							break;
						}

					} else {
						int li_offset = ((li_SectorCode - 56) * 94 + li_PositionCode) - 1;
						if (li_offset >= 0 && li_offset <= 3007)
							ls_ReturnStr = ls_ReturnStr + ls_SecondSecTable.substring(li_offset, li_offset + 1);
						else
							ls_ReturnStr = ls_ReturnStr + strinput.charAt(i);
					}
				}
			}

		} catch (Exception e) {
			logger.error("异常", e);
		}
		return ls_ReturnStr.toLowerCase();
	}

	public static String parseCollection(String str) {
		String result = null;
		Matcher matcher = collectionPattern.matcher(str);
		if (matcher.find())
			result = matcher.group(0);
		return result.toLowerCase();
	}

	public static String fillZero(String str, int size) {
		String result;
		if (str.length() < size) {
			char s[] = new char[size - str.length()];
			for (int i = 0; i < size - str.length(); i++)
				s[i] = '0';

			result = new String(s) + str;
		} else {
			result = str;
		}
		return result;
	}

	public static String getStrOfLength(String str, int size) {
		String result = "";
		;
		for (int i = 0; i < size; i++) {
			result += str;
		}
		return result;
	}

	public static String converFirstUpper(String source) {
		if (source == null || source.equals(""))
			return source;

		return source.substring(0, 1).toUpperCase() + source.substring(1);
	}

	public static String converFirstLower(String source) {
		if (source == null || source.equals(""))
			return source;

		return source.substring(0, 1).toLowerCase() + source.substring(1);
	}

	/**
	 * 返回一个非null字符对象
	 * 
	 * @param obj
	 * @return String
	 */
	public static String toString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	private static String StringReplace(String from, String to, String source) {
		int i = source.indexOf(from);
		while (i > 0) {
			source = source.substring(0, i).concat(to).concat(source.substring(i + from.length()));
			i = source.indexOf(from);
		}
		return (source);

	}

	public static String deal(String val) {

		val = StringReplace("\"", "’", val);
		val = StringReplace(">", "’", val);
		val = StringReplace("<", "’", val);
		return val;
	}

	/**
	 * @param data
	 * @return HashMap
	 */
	@SuppressWarnings("unchecked")
	public static HashMap transParams(String data[][]) {
		HashMap params = new HashMap();
		if (data != null) {
			for (int i = 0; i < data.length; i++)
				params.put(data[i][0], data[i][1]);

		}
		return params;
	}

	/**
	 * 转换为GBK中文
	 * 
	 * @param strvalue
	 *            转换前的中文字符串
	 * @return GBK格式的中文
	 */
	public static String toChinese(String strvalue) {
		try {
			if (strvalue == null) {
				return null;
			} else {
				strvalue = new String(strvalue.getBytes("ISO8859_1"), "GBK");
				return strvalue;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将字符串的编码格式由 GBK 转为 ISO8859_1
	 * 
	 * @param strvalue
	 *            String，需要转换的字符串
	 * @return String，转换后的GBK格式的字符串
	 */
	public static String toISO8859_1(String value) {
		try {
			if (value == null) {
				return null;
			} else {
				value = new String(value.getBytes("GBK"), "ISO8859_1");
				return value;
			}
		} catch (Exception e) {
			return null;
		}
	}

	static {
		collectionPattern = Pattern.compile("[a-z]{3}[0-9]{2}[0-9a-z]");
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 是否为16进制数
	 * @param data
	 * @param length
	 * @return
	 */
	public static boolean isNumberHexString(String data, int length) {
		String str = "[0-9A-F]{" + length + "}";
		Pattern pattern = Pattern.compile(str);
		Matcher match = pattern.matcher(data.toUpperCase());
		return match.matches();
	}

	/**
	 * 把字符串数组转换成用逗号隔开的字符串
	 * 
	 * @param arrayStr
	 *            字符串数组
	 * @return 返回字符串，类似"01","02"...
	 */
	public static String array2StrOfCommaSplit(Object[] arrayStr) {
		if (arrayStr == null) {
			return "";
		}

		String strTemp = "";
		for (int i = 0; i < arrayStr.length; i++) {
			if (i == 0) {
				if (arrayStr.length > 1) {
					strTemp += "'" + arrayStr[i] + "',";
				} else {
					strTemp += "'" + arrayStr[i] + "'";
				}
			} else if (i == arrayStr.length - 1) {
				strTemp += "'" + arrayStr[i] + "'";
			} else {
				strTemp += "'" + arrayStr[i] + "',";
			}
		}

		return strTemp;
	}

	/**
	 * Check that the given String is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a String that purely consists of
	 * whitespace.
	 * <p>
	 * 
	 * <pre>
	 * StringUtil.hasLength(null) = false
	 * StringUtil.hasLength("") = false
	 * StringUtil.hasLength(" ") = true
	 * StringUtil.hasLength("Hello") = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not null and has length
	 * @see #hasText(String)
	 */
	public static boolean hasLength(String str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * Check whether the given String has actual text. More specifically,
	 * returns <code>true</code> if the string not <code>null</code>, its length
	 * is greater than 0, and it contains at least one non-whitespace character.
	 * <p>
	 * 
	 * <pre>
	 * StringUtil.hasText(null) = false
	 * StringUtil.hasText("") = false
	 * StringUtil.hasText(" ") = false
	 * StringUtil.hasText("12345") = true
	 * StringUtil.hasText(" 12345 ") = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not <code>null</code>, its
	 *         length is greater than 0, and it does not contain whitespace only
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean hasText(String str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

}
