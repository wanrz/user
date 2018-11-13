package com.url;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UrlDemo {
//	@Autowired
//	private ThreadPoolTaskExecutor taskExecutor;
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			ExecutorService pool = Executors.newCachedThreadPool();
			pool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						downLoadUtil.downLoadFile("http://gd.122.gov.cn/group4/M00/D1/03/ra0KDljeBjSAQLXFAACe1GskwpI699.jpg","E:\\InjongOne",System.currentTimeMillis()+"my.jpg");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
//		for(int i=0;i<10;i++){
//			pool.execute(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						downLoadUtil.downLoadFile("http://gd.122.gov.cn/group4/M00/D1/03/ra0KDljeBjSAQLXFAACe1GskwpI699.jpg","E:\\InjongOne",System.currentTimeMillis()+"my.jpg");
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					
//				}
//			});
//		}
	}
//	
//	public void runtest(){
//		ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
////		List<Future<Void>> futures = Lists.newArrayList();
//		
//		taskExecutor.submit(new Callable<Void>() {
//
//			@Override
//			public Void call() throws Exception {
//				try {
//					downLoadUtil.downLoadFile("http://gd.122.gov.cn/group4/M00/D1/03/ra0KDljeBjSAQLXFAACe1GskwpI699.jpg","E:\\InjongOne",System.currentTimeMillis()+"my.jpg");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				return null;
//			}
//		});
//	}
	

}

class downLoadUtil{
	public static void downLoadFile(String ipAddress,String filePath,String filename) throws IOException {
		URL url=new URL(ipAddress);
//		URLConnection openConnection = url.openConnection();
//		InputStream in openConnection.getInputStream();
		InputStream in = url.openStream();
		byte[] buff=new byte[1024];
		int len=0;
		File file=new File(filePath);
		if(!file.exists()){
			file.mkdir();
		}
		@SuppressWarnings("resource")
		OutputStream out=new FileOutputStream(file.getAbsolutePath()+"\\"+filename);
		while((len=in.read(buff))!=-1){
			out.write(buff, 0, len);
		}
		if(out!=null){
			out.flush();
		}
		if(in!=null){
			in.close();
		}
	}
}
