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
