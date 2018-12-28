package com.insigma.afc.ui.status;

import com.insigma.afc.constant.ApplicationKey;
import com.insigma.commons.framework.view.status.IconStatusPage;

public class DatabaseStatusPage extends IconStatusPage {

	String dataBaseNormalImage = "/com/insigma/commons/ui/statusbar/DBNormal.png";

	String dataBaseErrorImage = "/com/insigma/commons/ui/statusbar/DBError.png";

	public DatabaseStatusPage() {
		setImage(new String[] { dataBaseNormalImage, dataBaseErrorImage });
		setStatusDesc(new String[] { "数据库正常", "数据库异常" });
		setKey(ApplicationKey.STATUS_DATABASE);
	}
}
