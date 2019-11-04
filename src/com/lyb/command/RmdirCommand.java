package com.lyb.command;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import com.lyb.server.ClientDeal;
/**
 * 删除当前服务器目录下的一个空目录
 * @author liuyoubin
 *
 */
public class RmdirCommand implements Command {

	@Override
	public void deal(Writer writer, String data, ClientDeal client) throws IOException {
		//文件夹目录
		String path = client.getCurrentPath()+File.separator+data;
		
		File file = new File(path);
		
		//响应信息
		String message = "212 目录删除成功. ------------\r\n";
		
		if(file.exists()&&file.isDirectory()) {//判断是否为目录
			
			if(!file.delete()) {//判断是否为空目录
				message = "503 该目录不是空目录,删除失败  。------------\r\n";
			}
			
		}else {
			message = "504 删除失败,目录不存在 .--------------\r\n";
		}
		
		writer.write(message);
		writer.flush();
	}

}
