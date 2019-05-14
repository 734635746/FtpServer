package com.lyb.command;

import java.io.Writer;

import com.lyb.server.ClientDeal;

public interface Command {
	/**
	 * 命令执行方法
	 * @param writer 输出流
	 * @param datas  数据信息
	 * @param client 连接处理对象
	 */
	public void deal(Writer writer,String data,ClientDeal client);
}
