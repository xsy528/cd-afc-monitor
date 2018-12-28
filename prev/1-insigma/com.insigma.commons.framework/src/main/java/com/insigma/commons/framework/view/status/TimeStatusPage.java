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
import com.insigma.commons.ui.control.TimeLabel;

public class TimeStatusPage extends StatusPage {

	public TimeStatusPage() {
		this.composite = TimeLabel.class;
		this.width = 100;
	}

}
