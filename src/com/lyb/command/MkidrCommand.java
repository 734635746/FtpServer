package com.lyb.command;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import com.lyb.server.ClientDeal;

/**
 *在当前目录下新建文件夹
 * @author liuyoubin
 *
 */
public class MkidrCommand implements Command {

	@Override
	public void deal(Writer writer, String data, ClientDeal client) throws IOException {
		
		//新建服务器工作目录
		String path = client.getCurrentPath()+File.separator+data;
		File file = new File(path);
		//响应信息
		String message = "212 文件夹建立完毕  . --------------\r\n";
		
		if(file.exists()&&file.isDirectory()) {
			message = "504 该文件夹已存在  . --------------\r\n";
		}else {
			//新建文件夹
			file.mkdir();
		}
		
		writer.write(message);
		writer.flush();
		
	  }

}
