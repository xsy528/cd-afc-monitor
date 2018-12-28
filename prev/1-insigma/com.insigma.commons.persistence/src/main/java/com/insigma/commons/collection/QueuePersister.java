/*
 * 日期：Jun 11, 2008
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.collection;

import java.io.Serializable;
import java.util.List;

/**
 * Ticket:
 * @author 华思远
 *
 * 描述: 这个接口定义了所有持久化类需要实现的方法
 * @param <E>
 */
public interface QueuePersister<E extends Serializable> {

	/**
	 * @param e 要持久化的对象
	 * @return true 持久化成功 <br>
	 *                false 持久化失败<br>
	 * 描述：这个方法把元素持久化到文件或者数据库的末尾
	 */
	boolean appendObj(E e);

	/**
	 * @return
	 *
	 * 描述：得到持久化中有但是内存中没有的第一个对象
	 */
	E retrieveObj();

	/**
	 * @return true 如果删除成功<br>
	 *                false 如果删除失败<br>
	 *
	 * 描述：这个方法删除文件或者数据库中最前面的一个元素
	 */
	boolean removeObj();

	/**
	 * @param mark 给持久化类打上标记表示和内存中同步的最后一个元素
	 *
	 */
	void setMark(E mark);

	/**
	 * @param size
	 * @return
	 *
	 * 描述：得到开始的若干个元素
	 */
	List<E> getFirstX(int size);

	/**
	 * @param size
	 * @return
	 *
	 * 描述：从持久化层中移除所有的元素
	 */
	List<E> removeFirstX(int size);
}
