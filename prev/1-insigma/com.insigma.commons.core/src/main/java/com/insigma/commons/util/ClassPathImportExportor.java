/**
 * 
 */
package com.insigma.commons.util;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

/**
 * @author 邱昌进(qiuchangjin@zdwxgd.com)
 *
 */
public class ClassPathImportExportor {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClassPathImportExportor window = new ClassPathImportExportor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClassPathImportExportor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("导出", null, panel, null);
		panel.setLayout(new GridLayout(1, 2, 1, 0));

		JLabel lblworkspace = new JLabel("导出WorkSpace");
		panel.add(lblworkspace);

		JButton btnNewButton = new JButton("导出");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					File[] ps = file.listFiles(new FileFilter() {

						@Override
						public boolean accept(File pathname) {
							return pathname.isDirectory();
						}
					});
					if (ps == null) {
						return;
					}
					JFileChooser out = new JFileChooser();
					out.setFileFilter(new javax.swing.filechooser.FileFilter() {

						@Override
						public String getDescription() {
							return "选择导出的文件夹";
						}

						@Override
						public boolean accept(File f) {
							return f.isDirectory();
						}
					});
					out.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if (out.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
						return;
					}
					try {
						File f = new File(out.getSelectedFile(), "classpath.zip");

						//用文件输出流构建ZIP压缩输出流 
						ZipOutputStream zipos = new ZipOutputStream(new FileOutputStream(f));
						for (File file2 : ps) {
							File[] classpath = file2.listFiles(new FilenameFilter() {

								@Override
								public boolean accept(File dir, String name) {
									return name.equals(".classpath");
								}
							});
							if (classpath == null || classpath.length == 0) {
								continue;
							}
							File cpath = classpath[0];
							zipos.putNextEntry(new ZipEntry(file2.getName() + "/" + ".classpath"));

							FileInputStream fis = new FileInputStream(cpath);
							byte[] b = new byte[1024];
							while (fis.read(b) != -1) {
								zipos.write(b);
								b = new byte[1024];
							}
							fis.close();
						}
						zipos.close();
					} catch (Exception e1) {
						// TODO: handle exception
					}

				}
			}
		});
		panel.add(btnNewButton);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("导入", null, panel_1, null);

	}
}
