/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.swt.SWT;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface View {

	int index() default -1;

	String group() default "";

	String type() default "Text";

	String label() default "";

	String postfix() default "";

	String description() default "";

	String format() default "";

	//20180316 shenchao @dataSource
	String[] list() default {};

		String[] values() default {};

	boolean checkable() default false;

	boolean checked() default false;

	String nullvalue() default "";

	int rowspan() default 1;

	int coloumnspan() default 1;

	/**
	 * 创建时间 2010-10-20 上午11:47:04<br>
	 * 描述：输入正则表达式验证
	 * 
	 * @return
	 */
	String regexp() default "";

	/**
	 * 创建时间 2010-10-13 上午09:58:47<br>
	 * 描述：输入长度限制，默认128位
	 * 
	 * @return
	 */
	int length() default 128;

	/**
	 * 创建时间 2015-4-21 <br>
	 * 描述：输入格式化长度限制，默认-1
	 * @return
	 */
	int formatLength() default -1;

	/**
	 * 创建时间 2015-4-21 <br>
	 * 描述：前补零，后补零，默认前补零
	 * @return
	 */
	boolean zero() default true;

	/**
	 * 文本框高度
	 * 
	 * @return
	 */
	int heightHint() default SWT.DEFAULT;

	/**
	 * 文本框自动换行
	 * 
	 * @return
	 */
	boolean wrap() default false;

	/**
	 * 控件的样式
	 * 
	 * @return
	 */
	int style() default SWT.DEFAULT;

	boolean modify() default true;

	//2013-7-25 shenchao 是否可编辑
	boolean editable() default true;

	//2013-7-25 shenchao 密码MD5格式化
	boolean hasMD5Format() default false;

	//2013-8-6 shenchao 文件大小限制 -1无限制
	long fileSize() default -1;

	//20180506 yangyang 进制说明
	int radix() default -1;

	/**
	 * 输入框密文显示 如：passwordEchoChar()='*',将wrap()置为false才有效。
	 * 
	 * @return
	 */
	char passwordEchoChar() default ' ';

	/**
	 * value显示
	 */
	// 20180502 hexingyue 使用columnview 中的convertor。
	//	Class<? extends ValueConvertor> convertor() default DefaultConvertor.class;

}
