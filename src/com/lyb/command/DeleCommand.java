package com.lyb.command;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import com.lyb.server.ClientDeal;
/**
 * 删除当前目录下的指定文件
 * @author liuyoubin
 *
 */
public class DeleCommand implements Command {

	@Override
	public void deal(Writer writer, String data, ClientDeal client) throws IOException {
				//文件目录
				String path = client.getCurrentPath()+File.separator+data;
				
				File file = new File(path);
				
				//响应信息
				String message = "213  文件删除成功. ------------\r\n";
				
				if(file.exists()&&file.isFile()) {//判断是否为文件
					
					file.delete();
					
					
				}else {
					message = "504 删除失败,文件不存在 .--------------\r\n";
				}
				
				writer.write(message);
				writer.flush();

	}

}
