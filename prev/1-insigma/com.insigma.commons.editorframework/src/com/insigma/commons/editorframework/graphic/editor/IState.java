/**
 * 
 */
package com.insigma.commons.editorframework.graphic.editor;

/**
 * @author DingLuofeng
 *
 */
public interface IState {

	void add(MapItem child);

	void delete(MapItem child);

	void update();

}
