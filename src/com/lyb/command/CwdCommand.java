package com.lyb.command;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import com.lyb.global.Global;
import com.lyb.server.ClientDeal;
import com.lyb.util.StringUtil;
/**
 *      切换工作目录
 * @author liuyoubin
 *
 */
public class CwdCommand implements Command{
	/**
	 * 切换规则：  1 进入子目录 cd \文件名\文件名
	 * 		   2 如果想切换到根目录可以 输入 cd \
	 * 		   3 上一级目录 cd .. 注意不能跳出根目录
	 * @throws IOException 
	 */
	@Override
	public void deal(Writer writer, String data, ClientDeal client) throws IOException {
		
		String message = "212 目录切换成功  .-----------------\r\n";
		String path = null;
		File file = null;
		
		if(data.equals("\\")) {//切换到根目录
			
			client.setCurrentPath(Global.rootPath);
			
		}else if(data.equals("..")){//切换到上一级目录
			
			//获取上一级目录
			path = StringUtil.returnLastPath(client.getCurrentPath());
			file = new File(path);
			
			if(Global.rootPath.length()>path.length()) {//目录跳出了服务器根目录
				message = "503 切换失败，不能跳出根目录 .---------------\r\n";
			}else if(file.exists()&&file.isDirectory()) {//判断目录是否存在
				client.setCurrentPath(path);
			}else {
				message = "503 切换失败，目录不存在 .---------------\r\n";
			}
			
			
		}else {
			
			
			path = client.getCurrentPath()+data;
			file = new File(path);
			
			if(file.exists()&&file.isDirectory()) {//判断目录是否存在
				client.setCurrentPath(path);
			}else {
				message = "503 切换失败，目录不存在 .---------------\r\n";
			}
			
		}
		
		//输出回复信息
		writer.write(message);
		writer.flush();
	}

}
