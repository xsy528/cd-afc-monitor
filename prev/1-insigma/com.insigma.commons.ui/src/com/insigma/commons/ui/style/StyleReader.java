package com.insigma.commons.ui.style;

import java.io.InputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class StyleReader {

	public static void read(String fileName) {
		InputStream fileContent = ClassLoader.getSystemResourceAsStream(fileName);
		if (fileContent != null) {
			XStream xstream = new XStream(new DomDriver());
			xstream.processAnnotations(Style.class);
			Style style = (Style) xstream.fromXML(fileContent);
			StyleManager.setStyle(style);
		}
	}

	public static void main(String[] args) {

		Style style = new Style();

		{
			TableStyle t = new TableStyle();
			Column c = new Column();
			c.setText("text");
			t.getColumns().add(c);
			style.getTable().add(t);
		}

		{
			TableStyle t = new TableStyle();
			Column c = new Column();
			c.setText("text");
			t.getColumns().add(c);
			style.getTable().add(t);
		}

		XStream xstream = new XStream();
		xstream.processAnnotations(Style.class);

		String xml = xstream.toXML(style);
		System.out.println(xml);
	}
}
