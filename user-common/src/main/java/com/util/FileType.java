package com.util;
/**
 * 文件类型枚举
 * 
 * @author Chunjie He
 * @date 2015-09-02
 */
public enum FileType {

	/**
	 * JPG.
	 */
	JPG("FFD8FF"),

	/**
	 * PNG.
	 */
	PNG("89504E47"),

	/**
	 * GIF.
	 */
	GIF("47494638"),

	/**
	 * TIFF.
	 */
	TIFF("49492A00"),

	/**
	 * Windows Bitmap.
	 */
	BMP("424D"),
	
	/**
     * ZIP Archive.
     */
    ZIP("504B0304");
	
	private String value = "";

	/**
	 * Constructor.
	 * 
	 * @param type
	 */
	private FileType(String value) {
		this.value = value;
	}

	/**
	 * 获取类型值
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置类型值
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
