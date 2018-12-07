package com.insigma.afc.log.provider;

import java.util.ArrayList;
import java.util.List;

import com.insigma.afc.log.config.ModuleConfig;
import com.insigma.afc.log.config.NodeConfig;
import com.insigma.afc.log.config.SystemNodeConfig;
import com.insigma.commons.application.Application;
import com.insigma.commons.ui.tree.ITreeProvider;
import com.insigma.commons.ui.tree.TreeNode;

public class LogTreeProvider implements ITreeProvider {

	public TreeNode getTree() {
		SystemNodeConfig systemNodeConfig = (SystemNodeConfig) Application.getData("systemNodeConfig");
		TreeNode tree = new TreeNode();
		tree.setText("功能模块");
		if (systemNodeConfig.getNodeConfig() != null) {
			List<Integer> topList = new ArrayList<Integer>();
			for (NodeConfig nodeConfig : systemNodeConfig.getNodeConfig()) {
				TreeNode childNode = new TreeNode();
				childNode.setText(nodeConfig.getNodeName());
				List<ModuleConfig> moduleConfigList = nodeConfig.getModuleConfigList();
				Integer[] moduleConfigs = new Integer[moduleConfigList.size()];
				int k = 0;
				for (ModuleConfig config : moduleConfigList) {
					TreeNode groundNode = new TreeNode();
					groundNode.setText(config.getModuleName() + "/" + config.getModuleNumber());
					groundNode.setKey(config.getModuleNumber());
					childNode.addNode(groundNode);
					moduleConfigs[k++] = config.getModuleNumber();
					topList.add(config.getModuleNumber());
				}
				childNode.setKey(moduleConfigs);
				tree.addNode(childNode);
			}
			tree.setKey(topList);
		}
		return tree;
	}

	public void setDepth(int depth) {

	}

}
