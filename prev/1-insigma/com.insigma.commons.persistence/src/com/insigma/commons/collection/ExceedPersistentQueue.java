/*
 * 日期：Jun 12, 2008
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.collection;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ExceedPersistentQueue<E extends Serializable> extends ConcurrentLinkedQueue<E> {

	/**
	 * serialized id
	 */
	private static final long serialVersionUID = 5772159905797882531L;

	/**
	 * explicit lock for thread safe garentee
	 */
	private ReentrantLock lock = new ReentrantLock();

	/**
	 * 这个域定义了具体的持久化方法的实现方式
	 */
	private QueuePersister<E> persister;

	/**
	 * maxSize 定义了这个队列容器的最大容量
	 */
	private int maxSize = 1000;

	/**
	 * 队列的总大小
	 */
	private int totalSize = 0;

	/**
	 * 队列的内存大小
	 */
	private int memorySize = 0;

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
	public ExceedPersistentQueue(QueuePersister<E> persister, int maxSize) {
		super();
		this.persister = persister;
		this.maxSize = maxSize;

	}

	/**
	 * @param persister
	 *            具体的持久化方法 默认大小为1000个元素
	 */
	public ExceedPersistentQueue(QueuePersister<E> persister) {
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
	 *            需要入队列的元素 入队列时若队列满则会被存在持久层中，若不满则放在内存中
	 * @return true 入队列成功<br>
	 *         false 入队列失败
	 *
	 * @throws NullPointerException
	 *             if the element to be offered is null
	 */
	@Override
	public boolean offer(E e) {
		if (e == null) {
			throw new NullPointerException();
		}

		lock.lock();
		try {
			boolean memoryOffer = false;
			boolean fileOffer = false;
			if (size() < maxSize) {
				// 如果队列不满则方在内存中
				memoryOffer = super.offer(e);
				persister.setMark(e);
			} else {
				// 反之则放在持久层中
				fileOffer = persister.appendObj(e);
			}
			if (memoryOffer) {
				memorySize++;
			}
			if (memoryOffer || fileOffer) {
				totalSize++;
			}

			return memoryOffer || fileOffer;

		} finally {
			lock.unlock();
		}
	}

	/**
	 *
	 * @return E 出队列的元素
	 *
	 * 从内存中出队列如果队列中的元素小于允许最大元素的一半则
	 *
	 */
	@Override
	public E poll() {
		lock.lock();

		try {

			// 得到队列头
			E e = super.poll();

			if (e == null) {
				return null;
			}

			memorySize--;
			totalSize--;

			// 如果队列中只有不到一半的元素则从持久化层中取出
			if (memorySize < maxSize / 2) {
				List<E> fileList = persister.removeFirstX(maxSize - memorySize);
				if (fileList != null) {
					for (E e2 : fileList) {
						if (super.offer(e2) == false) {
							dirty = true;
						} else {
							persister.setMark(e2);
						}
					}
					memorySize += fileList.size();
				}
			}
			return e;
		} finally {
			lock.unlock();
		}
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(int memorySize) {
		this.memorySize = memorySize;
	}
}
