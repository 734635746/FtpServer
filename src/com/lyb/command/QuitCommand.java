package com.lyb.command;

import java.io.Writer;

import com.lyb.global.Global;
import com.lyb.server.ClientDeal;

/**
 * 退出登陆处理类
 * @author liuyoubin
 *
 */
public class QuitCommand implements Command{

	@Override
	public void deal(Writer writer, String data, ClientDeal client) {
		//将用户从已登陆列表清除
		Global.loginUsers.remove(client.getName());
		//断开连接
		client.clear();
		
		
	}

}
