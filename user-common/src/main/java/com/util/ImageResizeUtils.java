package com.util;

import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import com.utility.recg.RecogniseUtils;

/**
 * 图片质量压缩工具类
 * @author Chunjie He
 * @since 1.7
 * @version 2.0
 */
public class ImageResizeUtils {

	/**
	 * 对图片数组对象转换为指定格式的数组对象
	 * @param image
	 * @return
	 */
	public static byte[] bytes2bytes(byte[] image, String format) {
		ByteArrayInputStream  is = new ByteArrayInputStream(image);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		try {
			ImageIO.write(ImageIO.read(is), format, os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return os.toByteArray();
	}
	
	/**
	 * 
	 * @param base64
	 * @param format
	 * @return
	 */
	public static byte[] base642bytes(String base64, String format) {
		return bytes2bytes(RecogniseUtils.decodeBase64(base64), format);
	}
	
	/**
	 * 自己设定压缩质量压缩图片
	 * @param base64
	 * @param quality
	 * @return
	 */
	public static byte[] base642bytes(String base64, float quality) {
		return bytes2bytes(RecogniseUtils.decodeBase64(base64), quality);
	}

	/**
	 * 自己设置压缩质量来把图片压缩成byte[]
	 * @param image 	压缩源图片
	 * @param quality 	压缩质量，在0-1之间，
	 * @return 			返回的字节数组
	 */
	public static byte[] bytes2bytes(byte[] image, float quality) {
		
		// 得到指定Format图片的writer
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter writer = (ImageWriter) iter.next(); 		// 得到writer

		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);	// 设置可否压缩
		iwp.setCompressionQuality(quality); 					// 设置压缩质量参数

		iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
		ColorModel colorModel = ColorModel.getRGBdefault();
		
		// 指定压缩时使用的色彩模式
		iwp.setDestinationType(new javax.imageio.ImageTypeSpecifier(
				colorModel, colorModel.createCompatibleSampleModel(16, 16)));

		// 开始打包图片，写入byte[]
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ByteArrayInputStream  is = new ByteArrayInputStream(image);
		
		try {
			IIOImage iIamge = new IIOImage(ImageIO.read(is), null, null);
			// 此处因为ImageWriter中用来接收write信息的output要求必须是ImageOutput
			// 通过ImageIo中的静态方法，得到byteArrayOutputStream的ImageOutput
			writer.setOutput(ImageIO.createImageOutputStream(os));
			writer.write(null, iIamge, iwp);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return os.toByteArray();
	}
}
