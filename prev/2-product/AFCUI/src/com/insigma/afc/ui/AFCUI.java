package com.insigma.afc.ui;

import com.insigma.afc.ui.widgets.provider.ByteArrayTextProvider;
import com.insigma.afc.ui.widgets.provider.CheckBoxGroupProvider;
import com.insigma.afc.ui.widgets.provider.ComboGroupProvider;
import com.insigma.afc.ui.widgets.provider.ComboProvider;
//import com.insigma.afc.ui.widgets.provider.FileSectionProvider;
import com.insigma.afc.ui.widgets.provider.ObjectTreeProvider;
import com.insigma.afc.ui.widgets.provider.TimeSectionProvider;
import com.insigma.afc.ui.widgets.provider.TimeSectionV2Provider;
import com.insigma.commons.ui.form.WidgetsFactory;
import com.insigma.commons.ui.form.widget.BitComboProvider;
import com.insigma.commons.ui.style.StyleReader;

public class AFCUI {

	public static void initialize() {

		WidgetsFactory.getInstance().addWidgetProvider(new ObjectTreeProvider());
		WidgetsFactory.getInstance().addWidgetProvider(new TimeSectionProvider());
		WidgetsFactory.getInstance().addWidgetProvider(new TimeSectionV2Provider());
		WidgetsFactory.getInstance().addWidgetProvider(new ByteArrayTextProvider());
		WidgetsFactory.getInstance().addWidgetProvider(new ComboGroupProvider());
		WidgetsFactory.getInstance().addWidgetProvider(new CheckBoxGroupProvider());
		WidgetsFactory.getInstance().addWidgetProvider(new ComboProvider());
		WidgetsFactory.getInstance().addWidgetProvider(new BitComboProvider());
		//WidgetsFactory.getInstance().addWidgetProvider(new FileSectionProvider());

		StyleReader.read("style.xml");
	}

}
