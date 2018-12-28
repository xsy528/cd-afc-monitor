package com.insigma.commons.ui.provider;

public interface IComboDataSource<T> {

	public String[] getText();

	public T[] getValue();
}
