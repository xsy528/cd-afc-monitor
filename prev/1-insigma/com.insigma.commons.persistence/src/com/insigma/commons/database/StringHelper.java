package com.insigma.commons.database;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import sun.io.ByteToCharConverter;
//import sun.io.CharToByteConverter;

@SuppressWarnings("deprecation")
public class StringHelper {

	private static Log logger = LogFactory.getLog(StringHelper.class);

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

	public StringHelper() {
	}

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

	//    public static String ChineseStringToAscii(String s) {
	//        try {
	//            CharToByteConverter toByte = CharToByteConverter.getConverter("GBK");
	//            byte orig[] = toByte.convertAll(s.toCharArray());
	//            char dest[] = new char[orig.length];
	//            for (int i = 0; i < orig.length; i++)
	//                dest[i] = (char) (orig[i] & 0xff);
	//
	//            return new String(dest);
	//        } catch (Exception e) {
	//        	logger.error("异常",e);
	//        }
	//        return s;
	//    }
	//
	//    public static String ChineseStringToUTF(String s) {
	//        try {
	//            CharToByteConverter toByte = CharToByteConverter.getConverter("UTF-8");
	//            byte orig[] = toByte.convertAll(s.toCharArray());
	//            char dest[] = new char[orig.length];
	//            for (int i = 0; i < orig.length; i++)
	//                dest[i] = (char) (orig[i] & 0xff);
	//
	//            return new String(dest);
	//        } catch (Exception e) {
	//        	logger.error("异常",e);
	//        }
	//        return s;
	//    }
	//
	//    public static String AsciiToChineseString(String s) {
	//        char orig[] = s.toCharArray();
	//        byte dest[] = new byte[orig.length];
	//        for (int i = 0; i < orig.length; i++)
	//            dest[i] = (byte) (orig[i] & 0xff);
	//
	//        try {
	//            ByteToCharConverter toChar = ByteToCharConverter.getConverter("GBK");
	//            return new String(toChar.convertAll(dest));
	//        } catch (Exception e) {
	//        	logger.error("异常",e);
	//        }
	//        return s;
	//    }

	public static synchronized String regexReplace(String regularExpression, String sub, String input) {
		Pattern pattern = PatternFactory.getPattern(regularExpression);
		Matcher matcher = pattern.matcher(input);
		StringBuffer sb = new StringBuffer();
		for (; matcher.find(); matcher.appendReplacement(sb, sub))
			;
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static synchronized boolean exist(String regularExpression, String input) {
		Pattern pattern = PatternFactory.getPattern(regularExpression);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
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

	public static synchronized String dealOrderBy(String hql) {
		String regexp = "order\\s+by";
		String spaceReg = "\\s+";
		hql = regexReplace(spaceReg, " ", hql);
		String orderHql = hql;
		if (exist(regexp, orderHql.toLowerCase())) {
			orderHql = regexReplace(regexp, GlobalNames.ORDER_BY, orderHql.toLowerCase());
			int orderByIndex = orderHql.lastIndexOf(GlobalNames.ORDER_BY);
			hql = hql.substring(0, orderByIndex) + GlobalNames.ORDER_BY + " " + hql.substring(orderByIndex + 8);
		}
		return hql;
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
}
