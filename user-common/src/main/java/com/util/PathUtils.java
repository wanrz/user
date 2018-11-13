package com.util;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

/**
 * ClassName: PathUtils <br/>
 * Description: 路径工具类. <br/>
 * Date: 2016年3月25日 上午10:18:03 <br/>
 *
 * @author Chunjie He
 * @version 1.0
 * @since 1.7
 */
public class PathUtils {
	
	
	/**
	 * 图片路劲层级分隔符
	 */
	public static final String SEPARATOR = "/";
	/**
	 * 水印文件保存路径
	 */
	public static String TMP_ROOT_PATH = PropsUtil.getProperty(PropsParam.TMP_ROOT_PATH);
	
	/**
	 * 获取NAS根路径
	 */
	public static String NAS_ROOT_PATH = PropsUtil.getProperty(PropsParam.NAS_ROOT_PATH);
	
	/**
	 * 临时文件保存路径
	 */
	public static String ZIP_FILE_PATH = PropsUtil.getProperty(PropsParam.UPLOAD_FILE_PATH);
	
	/**
	 * 获取上传压缩包文件根路径
	 * @return
	 */
	public static String getZipRoot() {
		return ZIP_FILE_PATH + SEPARATOR;
	}
	
	/**
	 * 获取上传图片根路径
	 * @return
	 */
	public static String getUploadRoot() {
		return TMP_ROOT_PATH + SEPARATOR;
	}
	
	/**
	 * 获取NAS根路径
	 * @return
	 */
	public static String getNasRoot() {
		return NAS_ROOT_PATH + SEPARATOR;
	}
	
	/**
	 * 获取NAS下的绝对路径
	 * @param path
	 * @return
	 */
	public static String getNasFullPath(String path) {
		return NAS_ROOT_PATH + SEPARATOR + path;
	}
	
	/**
	 * 创建已当日为目录的path路径
	 * @param endStep后端，直接添加在路径末尾
	 * @return
	 */
	public static String createCurrDatePath(String prefix, String channel) {
		return createDatePath(DateUtil.getCurrentDate()) + SEPARATOR 
				+ ((prefix == null) ? "" : prefix) + channel;
	}
	
	/**
	 * 通过日期字符串构造3层路径
	 * @param yyyymmdd
	 * @return
	 */
	public static String createDatePath(String yyMMdd) {
		return yyMMdd.substring(0, 4) + SEPARATOR + 
			   yyMMdd.substring(4, 6) + SEPARATOR + 
			   yyMMdd.substring(6, 8);
	}
	
	/**
	 * 获取构造后的完整字符串
	 * @param root
	 * @param yyyymmdd
	 * @return
	 */
	public static String create(String root, String yyMMdd) {
		return root + SEPARATOR + createDatePath(yyMMdd);
	}
	
	/**
	 * 获取构造后的完整字符串
	 * @param root
	 * @param yyyymmdd
	 * @return
	 */
	public static String create(String root, Integer yyMMdd) {
		return create(root, String.valueOf(yyMMdd));
	}
	
	/**
	 * 替换文件路径
	 * @param s
	 * @param t
	 * @param file
	 * @return
	 */
	public static String createNAS4File(String root, File file) {
		String fileName = FilenameUtils.getName(file.getAbsolutePath());
		return root + fileName.replaceAll("\\_", SEPARATOR);
	}
}
