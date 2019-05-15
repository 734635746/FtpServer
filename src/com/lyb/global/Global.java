package com.lyb.global;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 存放全局共享信息
 * @author liuyoubin
 *
 */
public class Global {
	
	//记录存在的用户 用户名---密码
	public static Map<String,String> users = new ConcurrentHashMap<>();
	
	//记录已经登陆的用户
	public static Set<String> loginUsers = new HashSet<>(); 
	
	//服务器根目录
	public static String rootPath = new File("").getAbsolutePath();
	
	/***
	 * 初始化全局参数
	 */
	public static void initGlobalMessgae() {
		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc = null;
		try {
			//读取配置文件
			db = df.newDocumentBuilder();
			doc = db.parse("src/config.xml");
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//获取根节点
		Element root = doc.getDocumentElement();
		
		/***
		 * 获取用户信息
		 */
		NodeList list = root.getElementsByTagName("user");
		for(int i=0;i<list.getLength();i++) {
			Node n = list.item(i);
			//判断是否为真实节点
			if(n.getNodeType()==Node.ELEMENT_NODE) {
				String name = null;
				String pwd = null;
				//获取用户具体信息
				NodeList ulist = n.getChildNodes();
				for(int j=0;j<ulist.getLength();j++) {
					Node ns = ulist.item(j);
					//判断是否为真实节点
					if(ns.getNodeType()==Node.ELEMENT_NODE) {
						if(ns.getNodeName().equals("name")) {
							name = ns.getTextContent();
						}else if(ns.getNodeName().equals("pwd")) {
							pwd = ns.getTextContent();
						}
					}
					
				}
				//将已配置的用户信息保存到map容器中
				users.put(name, pwd);
			}
		}
		
		/**
		 * 获取服务器目录
		 */
		NodeList dirList = root.getElementsByTagName("root");
		Node dir = dirList.item(0);
		rootPath = dir.getTextContent();
		
	}
	
}
