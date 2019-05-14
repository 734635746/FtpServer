package com.lyb.command;

import java.io.IOException;
import java.io.Writer;

import com.lyb.server.ClientDeal;
/**
 * 转换为主动模式
 * @author liuyoubin
 *
 */
public class PortCommand implements Command {

	@Override
	public void deal(Writer writer, String data, ClientDeal client){
	
		String[] ipAndPort = data.split(",");

		StringBuilder ip = new StringBuilder();
		int port = 0;
		
		//Port命令后六个数字用逗号隔开,其中前四个数字组成IP地址,第5个数字乘以256再加上第6个数字为实际的端口号
		if(ipAndPort.length==6) {
			
			for(int i=0;i<4;i++) {
				ip.append(ipAndPort[i]);
				if(i!=3) {
					ip.append(".");
				}
			}
			
			port = Integer.parseInt(ipAndPort[4])*256;
			port += Integer.parseInt(ipAndPort[5]);
			//设置
			client.setIp(ip.toString());
			client.setPort(port);
		}

		
		try {
			writer.write("200 端口号和ip已经设置完毕 .---------------\r\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
