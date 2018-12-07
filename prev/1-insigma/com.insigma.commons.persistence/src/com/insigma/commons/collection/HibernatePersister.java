/*
 * 日期：Jun 11, 2008
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.collection;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class HibernatePersister<E extends Serializable> implements QueuePersister<E> {

	protected Session session;

	protected E mark;

	public HibernatePersister(Session session) {
		super();
		if (session == null) {
			throw new NullPointerException();
		}
		this.session = session;
	}

	public boolean appendObj(E s) {
		Transaction t = null;
		try {
			t = session.beginTransaction();
			session.save(s);
			t.commit();
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			return false;
		}
		return true;
	}

	public boolean removeObj() {
		Transaction t = null;
		try {
			t = session.beginTransaction();
			Serializable s = getFirstObj();
			session.delete(s);
			t.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				t.rollback();
			}
			return false;
		}
	}

	public E retrieveObj() {
		try {
			E s = getMarkedObj();
			return s;
		} catch (Exception e) {
			return null;
		}

	}

	public List<E> removeFirstX(int size) {
		List<E> rL = getFirstX(size);
		Transaction t = null;

		if (rL == null) {
			return null;
		}
		try {
			t = session.beginTransaction();
			for (E e : rL) {
				session.delete(e);
			}
			t.commit();
			return rL;
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				t.rollback();
			}
			return null;
		}
	}

	protected abstract E getFirstObj();

	protected abstract E getMarkedObj();

	public E getMark() {
		return mark;
	}

	public void setMark(E mark) {
		this.mark = mark;
	}

}
