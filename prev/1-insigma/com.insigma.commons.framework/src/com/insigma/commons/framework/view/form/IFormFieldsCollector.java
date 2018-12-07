/**
 * 
 */
package com.insigma.commons.framework.view.form;

import java.util.List;

import com.insigma.commons.collection.IndexHashMap;
import com.insigma.commons.ui.form.BeanField;

/**
 * @author DingLuofeng
 *
 */
public interface IFormFieldsCollector {

	public final static String NONE_GROUP = "none_group";

	IndexHashMap<String, List<BeanField>> collectFields(Object data, Class<?> clz);

}
