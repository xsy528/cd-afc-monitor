/**
 * 
 */
package com.insigma.commons.editorframework.graphic.editor;

/**
 * @author DingLuofeng
 *
 */
public class SaveState extends AbstractState {

	public SaveState(MapItem self) {
		super(self);
	}

	@Override
	public void add(MapItem child) {
		if (child == null) {
			return;
		}
		self.addMapItem(child);
		self.setDataState(new ModifyState(self));
	}

	@Override
	public void delete(MapItem child) {
		if (child == null) {
			return;
		}
		self.removeMapItem(child);
		self.setDataState(new ModifyState(self));
	}

	@Override
	public void update() {
		self.setDataState(new ModifyState(self));
		MapItem parent = self.getParent();
		if (parent != null) {
			parent.setDataState(new ModifyState(parent));
		}
	}

}
