package com.insigma.acc.wz.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insigma.acc.wz.monitor.ACCMapEditorModule;
import com.insigma.acc.wz.util.RouteAddressUtil;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.initor.ApplicationInitor;
import com.insigma.afc.initor.ConfigLoadSystemInitor;
import com.insigma.afc.monitor.map.GraphicMapGenerator;
import com.insigma.afc.monitor.map.builder.EditGraphicMapBuilder;
import com.insigma.commons.editorframework.graphic.ImageGraphicItem;
import com.insigma.commons.editorframework.graphic.editor.MapItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by vigour on 2018/12/5.
 */
public class RestMain {
	static class MapData{
		MapItem root;
		List<String> imagePaths = new ArrayList<>();

		public MapItem getRoot(){
			return root;
		}

		public List<String> getImagePaths(){
			return imagePaths;
		}
	}

	private static TreeSet<String> imagePaths = new TreeSet<>();
	public static void rebuild_0(MapItem mapItem){
		Object value = mapItem.getValue();
		mapItem.setMapType(value.getClass().getSimpleName().replace("Metro", ""));
		imagePaths.add(mapItem.getImage());
		for(String path: ((ImageGraphicItem)mapItem.getItems().get(0)).getImagesPath()){
			imagePaths.add(path);
		}


		for(MapItem subItem: mapItem.getMapItems()){
			rebuild_0(subItem);
		}
	}

	public static void rebuild_1(MapItem mapItem){
		ImageGraphicItem imageGraphicItem = ((ImageGraphicItem)mapItem.getItems().get(0));

		ArrayList<String> allImages = new ArrayList<String>();
		allImages.addAll(imagePaths);
		for(String path: imageGraphicItem.getImagesPath()) {
			imageGraphicItem.getMonitorImgs().add(allImages.indexOf(path));
		}
		imageGraphicItem.setEditorImg(imageGraphicItem.getMonitorImgs().get(0));

		mapItem.setImageUrl(allImages.indexOf(mapItem.getImage()));

		for(MapItem subItem: mapItem.getMapItems()){
			rebuild_1(subItem);
		}
	}

	public static void rebuild_3(MapData mapData){
		List<String> shortPaths = new ArrayList<>(mapData.getImagePaths().size());
		for(String path: mapData.getImagePaths()){
			String shortPath = path;
			if(!shortPath.endsWith("png"))
				shortPath = shortPath + ".png";
			shortPaths.add(shortPath.replace("/com/insigma/afc/ui/", "/").replace("//", "/"));
		}
		mapData.imagePaths = shortPaths;
	}

	public static void printMap(MapData item){
		ObjectMapper mapper = new ObjectMapper();

		//User类转JSON
		//输出结果：{"name":"zhangsan","age":20,"birthday":844099200000,"email":"zhangsan@163.com"}
		try {
			mapper.writeValue(new File("webres/map.json"), item);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args){
		ConfigLoadSystemInitor configInitor = new ConfigLoadSystemInitor("config/Config.xml");
		configInitor.init();
		new ApplicationInitor().init();
		AFCApplication.init(new RouteAddressUtil());
		GraphicMapGenerator graphicMapGenerator = new GraphicMapGenerator();
		graphicMapGenerator.setEditUI(true);
		graphicMapGenerator.setGraphicMapBuilder(new EditGraphicMapBuilder());
		//
		MapItem root = graphicMapGenerator.getMapRootItem(true);
		rebuild_0(root);
		rebuild_1(root);

		MapData mapData = new MapData();
		mapData.root = root;
		mapData.imagePaths.addAll(imagePaths);
		rebuild_3(mapData);

		printMap(mapData);

		for(String path: imagePaths){
			System.out.println(path);
		}
		ACCMapEditorModule mapEditorModule = new ACCMapEditorModule();
		mapEditorModule.setGraphicMapGenerator(graphicMapGenerator);
		// 赋予权限
		//Module module = mapEditorModule.getModule();
		//module.setFunctionID(WZACCLogModuleCode.MODULE_MAP_EDITOR.toString());
	}

}
