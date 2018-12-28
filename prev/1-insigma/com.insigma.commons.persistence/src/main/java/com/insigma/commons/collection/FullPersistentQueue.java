/*
 * 日期：Jun 10, 2008
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.collection;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ticket: 1770
 *
 * @author 华思远
 *
 * 描述: 这是一个全持久化队列，所有队列中的数据都保存在数据库或者文件中，这个队列继承自
 * ConcurrentLinkedQueue,由父类保证其线程安全性，这个队列可以设置其最大容量，超过最大
 * 容量的数据只存在数据库或文件中，具体的持久化方法由接口QueuePersister 提供 这个队列在offer, poll 操作时可能出现脏数据要谨慎使用
 * @param <E>
 */
public class FullPersistentQueue<E extends Serializable> extends ConcurrentLinkedQueue<E> {

	ReentrantLock lock = new ReentrantLock();

	/**
	 * serialized number
	 */
	private static final long serialVersionUID = -5775892789941386951L;

	/**
	 * 这个域定义了具体的持久化方法的实现方式
	 */
	private QueuePersister<E> persister;

	/**
	 * maxSize 定义了这个队列容器的最大容量
	 */
	private int maxSize = 1000;

	/**
	 * 是否可能含有脏数据
	 */
	private boolean dirty = false;

	/**
	 * @param persister
	 *            具体的持久化方法
	 * @param maxSize
	 *            内存中的最大数据
	 */
	public FullPersistentQueue(QueuePersister<E> persister, int maxSize) {
		super();
		this.persister = persister;
		this.maxSize = maxSize;

	}

	/**
	 * @param persister
	 *            具体的持久化方法
	 */
	public FullPersistentQueue(QueuePersister<E> persister) {
		super();
		this.persister = persister;
	}

	/**
	 *
	 *
	 * 描述：通过持久化类来初始化元素
	 */
	public void initCapacity() {
		List<E> l = persister.getFirstX(maxSize);
		for (E e : l) {
			super.offer(e);
			persister.setMark(e);
		}
	}

	/**
	 * @return true 可能含有脏数据 <br>
	 *         false 不可能含有脏数据 <br>
	 *
	 * 描述：脏数据是指内存中和数据库中可能存在的不一样的情况
	 */
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * @param e
	 *            需要入队列的元素 入队列时若队列不满则在内存中和文件中都会保存一份，若队列满则只会把数据存放到文件中 ＠returne
	 * @return true 入队列成功<br>
	 *         false 入队列失败
	 */
	@Override
	public boolean offer(E e) {
		if (e == null) {
			throw new NullPointerException();
		}

		this.lock.lock();
		try {
			boolean result1 = persister.appendObj(e);
			if (result1 == false) {
				return false;
			}
			if (size() >= maxSize) {
				return true;
			}
			// 可能出现脏数据
			dirty = true;
			boolean result2 = super.offer(e);
			if (result1 != result2) {
				return false;
			}
			persister.setMark(e);
			// 不可能出现脏数据
			dirty = false;
			return true;
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 *
	 * @return E 出队列的元素
	 *
	 * 如果从持久化中删除对象错误或者队列为空则返回null
	 *
	 */
	@Override
	public E poll() {
		this.lock.lock();

		try {

			// 如果为空则返回null
			if (isEmpty()) {
				return null;
			}
			// 从数据库中删除第一个
			boolean result1 = persister.removeObj();

			if (!result1) {
				return null;
			}
			// 可能出现脏数据
			dirty = true;
			// 从队列中取出第一个
			E e = super.poll();
			if (size() < maxSize) {
				// 从持久化文件或者数据库中取出第一个元素
				E tail = persister.retrieveObj();
				if (tail != null) {
					super.offer(tail);
					persister.setMark(tail);
				}
			}
			dirty = false;

			// 返回队列中的第一个
			return e;
		} finally {
			this.lock.unlock();
		}
	}

}
