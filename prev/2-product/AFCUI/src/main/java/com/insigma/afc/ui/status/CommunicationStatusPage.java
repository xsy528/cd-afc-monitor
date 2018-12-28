package com.insigma.afc.ui.status;

import com.insigma.afc.constant.ApplicationKey;
import com.insigma.commons.framework.view.status.IconStatusPage;

public class CommunicationStatusPage extends IconStatusPage {

	String commNormalImage = "/com/insigma/commons/ui/statusbar/CommNormal.png";

	String commErrorImage = "/com/insigma/commons/ui/statusbar/CommError.png";

	public CommunicationStatusPage() {
		setImage(new String[] { commNormalImage, commErrorImage });
		setStatusDesc(new String[] { "通讯在线", "通讯离线" });
		setKey(ApplicationKey.STATUS_COMMUNICATION);
	}
}
