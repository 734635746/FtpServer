package com.lyb.command;

import java.io.IOException;
import java.io.Writer;

import com.lyb.global.Global;
import com.lyb.server.ClientDeal;
/**
 * 用户登陆处理类
 * @author liuyoubin
 *
 */
public class UserCommand implements Command{

	@Override
	public void deal(Writer writer, String data, ClientDeal client) throws IOException {
		//提示信息
		String message = "";
		
		if(client.isLogin()) {//判断是否已经登陆
			
			writer.write("230 您已登陆，如果要重新登陆请先断开连接 .---------------\r\n");
			writer.flush();
			return ;
		}
		
		if(Global.loginUsers.contains(data)) {//用户已经登陆
			message = "301 该用户已经登陆，请使用另一个账户 .---------------\r\n";
		}else if(Global.users.containsKey(data)) {//用户存在
			client.setName(data);
			message = "331 用户名正确 请输入你的密码 .---------------\r\n";
		}else {
			message = "301 用户名不存在 .---------------\r\n";
		}
		

		writer.write(message);
		writer.flush();

	}

}
