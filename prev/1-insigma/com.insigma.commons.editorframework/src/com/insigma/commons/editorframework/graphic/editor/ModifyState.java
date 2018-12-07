/**
 * 
 */
package com.insigma.commons.editorframework.graphic.editor;

/**
 * @author DingLuofeng
 *
 */
public class ModifyState extends AbstractState {

	public ModifyState(MapItem self) {
		super(self);
	}

	@Override
	public void add(MapItem child) {
		self.addMapItem(child);
		updateState();
	}

	@Override
	public void delete(MapItem child) {
		if (child == null) {
			return;
		}
		self.removeMapItem(child);
		updateState();

	}

	@Override
	public void update() {
		self.fireChanged();
	}

	private void updateState() {
		MapItem[] list = self.getMapItems();
		for (MapItem mapItem : list) {
			if (mapItem.isModify()) {
				return;
			}
		}
		self.setDataState(new SaveState(self));
	}
}
