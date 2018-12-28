/* 
 * 日期：2018-8-24
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.insigma.commons.ui.tree.TreeNode;

/**
 * Ticket:下拉多选框树结构
 * 
 * @author chenhangwen
 */
public class MultiChoiceTree extends Tree {

    public MultiChoiceTree(Composite arg0, int arg1, TreeNode root) {

        super(arg0, arg1 | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.CHECK);

        setHeaderVisible(true);
        setLinesVisible(true);
        addSelectionListener(new TreeSelectListener());

        TreeColumn stationCol = new TreeColumn(this, SWT.LEFT);
        stationCol.setText("名称");
        stationCol.setWidth(300);

        TreeItem rootItem = new TreeItem(this, SWT.NONE);
        rootItem.setText("全部");

        if (null != root) {
            setTreeItem(root, rootItem);
        }

    }

    /**
     * 根据角色列表设置树形菜单
     * 
     * @param roleList
     */
    private void setTreeItem(TreeNode root, TreeItem item) {

        List<TreeNode> subNodeList = root.getChilds();
        if (null == subNodeList || subNodeList.size() < 1) {
            return;
        }
        for (TreeNode subNode : subNodeList) {
            TreeItem subItem = new TreeItem(item, SWT.NONE);
            subItem.setText(subNode.getText());
            subItem.setData(subNode.getKey());
            setTreeItem(subNode, subItem);
        }
    }

    /**
     * 获取所角色id列表信息
     * 
     * @return
     */
    public List<String[]> getSelectedIds() {
        // 获取所选的角色id号
        List<String[]> roleIdList = new ArrayList<String[]>();
        for (TreeItem item : this.getItems()) {
            getSelectedRoleIdsFromSubItem(roleIdList, item);
        }

        return roleIdList;
    }

    /**
     * 从子节点中获取选取的对应角色id列表信息
     * 
     * @param roleIdList
     * @param item
     */
    private void getSelectedRoleIdsFromSubItem(List<String[]> roleIdList, TreeItem item) {
        if (null == item || null == item.getItems() || item.getItems().length < 1) {
            return;
        }
        for (TreeItem subItem : item.getItems()) {
            if (subItem.getChecked()) {
                if (null != subItem.getData()
                        && (null == subItem.getItems() || subItem.getItems().length < 1)) {
                    String[] roles = new String[1];
                    String role = (String) subItem.getText();
                    roles[0] = role;
                    roleIdList.add(roles);
                }
                getSelectedRoleIdsFromSubItem(roleIdList, subItem);
            }
        }
    }

    /**
     * 根据对应用户的角色列表设置树形菜单的选取
     * 
     * @param userToRoleList
     */
    public void setTreeItemsChecked(List<String[]> list) {
        for (TreeItem item : this.getItems()) {
            setSubItemsChecked(list, item);
        }
    }

    /**
     * 根据对应用户的角色列表设置子节点中的选取
     * 
     * @param userToRoleList
     * @param item
     */
    private void setSubItemsChecked(List<String[]> userToRoleList, TreeItem item) {
        if (null == item || null == item.getItems() || item.getItems().length < 1) {
            return;
        }
        for (TreeItem subItem : item.getItems()) {
            if (null != subItem.getData()) {
                String roleId = subItem.getText();
                for (String[] tapUserToRole : userToRoleList) {
                    if (isEqu(tapUserToRole, roleId)) {
                        subItem.setChecked(true);
                        this.setItemSelected(subItem);
                        break;
                    }
                }
            }
            setSubItemsChecked(userToRoleList, subItem);

        }
    }

    private boolean isEqu(String[] src, String des) {
        boolean result = true;
        for (int i = 0; i < src.length; i++) {
            if (src[i] == null && des == null) {
                continue;
            }
            if (src[i] != null && !src[i].equals(des)) {
                result = false;
                break;
            }
        }

        return result;

    }

}
