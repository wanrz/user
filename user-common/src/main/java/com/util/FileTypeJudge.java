package com.util;

/**
 * 文件类型判断类：用来判断二进制流所代表的文件类型
 * 
 * @author Chunjie He
 * @version 1.0
 * @date 2015-09-02
 *
 */
public class FileTypeJudge {
	
    /** 
     * 判断文件类型 
     *  
     * @param filePath 文件路径 
     * @return 文件类型 
     */  
    public static FileType getType(byte[] b) {  
        byte[] dest = new byte[28];
    	System.arraycopy(b, 0, dest, 0, 28);
    	
    	String fileHead = HexUtils.bytesToHex(dest);
        if (fileHead == null || fileHead.length() == 0) {
            return null;  
        }  
          
        fileHead = fileHead.toUpperCase();  
        FileType[] fileTypes = FileType.values();  
          
        for (FileType type : fileTypes) {  
            if (fileHead.startsWith(type.getValue())) {  
                return type;  
            }  
        }  
          
        return null;  
    }
    /**
     * 根据图片byte数组获取文件类型
     * @param b
     * @return
     */
    public static String getImageType(byte[] b) {  
    	FileType fileType = FileTypeJudge.getType(b);
    	if (fileType != null) {
    		return fileType.name().toLowerCase();
    	}
    	
    	return null;
    }
}
