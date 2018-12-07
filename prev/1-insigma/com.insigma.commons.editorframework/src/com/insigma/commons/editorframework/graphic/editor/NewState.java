/**
 * 
 */
package com.insigma.commons.editorframework.graphic.editor;

/**
 * @author DingLuofeng
 *
 */
public class NewState extends AbstractState {

	public NewState(MapItem self) {
		super(self);
	}

	@Override
	public void add(MapItem child) {
		if (child == null) {
			return;
		}
		self.addMapItem(child);
		self.fireChanged();
	}

	@Override
	public void delete(MapItem child) {
		if (child == null) {
			return;
		}
		self.removeMapItem(child);
		self.fireChanged();
	}

	@Override
	public void update() {
		self.fireChanged();
	}

}
