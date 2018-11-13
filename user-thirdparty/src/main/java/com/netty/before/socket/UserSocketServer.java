package com.netty.before.socket;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * <p>ClassName: UserSocketServer</p>
 * Description:用户socket服务端<br/>
 * @date 2018年9月7日 上午9:36:22 
 * @author wangrenzong@cloudwalk.cn
 * @version 1.0
 * @since JDK 1.7
 */ 
public class UserSocketServer extends Thread {
	
	private ServerSocket serverSocket;
	
	public UserSocketServer(int port){
		try {
			serverSocket=new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
	}
}
