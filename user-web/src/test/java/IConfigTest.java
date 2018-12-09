
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.common.Constants;

public class IConfigTest {
	public static void main(String[] args) {
		// String str=IConfig.getProperty("domain.www");
//		String str = Constants.Config.DOMAIN_WWW;
//		System.out.println(str);

		IConfigTest iConfigTest=new IConfigTest();
		File file = new File("F:\\迅雷下载\\");
		iConfigTest.print(file);
//		System.out.println(file.list());
		System.out.println(iConfigTest.fileName);
	}
	
	private String fileName="";
	
    public  void print(File f){
        if(f!=null){
            if(f.isDirectory()){
                File[] fileArray=f.listFiles();
                if(fileArray!=null){
                    for (int i = 0; i < fileArray.length; i++) {
                        //递归调用
                        print(fileArray[i]);
                    }
                }
            }
            else{
                fileName=fileName+f.getAbsolutePath().replace("F:\\迅雷下载\\", "")+",";
            }
        }
    }
}
