/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.graphic.editor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.graphic.GraphicItem;
import com.insigma.commons.editorframework.graphic.ImageGraphicItem;
import com.insigma.commons.ui.form.IEditorChangedListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定义将要在界面显示的数据结构
 * 
 * @author changjin_qiu
 *
 */
public class MapItem {

	private String text;

	private String hint;

	private boolean selected;

	private Integer status;

	// jfq，该列表有两个元素，items[0]是ImageItem，items[1]是TextItem
	private List<GraphicItem> items;// 界面渲染的基本图元

	// private List<GraphicItem> orgItems = new ArrayList<GraphicItem>();

	private List<MapItem> mapItems;

	private Action selectionAction;

	private Action doubleClickAction;

	private List<Action> contextAction;

	private Object value;

	private String image;

	private String backgroundImage;

	private Map<MapKey, Action> keyAction;


	private MapItem parent;

	private long mapId;// item的唯一标识

	private boolean isLoadSubItem = true;

	/**
	 * 节点状态
	 */
	private IState dataState = new NewState(this);
	private IState preDataState = null;

	private List<IEditorChangedListener> changedListeners;

	public MapItem(long id) {
		super();
		this.mapId = id;
		this.items = new ArrayList<GraphicItem>();
		mapItems = new ArrayList<MapItem>();
		contextAction = new ArrayList<Action>();
		changedListeners = new ArrayList<IEditorChangedListener>();
		keyAction = new HashMap<MapKey, Action>();
	}

	public void changeToSaveState() {
		for (MapItem item : mapItems) {
			item.changeToSaveState();
		}
		setDataState(new SaveState(this));
	}

	@JsonIgnore
	public Map<MapKey, Action> getKeyAction() {
		return keyAction;
	}

	public void addKeyAction(MapKey key, Action keyAction) {
		this.keyAction.put(key, keyAction);
	}

	public void addGraphicItem(GraphicItem item) {
		this.items.add(item);
		// GraphicItem graphicItem = new GraphicItem(item.getParent(),
		// item.getX(), item.getY(), item.getAngle());
		// orgItems.add(graphicItem);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@JsonIgnore
	public String getHint() {
		if (hint != null){
			throw new RuntimeException("");
		}
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	@JsonIgnore
	public Action getSelectionAction() {
		return selectionAction;
	}

	public void setSelectionAction(Action selectionAction) {
		this.selectionAction = selectionAction;
	}

	@JsonIgnore
	public Action[] getContextAction() {
		Action[] actions = new Action[contextAction.size()];
		for (int i = 0; i < contextAction.size(); i++) {
			actions[i] = contextAction.get(i);
		}
		return actions;
	}

	public void addContextAction(Action contextAction) {
		this.contextAction.add(contextAction);
	}

	public void removeContextAction(Action contextAction) {
		this.contextAction.remove(contextAction);
	}

	@JsonIgnore
	public Action getDoubleClickAction() {
		return doubleClickAction;
	}

	public void setDoubleClickAction(Action doubleClickAction) {
		this.doubleClickAction = doubleClickAction;
	}

	@JsonIgnore
	public Integer getStatus() {
		return status;
	}

	// jfq, 切换设备状态（这里执行的操作就是切换显示的底图）
	public void setStatus(int status) {
		this.status = status;
		if (items != null && !items.isEmpty()) {
			if (items.get(0) instanceof ImageGraphicItem) {
				ImageGraphicItem item = (ImageGraphicItem) items.get(0);
				item.setImageIndex(status);
			}
		}
	}

	@JsonIgnore
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		if (this.selected != selected) {
			this.selected = selected;
			for (GraphicItem item : items) {
				item.setSelected(selected);
			}
		}
	}

	public void paint(GC gc) {
		if (items != null) {
			for (GraphicItem item : items) {
				item.paint(gc);
			}
		}
	}

	public List<GraphicItem> getItems() {
		if (items == null) {
			items = new ArrayList<GraphicItem>();
		}
		return items;
	}

	public void setItems(List<GraphicItem> items) {
		this.items = items;
	}

	@JsonIgnore
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@JsonIgnore
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@JsonIgnore
	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	/**
	 * 
	 * @return 返回原始对象
	 */
	public List<MapItem> getMapItemList() {
		return mapItems;
	}

	/**
	 * 
	 * @return the mapItems 返回clone对象
	 */
	public MapItem[] getMapItems() {
		MapItem[] _items = new MapItem[mapItems.size()];
		return mapItems.toArray(_items);
	}

	public boolean isExists(MapItem mapItem) {
		return this.mapItems.contains(mapItem);
	}

	public boolean addMapItem(MapItem mapItem) {
		if (mapItem == null) {
			return false;
		}
		mapItem.parent = this;
		if (mapItems.contains(mapItem)) {
			return false;
		}

		return this.mapItems.add(mapItem);
	}

	public boolean removeMapItem(MapItem mapItem) {
		return this.mapItems.remove(mapItem);
	}

	/**
	 * @return the mapId
	 */
	public long getMapId() {
		return mapId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (mapId ^ (mapId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapItem other = (MapItem) obj;
		if (mapId != other.mapId)
			return false;
		return true;
	}

	/**
	 * @return the dataState
	 */
	@JsonIgnore
	public IState getDataState() {
		return dataState;
	}

	/**
	 * 获取上一次的状态
	 * 
	 * @return the preDataState
	 */
	@JsonIgnore
	public IState getPreDataState() {
		return preDataState;
	}

	/**
	 * @param dataState
	 *            the dataState to set
	 */
	/* public */void setDataState(IState dataState) {
		this.preDataState = this.dataState;
		this.dataState = dataState;
		fireChanged();
		// if (parent != null) {
		// parent.fireChanged();
		// }
	}

	protected void fireChanged() {
		for (IEditorChangedListener changedListener : changedListeners) {
			Event event = new Event();
			event.data = this;
			changedListener.editorChanged(event, isModify());
		}
	}

	/**
	 * @return the parent
	 */
	@JsonIgnore
	public MapItem getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(MapItem parent) {
		this.parent = parent;
	}

	@JsonIgnore
	public boolean isModify() {
		return !(this.dataState instanceof SaveState);
	}

	@JsonIgnore
	public boolean isNew() {
		return this.dataState instanceof NewState;
	}

	/**
	 * @return the changedListener
	 */
	@JsonIgnore
	public List<IEditorChangedListener> getChangedListeners() {
		return changedListeners;
	}

	public void addChangedListener(IEditorChangedListener changedListener) {
		this.changedListeners.add(changedListener);
	}

	/**
	 * @param changedListener
	 *            the changedListener to set
	 */
	public void removeChangedListener(IEditorChangedListener changedListener) {
		this.changedListeners.remove(changedListener);
	}

	@JsonIgnore
	public boolean isLoadSubItem() {
		return isLoadSubItem;
	}

	public void setLoadSubItem(boolean isLoadSubItem) {
		this.isLoadSubItem = isLoadSubItem;
	}


	private String mapType ;
	public String getMapType(){
		return mapType;
	}

	public void setMapType(String obj){
		this.mapType = obj;
	}

	private Integer imageUrl;

	public Integer getImageUrl(){
		return imageUrl;
	}
	public void setImageUrl(Integer value){
		this.imageUrl = value;
	}
}
