package com.lyb.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lyb.constant.Constant;
import com.lyb.global.Global;
/**
 * ftp服务器
 * @author liuyoubin
 *
 */
public class FtpServer {
	
	//开启一个线程池,用于并发的处理用户连接请求
	private static ExecutorService ThreadPool = Executors.newFixedThreadPool(Constant.THREADPOOLSIZE);
	//声明一个服务器套接字，用于接收响应客户端的请求连接
	public static ServerSocket server = null;
	
	public static void main(String[] args) throws IOException {
		//开启ServerSocket服务. 监听客户端的请求
		server = new ServerSocket(Constant.LISTENPORT);
		//初始化ftp服务全局参数
		Global.initGlobalMessgae();
		
		//循环不断监听连接请求的到来
		while(true) {
			//阻塞地获取连接
			Socket socket = server.accept();
			//将客户端连接交给线程池管理
			ThreadPool.execute(new ClientDeal(socket));
		}
		
		
	}
}
