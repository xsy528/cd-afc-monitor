/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-4
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.framework.view.status;

import com.insigma.commons.framework.application.StatusPage;
import com.insigma.commons.ui.control.DateLabel;

public class DateStatusPage extends StatusPage {
	public DateStatusPage() {
		this.composite = DateLabel.class;
		this.width = 160;

	}
}
