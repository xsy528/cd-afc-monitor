/*
 * 日期：2008-12-9
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.framework.view.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.thread.EnhancedThread;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.dialog.FileDialog;
import com.insigma.commons.ui.widgets.Shell;

public class XlsFileCreaterUtil {

	private static Log logger = LogFactory.getLog(EnhancedThread.class);

	//	private final HSSFWorkbook wb;

	private final XSSFWorkbook wbs;

	private final String fileName;

	private String sheetName;

	//xls:0 xlsx
	private int fileType;

	public XlsFileCreaterUtil(String fileName) {
		super();
		this.fileName = fileName;
		//		wb = new HSSFWorkbook();
		wbs = new XSSFWorkbook();
	}

	/**
	 * 将信息写入EXECL文件
	 * 
	 * @param sheetName
	 * @param list
	 */
	public void writeToXls(String sheetName, List<Object[]> list) {
		if (sheetName == null || list.size() == 0) {
			return;
		}

		XSSFSheet sheet = wbs.createSheet(sheetName);
		writeObjectList(sheet, 0, 0, list);
	}

	/**
	 * 生成文件
	 * 
	 * @throws Exception
	 */
	public void closeFile() throws Exception {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(fileName);
			wbs.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			logger.error("文件操作失败", e);
			throw e;
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					logger.error("无法关闭文件流", e);
				}
			}
		}

	}

	/**
	 * 往 sheet 中加入 list
	 * 
	 * @param sheet
	 * @param initialRow
	 *            起始行
	 * @param initialCol
	 *            起始列
	 * @param list
	 *            需要加入的数据 list
	 * @return int[0] 列表的列数 int[1] 列表的行数
	 */
	private static void writeObjectList(XSSFSheet sheet, int initialRow, int initialCol, List<?> list) {
		if (null == list || list.isEmpty()) {
			return;
		}
		int height = 0;
		int tempHeight = initialRow;
		XSSFRow headRow = null;
		XSSFRow valueRow = null;

		Object[] objLength = (Object[]) list.get(0);

		int count = 0;
		for (Object entity : list) {
			if (count == 0) {
				for (int i = 0; i < objLength.length; i++) {
					sheet.setColumnWidth(i, 40 * (Integer) objLength[i]);
					//					sheet.autoSizeColumn((short) i);
				}
				count++;
				continue;
			}

			if (entity instanceof Object[]) {
				Object[] temp = (Object[]) entity;
				for (int i = 0; i < temp.length; i++) {
					if (count == 1) {
						// 构造标题行
						if (sheet.getRow(tempHeight) == null) {
							headRow = sheet.createRow(tempHeight);
						} else {
							headRow = sheet.getRow(tempHeight);
						}
						headRow.createCell(initialCol + i).setCellValue(new XSSFRichTextString(temp[i].toString()));
					} else {
						// 构造数据行
						tempHeight = initialRow + height;
						if (sheet.getRow(tempHeight) == null) {
							valueRow = sheet.createRow(tempHeight);
						} else {
							valueRow = sheet.getRow(tempHeight);
						}
						valueRow.createCell(initialCol + i).setCellValue(new XSSFRichTextString(temp[i].toString()));
					}
				}
			}
			height++;
			count++;
		}
	}

	/**
	 * 往 sheet 中加入 list
	 * 
	 * @param sheet
	 * @param initialRow
	 *            起始行
	 * @param initialCol
	 *            起始列
	 * @param list
	 *            需要加入的数据 list
	 * @return int[0] 列表的列数 int[1] 列表的行数
	 */
	private static int[] writeObjectList(HSSFSheet sheet, int initialRow, int initialCol, List<?> list) {
		String str = "";

		if (null == list || list.isEmpty()) {
			return new int[] { 0, 0 };
		}
		int width = 0;
		int height = 0;

		// 构造标题行
		HSSFRow headRow = sheet.getRow(initialRow);
		if (headRow == null) {
			headRow = sheet.createRow(initialRow);
		}
		height++;

		for (Object entity : list) {
			width = 0;

			// 构造数据行
			HSSFRow valueRow = sheet.createRow(initialRow + height);

			if (!isUserDefinedClass(entity.getClass())) {
				// 处理基本数据类型
				valueRow.createCell(initialCol + width).setCellValue(new HSSFRichTextString(entity.toString()));
			} else {
				// 处理自定义数据类型
				Field[] fields = entity.getClass().getDeclaredFields();

				for (Field field : fields) {
					View view = field.getAnnotation(View.class);
					if (null == view) {
						continue;
					}
					headRow.createCell(initialCol + width).setCellValue(new HSSFRichTextString(view.label()));
					Object value = BeanUtil.getValue(entity, field.getName());
					// if (null == value) {
					// continue;
					// }
					if (value instanceof List) {
						int size[] = writeObjectList(sheet, initialRow + height, initialCol + width, (List<?>) value);
						width += size[0];
						height += size[1];
					} else if (null != value && value.getClass().isArray()) {
						Class<?> type = value.getClass().getComponentType();
						if (isUserDefinedClass(type)) {
							List<Object> l = new ArrayList<Object>();
							for (Object subObj : (Object[]) value) {
								l.add(subObj);
							}
							height++;
							int size[] = writeObjectList(sheet, initialRow + height, initialCol + width, l);
							width += size[0];
							height += size[1];
						} else {
							int size[] = writeObjectList(sheet, initialRow + height, initialCol + width,
									Arrays.asList(value));
							width += size[0];
							height += size[1];
						}

					} else {
						valueRow.createCell(initialCol + width).setCellValue(new HSSFRichTextString(value.toString()));
					}
					width++;
				}
				width--;
			}
			height++;
		}
		return new int[] { width, height - 1 };
	}

	/**
	 * @param clazz
	 * @return <code>true</code> OR <code>false</code>
	 * @Description 用于判断类型是否为用户自定义的类型
	 */
	public static boolean isUserDefinedClass(Class<?> clazz) {
		if (isAssignableFromPrimitiveOrNumber(clazz)) {
			return false;
		}
		if (clazz.isArray()) {
			return false;
		}
		if ((Date.class == clazz) || Date.class.isAssignableFrom(clazz)) {
			return false;
		}
		if (String.class == clazz) {
			return false;
		}
		if (Collection.class.isAssignableFrom(clazz)) {
			return false;
		}
		if (List.class.isAssignableFrom(clazz)) {
			return false;
		}
		return true;
	}

	private static boolean isAssignableFromPrimitiveOrNumber(Class<? extends Object> clazz) {
		// primitive
		if (clazz.isPrimitive()) {
			return true;
		}
		// Wrap Number
		if ((null != clazz.getSuperclass()) && Number.class.isAssignableFrom(clazz.getSuperclass())) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {

		List<Object[]> list = new ArrayList<Object[]>();
		Object[] obj0 = new Object[] { 190, 255 };
		Object[] obj1 = new Object[] { "但是有个问题，含有汉字的列没有完全展开来", "但是有个问题，含有汉字的列没有完全展开来但是有个问题，含有汉字的列没有完全展开来" };
		Object[] obj2 = new Object[] { "1", "2" };
		Object[] obj3 = new Object[] { "11", "22" };
		list.add(obj0);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		//		User user1 = new User("1", "2");
		//		User user2 = new User("11", "22");
		//		list.add(user1);
		//		list.add(user2);

		String selectedPath = "";

		Shell shell = new Shell(Display.getDefault().getActiveShell(), SWT.NO_FOCUS | SWT.DIALOG_TRIM);

		FileDialog fd = new FileDialog(shell, SWT.SAVE);
		selectedPath = fd.open();

		XlsFileCreaterUtil creater = new XlsFileCreaterUtil(selectedPath);
		creater.writeToXls("xxss", list);

		try {
			creater.closeFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static class User {

		@View(label = "mingzi")
		private String name;
		@View(label = "bianhao")
		private String Id;

		public User(String name, String id) {
			this.name = name;
			this.Id = id;
		}

		public String getId() {
			return Id;
		}

		public void setId(String id) {
			Id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
