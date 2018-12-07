/**
 * 
 */
package com.insigma.afc.monitor.dialog.sle;

import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.View;

public class VersionItem {

	@View(label = "版本信息")
	@ColumnView(name = "版本信息")
	private String name = "";

	@View(label = "版本值")
	@ColumnView(name = "版本值" /*,el = "${this.name}/${this.version}"*/)
	private String version = "";

	public VersionItem(String name, String version) {
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}