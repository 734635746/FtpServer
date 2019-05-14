package com.lyb.command;

import java.io.IOException;
import java.io.Writer;

import com.lyb.server.ClientDeal;

/**
 * 显示当前工作目录
 * @author liuyoubin
 *
 */
public class PwdCommand implements Command {

	@Override
	public void deal(Writer writer, String data, ClientDeal client) {
		try {
			//当前工作目录
			String path = client.getCurrentPath();
			writer.write("212 当前工作目录: "+path+" \r\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
