package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

@Dic(overClass = AFCSoftwareFileNameRegex.class)
public class AFCSoftwareFileNameRegex extends Definition {

	private static AFCSoftwareFileNameRegex instance = new AFCSoftwareFileNameRegex();

	public static AFCSoftwareFileNameRegex getInstance() {
		return instance;
	}

	// AA_XXX_LL_MM_NN_FILENAME
	@DicItem(name = "^\\d{2}_TP_04_\\d{2}_\\d{2}_.{0,24}", group = "SoftwareFileName")
	public static Integer TP_REGEX = 1;

	@DicItem(name = "^\\d{2}_\\w{3}_04_\\d{2}_\\d{2}_.{0,24}", group = "SoftwareFileName")
	public static Integer Dev_SOFT = 2;

}
