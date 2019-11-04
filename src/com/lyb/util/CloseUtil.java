package com.lyb.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 处理关闭连接的工具类
 * @author liuyoubin
 *
 */
public class CloseUtil {
	/**
	 * 关闭输入流
	 * @param ips
	 */
	public static void close(InputStream ips) {
		if(ips!=null) {
			try {
				ips.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭输出流
	 * @param ips
	 */
	public static void close(OutputStream ous) {
		if(ous!=null) {
			try {
				ous.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭缓冲输入字节流
	 * @param red
	 */
	public static void close(BufferedReader red) {
		if(red!=null) {
			try {
				red.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭缓冲输出字节流
	 * @param wri
	 */
	public static void close(BufferedWriter wri) {
		if(wri!=null) {
			try {
				wri.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭Socket连接
	 * @param socket
	 */
	public static void close(Socket socket) {
		if(socket!=null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
