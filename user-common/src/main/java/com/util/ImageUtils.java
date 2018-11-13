package com.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;

import com.utility.recg.RecogniseUtils;

/**
 * 图片工具类
 * @author hechj
 */
public class ImageUtils {
	
	/**
	 * 图片大小限制
	 */
	public static int IMAGE_MAX_SIZE = PropsUtil.getIntProperty(PropsParam.IMAGE_MAX_SIZE);
	public static int IMAGE_MIN_SIZE = PropsUtil.getIntProperty(PropsParam.IMAGE_MIN_SIZE);
	
	/**
	 * 图片的截取的区域信息
	 */
	public static String IMAGE_LOCATION_LEFT = PropsUtil.getProperty(PropsParam.IMAGE_LOCATION_LEFT);
	public static String IMAGE_LOCATION_RIGHT = PropsUtil.getProperty(PropsParam.IMAGE_LOCATION_RIGHT);

	
	/**
	 * 撞库流水记录前缀
	 */
	public static String COLLISION_PREFIX = "C";
	
	/**
	 * 注册用户永久性目录
	 */
	private static String PERMAN_FLODER = "9999";
	
	/**
	 * 水印文件永久性目录
	 */
	private static String WATER_FLODER = "9998";
	
	
	
	/**
	 * 随机产生两位字符串的目录
	 * @return
	 */
	private static String random2bitchar() {
		return Integer.toHexString(new Random().nextInt(100) + 16);
	}
	
	/**
	 * 创建图片名称
	 * @param suffix 文件名称后缀
	 * @return
	 */
	public static String createFileName(String suffix) {
		return UUIDUtils.get32LowerCase() + "." + suffix;
	}


	/**
	 * 创建图片保存路径
	 * @param channel
	 * @return
	 */
	public static String getChannelPath(String prefix, String channel) {
		String datePath = PathUtils.createCurrDatePath(prefix, channel);
		return datePath + PathUtils.SEPARATOR + random2bitchar();
	}
	
	/**
	 * 创建人脸注册图片永久保存路径
	 * @return
	 */
	public static String getPermanentPersonPath() {
		return PathUtils.createCurrDatePath(null, PERMAN_FLODER) 
				+ PathUtils.SEPARATOR + random2bitchar();
	}
	
	/**
	 * 创建水印永久性保存路径
	 * @return
	 */
	public static String getPermanentWaterPath() {
		return PathUtils.createCurrDatePath(null, WATER_FLODER)
				+ PathUtils.SEPARATOR + random2bitchar();
	}
	
	/**
	 * 获得永久存储路径
	 * @return
	 */
	public static String getPermanentFullPath() {
		return ImageUtils.getPermanentPersonPath() + PathUtils.SEPARATOR 
				+ ImageUtils.createFileName(FileType.JPG.name());
	}

	/**
	 * 保存图片
	 * 
	 * @param data
	 * @param path
	 * @param fileName
	 * @return
	 */
	private static String save(byte[] data, String path, String fileName) {
		return save(data, path + PathUtils.SEPARATOR + fileName);
	}

	/**
	 * 保存注册人员图片信息
	 * @param data
	 * @param suffix
	 * @return
	 */
	public static String savePermPersonImg(byte[] data, String suffix) {
		return save(data, getPermanentPersonPath(), createFileName(FileType.JPG.name()));
	}
	
	/**
	 * 
	 * @param data
	 * @param suffix
	 * @return
	 */
	public static String savePermWaterImg(byte[] data, String suffix) {
		return save(data, getPermanentWaterPath(), createFileName(FileType.JPG.name()));
	}
	
	/**
	 * 保存渠道流水图片
	 * 
	 * @param data
	 * @param channel
	 * @param prefix
	 * @return
	 */
	public static String saveChannelFlowImg(byte[] data, String channel, String prefix) {
		return save(data, getChannelPath(prefix, channel), createFileName(FileType.JPG.name()));
	}
	
	/**
	 * 二进制数据转图片
	 *
	 * @param data
	 * @param path
	 */
	public static String save(byte[] data, String path) {
		
		String fileName = path.replace("/", "_").replace("\\", "_");
		File file = new File(PathUtils.getUploadRoot() + fileName);
		
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		
		FileType type = FileTypeJudge.getType(data);
		InputStream is = new ByteArrayInputStream(data);
		
		if (FileType.BMP.equals(type)) {
			try {
				ImageIO.write(ImageIO.read(is), FileType.JPG.name(), file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				FileUtils.copyInputStreamToFile(is, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return path;
	}
	
	/**
	 * png图片转jpg格式
	 *
	 * @param data 图片二进制数据
	 * @return 返回转换后的jpg图片数据
	 */
	public static byte[] imageToJpgByte(byte[] data) {		
		
		FileType type = FileTypeJudge.getType(data);	
		InputStream is = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		
		if (FileType.PNG.equals(type)) {
			
			try {
				
				//输入流
				is = new ByteArrayInputStream(data);
				//输出流
				byteArrayOutputStream = new ByteArrayOutputStream();
				//读取图片流数据
				BufferedImage bufferedImage = ImageIO.read(is);
				//重新构造图片数据
				BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
			            bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			      newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
				ImageIO.write(newBufferedImage, FileType.JPG.name(), byteArrayOutputStream);
				//获取新的图片数据
				data = byteArrayOutputStream.toByteArray();
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					is = null;
				}
				if(byteArrayOutputStream != null) {
					try {
						byteArrayOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					byteArrayOutputStream = null;
				}
			}
		} 		
		
		return data;
	}
	
	/**
	 * png图片转jpg格式
	 *
	 * @param data 图片二进制数据
	 * @return 返回转换后的jpg图片数据
	 */
	public static String imageToJpgString(byte[] data) {		
		data = imageToJpgByte(data);
		return Base64.encodeBase64String(data);
	}
	
	/**
	 * 对文件进行拷贝,将文件拷贝到永久性目录
	 */
	public static String copyImageNas(String imagePath) {
		String path = getPermanentFullPath();
		
		File src = new File(PathUtils.getNasRoot() + imagePath);
		File dest = new File(PathUtils.getNasRoot() + path);
		
		try {
			FileUtils.copyFile(src, dest);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return path;
	}

	/**
	 * 图片转二进制数据
	 *
	 * @param path
	 * @return
	 */
	public static byte[] getImgByteArray(String path) {
		FileInputStream fis = null;
		
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}

		byte[] imageBytes = new byte[Integer.valueOf(String.valueOf(file.length()))];
		try {
			fis = new FileInputStream(file);
			fis.read(imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return imageBytes;
	}
	
	/**
	 * 判断是否图片
	 * @param byteData
	 * @return
	 */
	public static boolean isImageFromBytes(byte[] byteData) {  
		//图片标识 true图片 false非图片
        boolean flag = false;  
        if(byteData == null){
        	return flag;
        }
        try {  
            BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(byteData));  
            if (null == bufImg) {  
                return flag;  
            }  
            flag = true;  
        } catch (Exception e) {  
        }  
        
        return flag;  
    }  	
	
	/**
	 * 判断base64字符串是否为图片数据
	 * @param base64String
	 * @return
	 */
	public static boolean isImageFromBase64String(String base64String) { 
		if(StringUtils.isBlank(base64String)){
			return false;
		}
		byte[] imgData = Base64.decodeBase64(RecogniseUtils.clearBase64String(base64String));
		return isImageFromBytes(imgData);
	}	
	
}
