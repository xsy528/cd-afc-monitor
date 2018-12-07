package com.insigma.commons.ui.form;

public class BeanEditorLayoutData {

	public int inputControlWidth = 200;

	public int inputLabelWidth = 180;

	/**
	 * 设置单行显示的列数
	 */
	public int numColumn = 1;

	public BeanEditorLayoutData(int inputControlWidth, int inputLabelWidth, int numColumn) {
		super();
		this.inputControlWidth = inputControlWidth;
		this.inputLabelWidth = inputLabelWidth;
		this.numColumn = numColumn;
	}

	/**
	 * @return the inputControlWidth
	 */
	public int getInputControlWidth() {
		return inputControlWidth;
	}

	/**
	 * @param inputControlWidth the inputControlWidth to set
	 */
	public void setInputControlWidth(int inputControlWidth) {
		this.inputControlWidth = inputControlWidth;
	}

	/**
	 * @return the inputLabelWidth
	 */
	public int getInputLabelWidth() {
		return inputLabelWidth;
	}

	/**
	 * @param inputLabelWidth the inputLabelWidth to set
	 */
	public void setInputLabelWidth(int inputLabelWidth) {
		this.inputLabelWidth = inputLabelWidth;
	}

	/**
	 * @return the numColumn
	 */
	public int getNumColumn() {
		return numColumn;
	}

	/**
	 * @param numColumn the numColumn to set
	 */
	public void setNumColumn(int numColumn) {
		this.numColumn = numColumn;
	}

}