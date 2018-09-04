package com.netty.before.oio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OioServer {
	public static void main(String[] args) throws Exception {
		ExecutorService newCachedThreadPool=Executors.newCachedThreadPool();
		ServerSocket server=new ServerSocket(10101);
		System.out.println("服务启动");
		while(true){
			final Socket socket=server.accept();
			System.out.println("连上来一个新的客户端");
			newCachedThreadPool.execute(new Runnable(){

				@Override
				public void run() {
					//业务处理
					handler(socket);
				}
				
			});
			
		}
	}
	
	public static void handler(Socket socket){
		try {
			byte[] bytes=new byte[2048];
			InputStream inputStream=socket.getInputStream();
			while(true){
				int read=inputStream.read(bytes);
				if(read!=-1){
					System.out.println(new String(bytes,0,read));
				}else{
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				System.out.println("socket关闭");
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
