/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.control;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.widgets.Combo;
import com.insigma.commons.ui.widgets.EnhanceComposite;

public class PageNavigator extends EnhanceComposite {

	private Spinner gotoPage;

	private long totalRecord;

	private int currentPage = 1;

	private int pageSize = 20;

	private IPageChangedListener pageChangedListener;

	private Button pageFirst;

	private Button prevPage;

	private Button nextPage;

	private Button pageLast;

	private Label pagelblLabel;

	private Label lblsum;

	private Button button;

	private Combo comPageSize;

	private Integer[] pageSizeValue = new Integer[] { 20, 50, 80, 100 };

	public PageNavigator(Composite parent, int style, Integer[] pageSizeValue) {
		super(parent, style);
		this.pageSizeValue = Arrays.copyOf(pageSizeValue, pageSizeValue.length);
		doCreate();
	}

	public PageNavigator(Composite parent, int style) {
		super(parent, style);
		doCreate();
	}

	private void doCreate() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 15;

		this.setLayout(layout);

		final Label label_1 = new Label(this, SWT.NONE);
		GridData gd_label_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_1.widthHint = 46;
		label_1.setLayoutData(gd_label_1);
		// label_1.setBounds(5, 10, 42, 22);
		label_1.setText("当前页:");

		pagelblLabel = new Label(this, SWT.NONE);
		GridData gd_pagelblLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_pagelblLabel.widthHint = 100;
		pagelblLabel.setLayoutData(gd_pagelblLabel);
		// pagelblLabel.setBounds(5, 10, 250, 22);

		pageFirst = new Button(this, SWT.NONE);
		GridData gd_pageFirst = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_pageFirst.widthHint = 34;
		pageFirst.setLayoutData(gd_pageFirst);
		// pageFirst.setBounds(303, 5, 42, 22);
		pageFirst.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				pageFirst.setEnabled(false);
				if (pageChangedListener != null) {
					pageChangedListener.pageChanged(currentPage, currentPage = 1);
				}
			}
		});
		pageFirst.setText("<<");

		prevPage = new Button(this, SWT.NONE);
		GridData gd_prevPage = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_prevPage.widthHint = 28;
		prevPage.setLayoutData(gd_prevPage);
		// prevPage.setBounds(350, 5, 42, 22);
		prevPage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				prevPage.setEnabled(false);
				if (pageChangedListener != null) {
					pageChangedListener.pageChanged(currentPage, currentPage -= 1);
				}
			}
		});
		prevPage.setText("<");

		nextPage = new Button(this, SWT.NONE);
		GridData gd_nextPage = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_nextPage.widthHint = 27;
		nextPage.setLayoutData(gd_nextPage);
		// nextPage.setBounds(400, 5, 42, 22);
		nextPage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				nextPage.setEnabled(false);
				if (pageChangedListener != null) {
					pageChangedListener.pageChanged(currentPage, currentPage += 1);
				}
			}
		});
		nextPage.setText(">");

		pageLast = new Button(this, SWT.NONE);
		GridData gd_pageLast = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_pageLast.widthHint = 34;
		pageLast.setLayoutData(gd_pageLast);
		// pageLast.setBounds(450, 5, 42, 22);
		pageLast.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				pageLast.setEnabled(false);
				if (pageChangedListener != null) {
					pageChangedListener.pageChanged(currentPage, currentPage = getTotalPage().intValue());
				}
			}
		});
		pageLast.setText(">>");

		final Label label = new Label(this, SWT.NONE);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label.widthHint = 34;
		label.setLayoutData(gd_label);
		// label.setBounds(500, 10, 30, 22);
		label.setText("转到:");

		gotoPage = new Spinner(this, SWT.BORDER);
		GridData gd_gotoPage = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_gotoPage.widthHint = 50;
		gotoPage.setLayoutData(gd_gotoPage);
		gotoPage.setMinimum(1);
		// gotoPage.setBounds(485, 5, 77, 21);
		gotoPage.setMaximum(Integer.MAX_VALUE);
		final Label lblPage = new Label(this, SWT.NONE);
		GridData gd_lblPage = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblPage.widthHint = 19;
		lblPage.setLayoutData(gd_lblPage);
		// lblPage.setBounds(565, 10, 12, 22);
		lblPage.setText("页");
		gotoPage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 13 || e.keyCode == 16777296) {
					if (pageChangedListener != null) {
						if (gotoPage.getSelection() < 1 || gotoPage.getSelection() > getTotalPage().intValue()) {
							MessageForm.Message("错误信息", "表格里没有第" + gotoPage.getSelection() + "页。");
							return;
						}
						pageChangedListener.pageChanged(currentPage, currentPage = gotoPage.getSelection());
					}
					e.doit = true;
				}
			}
		});

		button = new Button(this, SWT.NONE);
		GridData gd_button = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button.widthHint = 51;
		button.setLayoutData(gd_button);
		// button.setBounds(580, 5, 36, 22);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if (pageChangedListener != null) {
					if (gotoPage.getSelection() < 1 || gotoPage.getSelection() > getTotalPage().intValue()) {
						MessageForm.Message("错误信息", "表格里没有第" + gotoPage.getSelection() + "页。");
						return;
					}
					pageChangedListener.pageChanged(currentPage, currentPage = gotoPage.getSelection());
				}
			}
		});
		button.setText("跳转");

		Label lblpage = new Label(this, SWT.NONE);
		GridData gd_lblpage = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblpage.widthHint = 60;
		lblpage.setLayoutData(gd_lblpage);
		// lblsum.setBounds(625, 10, 264, 22);
		lblpage.setText("每页显示");

		String[] pageSizeItems = new String[pageSizeValue.length];
		{
			String strNumbersPerPage = System.getProperty("numbersPerPage");
			if (null != strNumbersPerPage && !"".equals(strNumbersPerPage)) {
				String[] numberPerPageItems = strNumbersPerPage.split(",");
				pageSizeValue = new Integer[numberPerPageItems.length];
				pageSizeItems = numberPerPageItems;
				for (int i = 0; i < numberPerPageItems.length; i++) {
					pageSizeValue[i] = Integer.valueOf(numberPerPageItems[i]);
				}
			} else {
				for (int i = 0; i < pageSizeValue.length; i++) {
					pageSizeItems[i] = String.valueOf(pageSizeValue[i]);
				}
			}
		}

		comPageSize = new Combo(this, SWT.NONE);
		GridData gd_comPageSize = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_comPageSize.widthHint = 40;
		comPageSize.setValues(pageSizeValue);

		comPageSize.setItems(pageSizeItems);
		comPageSize.setObjectValue(pageSizeValue[0]);
		comPageSize.setLayoutData(gd_comPageSize);
		comPageSize.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				pageSize = Integer.valueOf(comPageSize.getObjectValue().toString());
				if (pageChangedListener != null) {
					pageChangedListener.pageChanged(currentPage, currentPage = 1);
				}
			}
		});

		lblsum = new Label(this, SWT.NONE);
		GridData gd_lblsum = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblsum.widthHint = 108;
		lblsum.setLayoutData(gd_lblsum);
		// lblsum.setBounds(625, 10, 264, 22);
		lblsum.setText("条");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		refresh();
	}

	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public Long getTotalPage() {
		if (totalRecord < 0)
			return -1L;

		long count = totalRecord / pageSize;
		if (totalRecord % pageSize > 0) {
			count++;
		}
		return count;
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		if (currentPage < 1) {
			this.currentPage = 1;
		}
	}

	public int getSelectedPage() {
		if (gotoPage.getSelection() > 1) {
			return gotoPage.getSelection();
		}
		return 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时自动调整为20.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 20;
		}
	}

	/**
	 * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre())
			return currentPage - 1;
		else
			return currentPage;
	}

	/**
	 * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext())
			return currentPage + 1;
		else
			return currentPage;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (currentPage + 1 <= getTotalPage());
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (currentPage - 1 >= 1);
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((currentPage - 1) * pageSize) + 1;
	}

	public IPageChangedListener getPageChangedListener() {
		return pageChangedListener;
	}

	public void setPageChangedListener(IPageChangedListener pageChangedListener) {
		this.pageChangedListener = pageChangedListener;
	}

	public void reset() {
		setCurrentPage(1);
		setTotalRecord(0);
		refresh();
	}

	public void refresh() {
		if (getShell().isDisposed()) {
			return;
		}
		Long totalPage = getTotalPage();
		if (totalPage < currentPage) {
			int newCurr = 1; // 如果总页数小于当前页，把当前页设置为1
			setCurrentPage(newCurr);
			if (totalRecord > 0 && pageChangedListener != null) {// 如果总条数为0，不需要重新查询
				pageChangedListener.pageChanged(1, newCurr);
			}
		}

		pagelblLabel.setText(String.format("第%d页/共 %d页    ", currentPage, totalPage));

		lblsum.setText("条/共" + totalRecord + "条          ");

		nextPage.setEnabled(true);
		pageFirst.setEnabled(true);
		prevPage.setEnabled(true);
		pageLast.setEnabled(true);
		gotoPage.setSelection(currentPage);

		if (totalPage <= 1) {
			gotoPage.setEnabled(false);
			gotoPage.setSelection(0);
			button.setEnabled(false);
		} else {
			gotoPage.setEnabled(true);
			button.setEnabled(true);
		}

		if (isHasPre()) {
			pageFirst.setEnabled(true);
			prevPage.setEnabled(true);
		} else {
			pageFirst.setEnabled(false);
			prevPage.setEnabled(false);
		}
		if (isHasNext()) {
			pageLast.setEnabled(true);
			nextPage.setEnabled(true);
		} else {
			pageLast.setEnabled(false);
			nextPage.setEnabled(false);
		}

	}

}
