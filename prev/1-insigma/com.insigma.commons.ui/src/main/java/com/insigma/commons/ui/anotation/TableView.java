/**
 * 
 */
package com.insigma.commons.ui.anotation;

import com.insigma.commons.ui.convert.ConvertorAdapter;
import com.insigma.commons.ui.convert.RowColorConvertor;

/**
 * 定义整个表格
 * @author 邱昌进(qiuchangjin@zdwxgd.com)
 *
 */
@java.lang.annotation.Target(value = { java.lang.annotation.ElementType.TYPE })
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface TableView {

	boolean isMultiple() default false;

	boolean isCheckable() default false;

	boolean isEditable() default false;

	Class<? extends RowColorConvertor> colorConvertor() default ConvertorAdapter.class;
}
