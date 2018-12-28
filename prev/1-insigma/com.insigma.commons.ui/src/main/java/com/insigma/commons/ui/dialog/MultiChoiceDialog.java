/* 
 * 日期：2018-8-24
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.ui.dialog;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.ui.tree.TreeNode;
import com.insigma.commons.ui.widgets.MultiChoiceTree;
import com.swtdesigner.SWTResourceManager;

/**
 * Ticket:多选下拉对话框
 * 
 * @author chenhangwen
 */
public class MultiChoiceDialog {

    private List<String[]> result;

    private Shell shell;

    private MultiChoiceTree swtTree;

    private Display display;

    private Button btnOK;

    public MultiChoiceDialog(Display display, TreeNode root) {
        this.display = display;

        shell = new Shell(display.getActiveShell(), SWT.NONE | SWT.APPLICATION_MODAL);
        shell.setLayout(new GridLayout());
        shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
        shell.setSize(320, 400);

        swtTree = new MultiChoiceTree(shell, SWT.NONE | SWT.APPLICATION_MODAL, root);
        final GridData gd_functionTree = new GridData(SWT.FILL, SWT.FILL, true, true);
        swtTree.setLayoutData(gd_functionTree);
        swtTree.setSize(320, 380);
        swtTree.setExpanded(true);

        final Composite composite = new Composite(shell, SWT.NONE);
        composite.setBackground(SWTResourceManager.getColor(255, 255, 255));
        final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        composite.setLayout(gridLayout);

        btnOK = new Button(composite, SWT.NONE);
        btnOK.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent arg0) {
                result = swtTree.getSelectedIds();
                shell.close();
            }
        });
        btnOK.setText("确  定 (&O)");

        final Button btnCancel = new Button(composite, SWT.NONE);
        btnCancel.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent arg0) {
                result = null;
                shell.close();
            }
        });
        btnCancel.setText("取  消 (&X)");

    }

    public void setTreeItemsChecked(Object value) {
        if (value instanceof List) {
            try {
                result = (List<String[]>) value;
                swtTree.setTreeItemsChecked((List<String[]>) value);
            } catch (Exception e) {

            }
        }
    }

    public List<String[]> open() {
        // shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        return result;
    }

    public void setLocation(Point point) {
        shell.setLocation(point);
    }

    public Point getSize() {
        return shell.getSize();
    }

    public void close() {
        shell.close();
    }

    /**
     * @return the swtTree
     */
    public MultiChoiceTree getSwtTree() {
        return swtTree;
    }

    /**
     * @param swtTree
     *            the swtTree to set
     */
    public void setSwtTree(MultiChoiceTree swtTree) {
        this.swtTree = swtTree;
    }

}
