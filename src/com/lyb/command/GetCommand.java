package com.lyb.command;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.lyb.constant.Constant;
import com.lyb.server.ClientDeal;
import com.lyb.server.FtpServer;
import com.lyb.util.CloseUtil;
/**
 * 下载文件处理类
 * @author liuyoubin
 *
 */
public class GetCommand implements Command {

	@Override
	public void deal(Writer writer, String data, ClientDeal client) throws IOException {
		//下载文件的路径
		String path = client.getCurrentPath()+File.separator+data;
		
		File file = new File(path);
		
		//判断是否为文件类型
		if(file.isFile()) {
			
			
			try {
				
				writer.write("150 文件数据正常，准备打开连接 .---------------\r\n");
				writer.flush();
				
				//开启数据传输套接字
				Socket dataSocket1  = new Socket(InetAddress.getByName(client.getIp()), client.getPort(),FtpServer.server.getInetAddress(),Constant.DATAPORT);
				//输出流
				BufferedOutputStream ous = new BufferedOutputStream(dataSocket1.getOutputStream());
				//文件输入流
				InputStream is = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = -1;
				
				while((len=is.read(buffer))!=-1) {
					ous.write(buffer, 0, len);
					ous.flush();
				}
				
				writer.write("220 文件传输完毕  . ---------------\r\n");
				writer.flush();
				
				CloseUtil.close(is);
				CloseUtil.close(ous);
				CloseUtil.close(dataSocket1);
				
				writer.write("226  关闭数据连接 .---------------\r\n");
				writer.flush();
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else {
				writer.write("220 文件不存在 .---------------\r\n ");
				writer.flush();
					
		}
	}

}
