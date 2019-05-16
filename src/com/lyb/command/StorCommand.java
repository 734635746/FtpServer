package com.lyb.command;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;

import com.lyb.constant.Constant;
import com.lyb.server.ClientDeal;
import com.lyb.server.FtpServer;
import com.lyb.util.CloseUtil;
/**
 * 客户端上传文件到服务器当前工作目录
 * @author liuyoubin
 *
 */
public class StorCommand implements Command {

	@Override
	public void deal(Writer writer, String data, ClientDeal client) throws IOException {
		
		
		//传输文件虚拟路径
		String path = client.getCurrentPath()+File.separator+data;
		File file = new File(path);
		
		if(file.exists()) {
			writer.write("502 上传失败(文件已存在/文件名重复) . -----------\\r\\n");
			writer.flush();
		}else {
			
			writer.write("150 文件数据正常，准备打开连接 .---------------\r\n");
			writer.flush();
			
			//开启数据传输套接字
			Socket dataSocket  = new Socket(InetAddress.getByName(client.getIp()), client.getPort(),FtpServer.server.getInetAddress(),Constant.DATAPORT);
			//获取客户端的输入流
			BufferedInputStream is = new BufferedInputStream(dataSocket.getInputStream());
			//文件输出流
			FileOutputStream fos = new FileOutputStream(file);
			//数据缓冲区
			byte[] buffer = new byte[1024];
			int len = -1;
			
			while((len=is.read(buffer))!=-1) {
				fos.write(buffer, 0, len);
				fos.flush();
			}
			
			writer.write("220 文件上传完毕  . ---------------\r\n");
			writer.flush();
			
			CloseUtil.close(is);
			CloseUtil.close(fos);
			CloseUtil.close(dataSocket);
			
			writer.write("226  关闭数据连接 .---------------\r\n");
			writer.flush();
			
			
		}
	
	}

}
