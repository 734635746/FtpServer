package com.lyb.command;
/**
 * 采用简单工厂模式 
 * 根据输入的类型创建对应命令处理类
 * @author liuyoubin
 *
 */
public class CommandFactory {
	public static Command getCommand(String type) {
		//转为大写
		type = type.toUpperCase();
		
		switch(type) {
		
			case "USER" :return new UserCommand();
			
			case "PASS" :return new PassCommand();
			
			case "LIST" :return new DirCommand();
			
			case "RETR" :return new GetCommand();
			
			case "PORT" :return new PortCommand();
			
			case "QUIT" :return new QuitCommand();
			
			case "CWD"  :return new CwdCommand();
			
			case "XPWD" :return new PwdCommand();
			
			default:return null;

		}
	}
}
