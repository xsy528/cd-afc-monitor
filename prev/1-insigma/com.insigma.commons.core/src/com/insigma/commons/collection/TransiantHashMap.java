package com.insigma.commons.collection;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class TransiantHashMap implements Serializable {
	private static final long serialVersionUID = 1L;

	int MAXLENGTH;

	List keys;

	List values;

	public TransiantHashMap() {
		this(20);
	}

	public TransiantHashMap(int size) {
		MAXLENGTH = 20;
		keys = null;
		values = null;
		MAXLENGTH = size;
		keys = new ArrayList();
		values = new ArrayList();
	}

	public void put(Object key, Object value) {
		if (keys.size() >= MAXLENGTH)
			remove(0);
		remove(key);
		keys.add(key);
		values.add(value);
	}

	public Object get(Object key) {
		if (key == null)
			return null;
		Iterator iterValues = values.iterator();
		Object iterKey = null;
		Object iterValue = null;
		for (Iterator iterKeys = keys.iterator(); iterKeys.hasNext();) {
			Object tmpKey = iterKeys.next();
			Object tmpValue = iterValues.next();
			if (key.equals(tmpKey)) {
				iterKey = tmpKey;
				iterValue = tmpValue;
				break;
			}
		}

		remove(iterKey);
		put(iterKey, iterValue);
		return iterValue;
	}

	public List keySet() {
		return keys;
	}

	public List values() {
		return values;
	}

	public void remove(Object key) {
		if (key == null)
			return;
		Iterator iterValues = values.iterator();
		Object removeKey = null;
		Object removeValue = null;
		for (Iterator iterKeys = keys.iterator(); iterKeys.hasNext();) {
			Object tmpKey = iterKeys.next();
			Object tmpValue = iterValues.next();
			if (key.equals(tmpKey)) {
				removeKey = tmpKey;
				removeValue = tmpValue;
				break;
			}
		}

		keys.remove(removeKey);
		values.remove(removeValue);
	}

	public void remove(int index) {
		if (index < 0 || index > keys.size()) {
			return;
		} else {
			keys.remove(index);
			values.remove(index);
			return;
		}
	}
}
