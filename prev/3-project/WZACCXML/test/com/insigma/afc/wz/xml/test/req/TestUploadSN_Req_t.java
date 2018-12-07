/* 
 * 日期：2014-12-30
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.wz.xml.test.req;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.distinct.rpc.XDRInputStream;
import com.distinct.rpc.XDROutputStream;
import com.insigma.acc.wz.xdr.IXDRDecoder;
import com.insigma.acc.wz.xdr.IXDREncoder;
import com.insigma.acc.wz.xdr.WZXDRDecoder;
import com.insigma.acc.wz.xdr.WZXDREncoder;
import com.insigma.acc.wz.xdr.message.req.UploadSN_Req_t;
import com.insigma.acc.wz.xdr.message.req.UploadSN_Req_t_Decoder;
import com.insigma.acc.wz.xdr.message.req.UploadSN_Req_t_Encoder;
import com.insigma.afc.wz.xml.test.EntityUtil;

/**
 * Ticket: 
 * @author  chenfuchun
 *
 * 
 */
public class TestUploadSN_Req_t {
	public static void main(String[] args) {
		TestUploadSN_Req_t uploadSN = new TestUploadSN_Req_t();
		uploadSN.testModel();
	}

	private void testModel() {

		String filePath = System.getProperty("user.dir");
		String fileName = "testXDr";
		String fileName2 = "testxdr2";

		UploadSN_Req_t file = generateBean();
		System.out.println(ReflectionToStringBuilder.toString(file));

		// encode
		fileEncoder(filePath, fileName, file);

		// decode
		UploadSN_Req_t file_t = fileDecode(filePath, fileName);

		// 反向encode
		fileEncoder(filePath, fileName2, file_t);

		// 反向decode
		UploadSN_Req_t file_t2 = fileDecode(filePath, fileName2);

		// 比较实体的各个域是否一致
		boolean result = EntityUtil.deepCompare(file_t2, file_t);

		if (!result) {
			System.err.println("编解码不一致。");
		}

		// 比较两个文件的字节流是否一致
		byte[] original = getDataFromFile(filePath, fileName);
		byte[] original2 = getDataFromFile(filePath, fileName2);
		//多出来一部分不知道原因
		System.out.println(original);
		System.out.println(original2);

		System.out.println(original.length + "++++" + original2.length);
		for (int i = 0; i < original2.length; i++) {
			if (original[i] != original2[i]) {
				System.out.println(
						"总长：" + original.length + ">>>" + original[i] + "<-序列:" + (i + 1) + "->" + original2[i]);
			}
		}

	}

	private byte[] getDataFromFile(String filePath, String fileName) {
		try {
			return FileUtils.readFileToByteArray(new File(filePath + File.separator + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private UploadSN_Req_t fileDecode(String filePath, String fileName) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(filePath, fileName));
			XDRInputStream xdrStreamin = new XDRInputStream(in);
			IXDRDecoder decoder = (IXDRDecoder) new WZXDRDecoder(xdrStreamin);
			UploadSN_Req_t file1 = new UploadSN_Req_t_Decoder().decode(decoder);
			return file1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private void fileEncoder(String filePath, String fileName, UploadSN_Req_t file) {
		FileOutputStream out = null;
		try {
			assertFolder(filePath);
			out = new FileOutputStream(new File(filePath, fileName));
			XDROutputStream xdrStream = new XDROutputStream(out);
			IXDREncoder encoder = (IXDREncoder) new WZXDREncoder(xdrStream);
			new UploadSN_Req_t_Encoder().encode(encoder, file);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private UploadSN_Req_t generateBean() {
		//test:
		UploadSN_Req_t dataFile_t = new UploadSN_Req_t();

		return dataFile_t;
	}

	/**
	 * 检查文件夹是否存在，如果不存在则创建
	 * 
	 * @pEventam filePath
	 */
	private static void assertFolder(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

}
