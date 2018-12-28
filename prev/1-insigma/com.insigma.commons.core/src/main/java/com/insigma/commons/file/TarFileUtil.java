package com.insigma.commons.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.apache.tools.tar.TarOutputStream;

/*
 * 功能：压缩文件成tar.gz格式
 */
public class TarFileUtil {
	private static Logger logger = Logger.getLogger(TarFileUtil.class);

	/*
	 * 方法功能：打包单个文件或文件夹 参数：inputFileName 要打包的文件夹或文件的路径 targetFileName 打包后的文件路径
	 */
	public static void execute(String inputFileName, String targetFileName) {
		File inputFile = new File(inputFileName);
		String base = inputFileName.substring(inputFileName.lastIndexOf("/") + 1);
		TarOutputStream out = getTarOutputStream(targetFileName);
		tarPack(out, inputFile, base);
		try {
			if (null != out) {
				out.close();
			}
		} catch (IOException e) {
			logger.error(e == null ? "" : e.getMessage(), e);
		}
	}

	public static void execute(File inputFile, String targetFileName) {
		String base = inputFile.getName();
		TarOutputStream out = getTarOutputStream(targetFileName);
		tarPack(out, inputFile, base);
		try {
			if (null != out) {
				out.close();
			}
		} catch (IOException e) {
			logger.error(e == null ? "" : e.getMessage(), e);
		}
	}

	/*
	 * 方法功能：打包多个文件或文件夹 参数：inputFileNameList 要打包的文件夹或文件的路径的列表 targetFileName 打包后的文件路径
	 */
	public static void execute(List<String> inputFileNameList, String targetFileName) {
		TarOutputStream out = getTarOutputStream(targetFileName);
		for (String inputFileName : inputFileNameList) {
			File inputFile = new File(inputFileName);
			String base = inputFile.getName();
			tarPack(out, inputFile, base);
		}

		try {
			if (null != out) {
				out.close();
			}
		} catch (IOException e) {
			logger.error(e == null ? "" : e.getMessage(), e);
		}
	}

	/*
	 * 方法功能：打包成tar文件 参数：out 打包后生成文件的流 inputFile 要压缩的文件夹或文件 base 打包文件中的路径
	 */

	private static void tarPack(TarOutputStream out, File inputFile, String base) {
		if (inputFile.isDirectory()) // 打包文件夹
		{
			packFolder(out, inputFile, base);
		} else // 打包文件
		{
			packFile(out, inputFile, base);
		}
	}

	/*
	 * 方法功能：遍历文件夹下的内容，如果有子文件夹，就调用tarPack方法 参数：out 打包后生成文件的流 inputFile 要压缩的文件夹或文件 base 打包文件中的路径
	 */
	private static void packFolder(TarOutputStream out, File inputFile, String base) {
		File[] fileList = inputFile.listFiles();
		try {
			// 在打包文件中添加路径
			out.putNextEntry(new org.apache.tools.tar.TarEntry(base + "/"));
		} catch (IOException e) {
			logger.error(e == null ? "" : e.getMessage(), e);
		}
		base = base.length() == 0 ? "" : base + "/";
		for (File file : fileList) {
			tarPack(out, file, base + file.getName());
		}
	}

	/*
	 * 方法功能：打包文件 参数：out 压缩后生成文件的流 inputFile 要压缩的文件夹或文件 base 打包文件中的路径
	 */
	private static void packFile(TarOutputStream out, File inputFile, String base) {
		TarEntry tarEntry = new TarEntry(base);

		// 设置打包文件的大小，如果不设置，打包有内容的文件时，会报错
		tarEntry.setSize(inputFile.length());
		try {
			out.putNextEntry(tarEntry);
		} catch (IOException e) {
			logger.error(e == null ? "" : e.getMessage(), e);
		}
		try {
			byte[] wBuf = FileUtils.readFileToByteArray(inputFile);
			out.write(wBuf);
		} catch (IOException e) {
			logger.error(e == null ? "" : e.getMessage(), e);
		} catch (NullPointerException e) {
			System.err.println("NullPointerException info ======= [FileInputStream is null]");
		} finally {
			try {
				if (null != out) {
					out.closeEntry();
				}
			} catch (IOException e) {

			}
		}
	}

	/*
	 * 方法功能：获得打包后文件的流 参数：targetFileName 打包后文件的路径
	 */
	private static TarOutputStream getTarOutputStream(String targetFileName) {
		// 如果打包文件没有.tar后缀名，就自动加上
		targetFileName = targetFileName.endsWith(".tar") ? targetFileName : targetFileName + ".tar";
		FileOutputStream fileOutputStream = null;
		File path = new File(targetFileName).getParentFile();
		if (!path.exists()) {
			path.mkdirs();
		}
		try {
			fileOutputStream = new FileOutputStream(targetFileName);
		} catch (FileNotFoundException e) {
			logger.error(e == null ? "" : e.getMessage(), e);
		}
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		TarOutputStream out = new TarOutputStream(bufferedOutputStream);

		// 如果不加下面这段，当压缩包中的路径字节数超过100 byte时，就会报错
		out.setLongFileMode(TarOutputStream.LONGFILE_GNU);
		return out;
	}

	/**
	 * Deprecated by unTarWithEncoding
	 * @param file
	 * @param outputDir
	 */
	@Deprecated
	public static void unTar(File file, String outputDir) {
		if (!file.getName().endsWith(".tar")) {
			logger.error(file.getName() + "不是Tar文件");
			return;
		}
		TarInputStream tarIn = null;
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			tarIn = new TarInputStream(fileInputStream);
			createDirectory(outputDir, null);// 创建输出目录
			TarEntry entry = null;
			while ((entry = tarIn.getNextEntry()) != null && !"".equals(entry.getName())) {
				if (entry.isDirectory()) {// 是目录
					createDirectory(outputDir, entry.getName());// 创建空目录
				} else {// 是文件
					File tmpFile = new File(outputDir + "/" + entry.getName());
					createDirectory(tmpFile.getParent() + "/", null);// 创建输出目录
					OutputStream out = new FileOutputStream(tmpFile);
					tarIn.copyEntryContents(out);
					closeQuietly(out);
				}
			}
		} catch (Exception e) {
			logger.error("解压文件" + file.getName() + "出错", e);
		} finally {
			closeQuietly(fileInputStream);
			closeQuietly(tarIn);

		}
	}

	public static void unTarWithGbk(File file, String outputDir) {
		unTarWithEncoding(file, outputDir, "GBK");
	}

	/**
	 * 解压tar文件。tar文件中，文件名都是ascii编码，必须中文必须转换为gbk编码。
	 *
	 * @param file
	 * @param outputDir
	 */
	public static void unTarWithEncoding(File file, String outputDir, String fileNameEncoding) {
		if (!file.getName().endsWith(".tar")) {
			logger.error(file.getName() + "不是Tar文件");
			return;
		}
		TarInputStream tarIn = null;
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			tarIn = new TarInputStream(fileInputStream);
			createDirectory(outputDir, null);// 创建输出目录
			TarEntry entry = null;
			while ((entry = tarIn.getNextEntry()) != null && !"".equals(entry.getName())) {
				if (entry.isDirectory()) {// 是目录
					createDirectory(outputDir, entry.getName());// 创建空目录
				} else {// 是文件
					byte[] tmp = new byte[entry.getName().length()];
					for (int i = 0; i < entry.getName().length(); i++) {
						char c = entry.getName().charAt(i);
						byte b = (byte) (0xFF & c);
						tmp[i] = b;
					}
					String fName = new String(tmp, fileNameEncoding);
					File tmpFile = new File(outputDir + "/" + fName);

					createDirectory(tmpFile.getParent() + "/", null);// 创建输出目录
					OutputStream out = new FileOutputStream(tmpFile);
					tarIn.copyEntryContents(out);
					closeQuietly(out);
				}
			}
		} catch (Exception e) {
			logger.error("解压文件" + file.getName() + "出错", e);
		} finally {
			closeQuietly(fileInputStream);
			closeQuietly(tarIn);

		}
	}

	private static void closeQuietly(InputStream io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				logger.error("关闭IO流异常。", e);
			}
		}
	}

	private static void closeQuietly(OutputStream io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				logger.error("关闭IO流异常。", e);
			}
		}
	}

	/*
	 * 方法功能：解压文件 参数：file 要解压的文件 解压目标路径
	 */
	public static void unTarGz(File file, String outputDir) {
		if (!file.getName().endsWith(".tar.gz")) {
			logger.error(file.getName() + "不是tar.gz文件");
			return;
		}
		TarInputStream tarIn = null;
		GZIPInputStream inputStream = null;
		BufferedInputStream in = null;
		FileInputStream in2 = null;
		try {
			in2 = new FileInputStream(file);
			in = new BufferedInputStream(in2);
			inputStream = new GZIPInputStream(in);
			tarIn = new TarInputStream(inputStream);
			// 创建输出目录
			createDirectory(outputDir, null);
			TarEntry entry = null;
			while ((entry = tarIn.getNextEntry()) != null) {
				if (entry.isDirectory()) {
					// 创建空目录
					createDirectory(outputDir, entry.getName());
				} else {
					File tmpFile = new File(outputDir + File.pathSeparatorChar + entry.getName());
					createDirectory(tmpFile.getParent() + File.pathSeparatorChar, null);// 创建输出目录
					OutputStream out = new FileOutputStream(tmpFile);
					tarIn.copyEntryContents(out);
					closeQuietly(out);
				}
			}
		} catch (IOException e) {
			logger.error("解压文件" + file.getName() + "出错", e);
		} finally {
			closeQuietly(in2);
			closeQuietly(in);
			closeQuietly(inputStream);
			closeQuietly(tarIn);
		}

	}

	private static void createDirectory(String outputDir, String subDir) {

		File file = new File(outputDir);
		if (!(subDir == null || subDir.trim().equals(""))) {// 子目录不为空
			file = new File(outputDir + "/" + subDir);
		}
		if (!file.exists()) {
			file.mkdirs();
		}
	}
}
