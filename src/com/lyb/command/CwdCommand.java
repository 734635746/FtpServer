package com.lyb.command;

import java.io.File;
import java.io.Writer;

import com.lyb.global.Global;
import com.lyb.server.ClientDeal;
/**
 *      切换工作目录
 * @author liuyoubin
 *
 */
public class CwdCommand implements Command{
	/**
	 * 切换规则： 1 只能进入子目录
	 * 		 2 如果想切换到根目录可以 输入 cd ~
	 */
	@Override
	public void deal(Writer writer, String data, ClientDeal client) {
	  
		String path = null;
		
		//切换到根目录
		if(data.equals("~")) {
			client.setCurrentPath(Global.rootPath);
			
			return ;
		}else {
			
			path = client.getCurrentPath()+File.pathSeparator+data;
			File file = new File(path);
			
			if(file.exists()&&file.isDirectory()) {
				client.setCurrentPath(path);
			}
			
		}
		
		
	}

}
