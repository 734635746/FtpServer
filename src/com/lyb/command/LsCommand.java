package com.lyb.command;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.lyb.constant.Constant;
import com.lyb.server.ClientDeal;
import com.lyb.server.FtpServer;
import com.lyb.util.CloseUtil;
import com.lyb.util.TimeUtil;
/**
 * 查看当前服务器目录下的所有文件
 * @author liuyoubin
 *
 */
public class LsCommand implements Command {

	@Override
	public void deal(Writer writer, String data, ClientDeal client) throws IOException {
				//文件目录路径
				String path = client.getCurrentPath()+data;
				
				File file = new File(path);
				
				//检查文件或目录是否存在这种抽象路径名记
				if(!file.exists()) {
					
					writer.write("213 文件目录不存在 .---------------\r\n");
					writer.flush();
					return ;
					
				}else {
					//文件列表信息
					List<String> fileListMsg = new ArrayList<>();
					
					fileListMsg.add("-------当前目录下文件列表如下 :------\r\n");
					
					//获得该目录下的抽象路径名的文件和目录命名数组
					String[] fileList = file.list();
					if(fileList!=null) {
						for(String dirName:fileList) {
							//临时目录文件
							File tempFile = new File(path+File.separator+dirName);
							if(tempFile.isFile()) {
								String info = "f"+" rw-rw-rw- ftp  ftp "+tempFile.length()+"B     "+TimeUtil.longToString_Time(tempFile.lastModified())+"    "+dirName+"\r\n";
								fileListMsg.add(info);
							}
							
						}
						fileListMsg.add("\r\n");
					}
					
					
						
						try {
							
							writer.write("150 文件数据正常，准备打开连接 .---------------\r\n");
							writer.flush();
							
							//开启数据传输套接字
							Socket dataSocket  = new Socket(InetAddress.getByName(client.getIp()), client.getPort(),FtpServer.server.getInetAddress(),Constant.DATAPORT);
							//输出流
							BufferedWriter dataWriter = new BufferedWriter(new OutputStreamWriter(dataSocket.getOutputStream(),"GB2312"));
							
							
							for(String str:fileListMsg) {
								dataWriter.write(str);
							}
							
							writer.write("226 传输完毕 .---------------\r\n");
							writer.flush();
							
							CloseUtil.close(dataWriter);
							CloseUtil.close(dataSocket);
							

							writer.write("226  关闭数据连接 .---------------\r\n");
							writer.flush();
							
							
						} catch (UnknownHostException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						

				}
				
				

			}


	}


