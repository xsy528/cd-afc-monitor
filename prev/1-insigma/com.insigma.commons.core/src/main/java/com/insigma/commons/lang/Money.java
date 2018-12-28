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

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("unchecked")
public class Money implements Comparable, Serializable {

	private static final long serialVersionUID = 1L;

	public static final Money ZERO = new Money("0");

	private BigDecimal amount;

	private int decimal;

	private int roundingMode;

	/**
	 * 带一个Stirng对象的构造方法
	 * 
	 * @param amount
	 */
	public Money(String amount) {
		if (amount != null && !"".equals(amount.trim()))
			this.amount = init(new BigDecimal(amount));
		else
			this.amount = ZERO.getAmount();
	}

	/**
	 * 带一个BigDecimal对象的构造方法
	 * 
	 * @param amount
	 */
	public Money(BigDecimal amount) {
		this.amount = init(amount);
	}

	/**
	 * 带一个BigDecimal对象和一个int变量和一个int变量的构造方法
	 * 
	 * @param amount
	 * @param decimal
	 * @param roundingMode
	 */
	public Money(BigDecimal amount, int decimal, int roundingMode) {
		this.amount = init(amount, decimal, roundingMode);
	}

	/**
	 * 初始化方法将返回一个大双精度型
	 * 
	 * @param amount
	 * @return BigDecimal
	 */
	private BigDecimal init(BigDecimal amount) {
		return init(amount, 2, 4);
	}

	/**
	 * 初始化方法将返回一个大双精度型
	 * 
	 * @param amount
	 * @param decimal
	 * @param roundingMode
	 * @return BigDecimal
	 */
	private BigDecimal init(BigDecimal amount, int decimal, int roundingMode) {
		if (amount != null) {
			this.decimal = decimal;
			this.roundingMode = roundingMode;
			return amount.setScale(decimal, roundingMode);
		} else {
			return null;
		}
	}

	/**
	 * 返回一个大双精度型数量
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 比较amount对象和other对象的大小，如果amount>other则返回一个大于0的数，如果amount<other则返回一个小于0的数，其值是amount和other的差
	 * 
	 * @param obj
	 * @return int
	 */
	public int compareTo(Object obj) {
		Money other = (Money) obj;
		if (other == null || amount == null)
			return -1;
		else
			return amount.compareTo(other.amount);
	}

	/**
	 * 当对象为小于0的数时，则返回一个false
	 * 
	 * @return boolean
	 */
	public boolean isLessThanZero() {
		return compareTo(ZERO) == -1;
	}

	/**
	 * 返回一个Money类型的对象
	 * 
	 * @param other
	 * @return Money
	 */
	public Money add(Money other) {
		if (other != null && amount != null)
			return new Money(amount.add(other.amount), decimal, roundingMode);
		else
			return this;
	}

	/**
	 * 返回一个Money类型的对象
	 * 
	 * @param other
	 * @return Money
	 */
	public Money subtract(Money other) {
		if (other != null && amount != null)
			return new Money(amount.subtract(other.amount), decimal, roundingMode);
		else
			return this;
	}

	/**
	 * 返回一个Money类型的对象
	 * 
	 * @param value
	 * @return Money
	 */
	public Money multiply(int value) {
		if (amount == null)
			return this;
		else
			return new Money(amount.multiply(new BigDecimal(String.valueOf(value))), decimal, roundingMode);
	}

	/**
	 * 返回一个Money类型的对象
	 * 
	 * @param value
	 * @param roundingMode
	 * @return Money
	 */
	public Money multiply(BigDecimal value, int roundingMode) {
		if (amount == null || value == null)
			return this;
		else
			return new Money(amount.multiply(value).setScale(amount.scale(), roundingMode), decimal, this.roundingMode);
	}

	/**
	 * 比较当对象o的类型是否是Money时，则返回一个boolean类型的值
	 * 
	 * @param o
	 * @return boolean
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Money))
			return false;
		Money other = (Money) o;
		if (amount != null)
			return other.amount.equals(amount);
		else
			return false;
	}

	/**
	 * 返回调用映射的散列码
	 * 
	 * @return int
	 */
	public int hashCode() {
		if (amount == null)
			return -1;
		else
			return amount.hashCode();
	}

	/**
	 * 返回一个String对象
	 * 
	 * @return String
	 */
	public String toString() {
		if (amount == null) {
			return null;
		} else {
			StringBuffer result = new StringBuffer("￥");
			result.append(amount.toString());
			return result.toString();
		}
	}
}