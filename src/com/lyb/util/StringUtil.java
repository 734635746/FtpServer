package com.lyb.util;
/**
 * 字符串处理工具类
 * @author liuyoubin
 *
 */
public class StringUtil {
	/**
	 * 输入一个目录字符串返回其上一级目录
	 * @param path 当前目录字符串
	 * @return 上一级目录
	 */
	public static String returnLastPath(String path) {
		//新文件目录
		String newPath = null;
		//获得最后一个\的位置
		int index = path.lastIndexOf("\\");
		//截取字符串路径
		newPath = path.substring(0, index);
	
		return newPath;
	}
	
}
