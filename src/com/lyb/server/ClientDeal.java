package com.lyb.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.lyb.command.Command;
import com.lyb.command.CommandFactory;
import com.lyb.command.PassCommand;
import com.lyb.command.UserCommand;
import com.lyb.global.Global;
import com.lyb.util.CloseUtil;

/**
 * 用户请求处理类
 * @author liuyoubin
 *
 */
public class ClientDeal implements Runnable{
	
	@SuppressWarnings("unused")
	//客户端与服务器端的连接
	private Socket socket = null;
	private InputStream ips = null;
	private OutputStream ous = null;
	private BufferedReader reader = null;
	private BufferedWriter writer = null;
	
	//当前连接对应的用户名
	private String name = null;
	
	//判断是否已经登陆
	private volatile boolean isLogin = false;
	
	//当前服务器工作目录
	private String currentPath = Global.rootPath;
	
	//客户端的ip地址
	private String ip = null;
	
	//客户端数据传输端口
	private int port;
	
	//记录是否第一次访问
	private boolean isFirstConn = true;
	
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCurrentPath() {
		return currentPath;
	}

	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	public ClientDeal(Socket socket) {
		try {
			this.socket = socket;
			//获取套接字的输入输出流
			ips = socket.getInputStream();
			ous = socket.getOutputStream();
		} catch (IOException e) {
			CloseUtil.close(ips);
			CloseUtil.close(ous);
			CloseUtil.close(socket);
		}
	}
	
	/**
	 * 当用户调用quit命令是时调用此方法进行连接清理工作
	 */
	public void clear() {
		CloseUtil.close(reader);
		CloseUtil.close(writer);
		CloseUtil.close(ips);
		CloseUtil.close(ous);
		CloseUtil.close(socket);
	}
	
	
	@Override
	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(ips,"GB2312"));
			writer = new BufferedWriter(new OutputStreamWriter(ous,"GB2312"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		while(!socket.isClosed()) {
			//第一次访问(连接成功) 提示用户
			if(isFirstConn) {
				try {
					writer.write("220  连接ftp服务器成功 .---------------\r\n");
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//标志为已经访问过
				isFirstConn = false;
			}else {
				
				if(!socket.isClosed()) {
					//接收客户端的命令
					String command = null;
						try {
							//阻塞地等待客户端命令
							command = reader.readLine();
						} catch (IOException e) {
							clear();
							e.printStackTrace();
						}
                    System.out.println(command);
                    
                    if(command!=null) {//获取到命令command
                    	//分割命令
                    	String[] datas = command.split(" ");
                    	//通过命令工厂获得响应的命令处理类
                    	Command commandDeal = CommandFactory.getCommand(datas[0]);
                    	
                    	//一开始在没有登陆的情况下只能使用 user pass 命令
                    	if(isLogin||commandDeal instanceof UserCommand||commandDeal instanceof PassCommand) {
                    		//判断命令是否存在
                    		if(commandDeal==null) {
                    			try {
									writer.write("503 命令不存在，请重新输入 .---------------\r\n");
									writer.flush();
								} catch (IOException e) {
									e.printStackTrace();
								}
            					
                    		}else {
                    			//命令存在则 获取命令的数据信息
                    			String data = "";
                    			if(datas.length>=2) {
                    				data = datas[1];
                    			}
                    			//调用命令处理类的处理方法
                    			try {
									commandDeal.deal(writer, data, this);
								} catch (IOException e) {
									e.printStackTrace();
								}
                    		}
                    		
                    	}else {//未登陆状态
                    		try {
								writer.write("530 执行该命令需要先登陆 .---------------\r\n");
								writer.flush();
							} catch (IOException e) {
								e.printStackTrace();
							}
                    	}
	
                    }
				
				}else {
					//连接断开则退出循环
					break;
					
				}
				
			}
						
		}
		
	
	}

}
