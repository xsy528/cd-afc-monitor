/**
 * iFrameWork 框架 公共基础库
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-4
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.lang;

public class FourValue<E1, E2, E3, E4> {

	private E1 element1;

	private E2 element2;

	private E3 element3;

	private E4 element4;

	public E1 getElement1() {
		return element1;
	}

	public void setElement1(E1 element1) {
		this.element1 = element1;
	}

	public E2 getElement2() {
		return element2;
	}

	public void setElement2(E2 element2) {
		this.element2 = element2;
	}

	public E3 getElement3() {
		return element3;
	}

	public void setElement3(E3 element3) {
		this.element3 = element3;
	}

	public E4 getElement4() {
		return element4;
	}

	public void setElement4(E4 element4) {
		this.element4 = element4;
	}

}
