/* 
 * 日期：2009-10-28
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * Zip打包工具类，解决中文乱码问题 Ticket:
 * 
 * @author DingLuofeng
 */
public class ZipFileUtil {

	private static Logger logger = Logger.getLogger(ZipFileUtil.class);

	public static void main(String[] args) throws IOException {
		compressFile("D:\\24", "D:\\241");
	}

	/**
	 * CreateTime：2009-9-10上午11:12:54<br>
	 * 描述：压缩needTozipFilePath目录下的文件到zipFilePath目录下，压缩后的文件名为needTozipFilePath + ".zip"
	 * 
	 * @param needTozipFilePath
	 * @param zipFilePath
	 * @throws IOException
	 */
	public static void compressFile(String needTozipFilePath, String zipFilePath) throws IOException {
		compressFile(needTozipFilePath, zipFilePath, null, "UTF-8", null);
	}

	/**
	 * CreateTime：2009-9-10上午11:12:54<br>
	 * 描述：压缩needTozipFilePath目录下的文件到zipFilePath目录下，压缩后的文件名为zipFileName
	 * 
	 * @param needTozipFilePath
	 * @param zipFilePath
	 * @param zipFileName
	 * @param encoding
	 * @throws IOException
	 */
	public static void compressFile(String needTozipFilePath, String zipFilePath, String zipFileName, String encoding)
			throws IOException {
		compressFile(needTozipFilePath, zipFilePath, zipFileName, encoding, null);
	}

	/**
	 * CreateTime：2009-9-10上午11:12:54<br>
	 * 描述：压缩needTozipFilePath目录下的文件到zipFilePath目录下，压缩后的文件名为zipFileName
	 * 
	 * @param needTozipFilePath
	 * @param zipFilePath
	 * @param zipFileName
	 * @param encoding
	 * @param folderFilter
	 *            文件夹名称过滤
	 * @throws IOException
	 */
	public static void compressFile(String needTozipFilePath, String zipFilePath, String zipFileName, String encoding,
			IFolderFilter folderFilter) throws IOException {

		if (needTozipFilePath == null || zipFilePath == null) {
			throw new IOException("被压缩文件和压缩文件存放路径不允许为空。");
		}

		File needtozipfile = new File(needTozipFilePath);

		File zipPath = new File(zipFilePath);
		if (!zipPath.exists()) {
			zipPath.mkdirs();
		}
		if (zipFileName == null) {
			zipFileName = needtozipfile.getName() + ".zip";
		}

		File zipfile = new File(zipPath, zipFileName);

		ZipOutputStream zout = null;
		try {
			FileOutputStream fout = new FileOutputStream(zipfile);
			zout = new ZipOutputStream(fout);

			if (needtozipfile.isFile()) {
				zipFile(zout, needtozipfile, "", folderFilter);
			} else {
				// zipFile(zout, file, needtozipfile.getName(), folderFilter);
				if (folderFilter != null) {
					//illegal目录比end目录多一层异常类型的子目录，
					//因此需要增加文件层数的判断之后再进行文件夹时间的判断
					if (folderFilter.accept(needtozipfile)) {
						int depth = folderFilter.getDepth();
						for (File innerFile : needtozipfile.listFiles()) {
							zipFile(zout, innerFile, needtozipfile.getName(), folderFilter);
							folderFilter.setDepth(depth);
						}
					}
				} else {
					for (File innerFile : needtozipfile.listFiles()) {
						zipFile(zout, innerFile, needtozipfile.getName(), folderFilter);
					}
				}
			}
		} finally {
			if (zout != null) {
				zout.close();
			}
		}
	}

	/**
	 * CreateTime：2009-9-10上午11:12:54<br>
	 * 描述：递归压缩文件到zipOut输出流(保留原文件结构不变)
	 * 
	 * @param zipOut
	 * @param needToZipFile
	 * @param content
	 * @param folderFilter
	 */
	@SuppressWarnings("finally")
	private static void zipFile(ZipOutputStream zipOut, File needToZipFile, String content,
			IFolderFilter folderFilter) {

		//目录为空时，不需要"/"
		if (content != null && !content.equals("")) {
			content += File.separatorChar;
		}
		try {
			if (needToZipFile.isFile() && needToZipFile.canRead() && needToZipFile.canWrite()) {
				ZipEntry ze = new ZipEntry(content + needToZipFile.getName());

				zipOut.putNextEntry(ze);
				FileInputStream fis = new FileInputStream(needToZipFile);
				try {
					IOUtils.copy(fis, zipOut);
				} finally {
					if (fis != null) {
						fis.close();
					}
					zipOut.closeEntry();
				}
			} else if (needToZipFile.isDirectory() && needToZipFile.listFiles().length == 0) {
				try {
					ZipEntry ze = new ZipEntry(content + needToZipFile.getName() + File.separatorChar);
					zipOut.putNextEntry(ze);
				} finally {
					zipOut.closeEntry();
					return;
				}
			} else if (needToZipFile.isDirectory()) {
				if (folderFilter != null) {
					if (folderFilter.filterFolder(needToZipFile)) {
						int depth = folderFilter.getDepth();
						for (File innerFile : needToZipFile.listFiles()) {
							zipFile(zipOut, innerFile, content + needToZipFile.getName(), folderFilter);
							folderFilter.setDepth(depth);
						}
					}
				} else {
					for (File innerFile : needToZipFile.listFiles()) {
						zipFile(zipOut, innerFile, content + needToZipFile.getName(), folderFilter);
					}
				}
			}
		} catch (IOException e) {
			logger.error("压缩文件出错。", e);
		}
	}

	/**
	 * 解压缩文件
	 * 
	 * @param fileName
	 *            压缩文件路径+名字 例: ./temp/test.zip
	 * @param filePath
	 *            存放解压缩文件的路径 例: ./temp
	 */
	public static void decompressFile(String fileName, String filePath) throws IOException {
		filePath += File.separatorChar;
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(fileName);
			Enumeration<?> emu = zipFile.entries();
			while (emu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) emu.nextElement();
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				if (entry.isDirectory() || entry.getSize() <= 0) {
					logger.debug("目录：" + entry.getName());
					new File(filePath + entry.getName()).mkdirs();
					continue;
				}
				File file = new File(filePath + entry.getName());
				logger.debug("文件：" + file.getName());
				// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
				// 而这个文件所在的目录还没有出现过，所以要建出目录来。
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(file);
				InputStream ins = zipFile.getInputStream(entry);
				try {
					IOUtils.copy(ins, fos);
				} finally {
					if (fos != null) {
						fos.close();
					}
					if (ins != null) {
						ins.close();
					}
				}
			}
		} finally {
			if (zipFile != null) {
				zipFile.close();
			}
		}
	}
}
