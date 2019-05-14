package com.lyb.constant;

import java.nio.charset.Charset;
/**
 * 存放常量
 * @author liuyoubin
 *
 */
public class Constant {
	//window命令行默认字符集
	public static Charset DEFAULTCHARSET = Charset.forName("GB2312");
	//服务器命令监听端口,用于监听客户端连接
	public static int LISTENPORT = 21;
	//服务器数据端口号,用于向客户端发送数据
	public static int DATAPORT = 20;
	//线程池默认大小
	public static int THREADPOOLSIZE = 20;
}
