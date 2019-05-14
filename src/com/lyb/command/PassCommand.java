package com.lyb.command;

import java.io.IOException;
import java.io.Writer;

import com.lyb.global.Global;
import com.lyb.server.ClientDeal;
/**
 * 登陆密码处理类
 * @author liuyoubin
 *
 */
public class PassCommand implements Command{

	@Override
	public void deal(Writer writer, String data, ClientDeal client) {
		//提示信息
		String message ="";
		
		if(client.isLogin()) {//判断是否已经登陆
			try {
				writer.write("230 您已登陆，如果要重新登陆请先断开连接 .---------------\r\n");
				writer.flush();
				return ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//获取用户账号
		String name = client.getName();
		
		if(name==null) {
			try {
				writer.write("332 还未输入账户 请先执行USER命令 .---------------\r\n");
				writer.flush();
				return ;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		//获取账户对应的密码
		String password = Global.users.get(name);
		
		if(password!=null&&password.equals(data)) {
			//更新登陆列表
			Global.loginUsers.add(name);
			//更新登陆状态
			client.setLogin(true);
			message = "230 用户："+name+"登陆成功";
			
		}else {
			message ="530 密码错误请重新登陆  .";
		}
		
		try {
			writer.write(message+".---------------\r\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
