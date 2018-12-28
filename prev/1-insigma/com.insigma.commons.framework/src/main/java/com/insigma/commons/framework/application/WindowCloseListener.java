/**
 * 
 */
package com.insigma.commons.framework.application;

import org.eclipse.swt.graphics.Image;

/**  
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public interface WindowCloseListener {

	public boolean prepare();

	public void beforeClose();

	public void afterClose();

	public String getName();

	public Image getImage();

}
