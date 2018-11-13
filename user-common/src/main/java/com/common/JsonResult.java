package com.common;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * JSON Response Basic data
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "javassistLazyInitializer" })
public class JsonResult {

	private boolean success = true;

	private String code = "";

	private String message = "";

	private Map<Object, Object> data = new HashMap<Object, Object>();

	public JsonResult() {
	}

	public JsonResult(boolean succ, String messsage) {
		this.success = succ;
		this.message = messsage;
	}

	public JsonResult(boolean success) {
		this.success = success;
	}

	public JsonResult(String code, String message) {
		this.code = code;
		this.message = message;
		this.success = false;
	}

	/**
	 * 添加数据. <br/>
	 * @param map
	 */
	public void appendData(Map<?, ?> map) {
		this.data.putAll(map);
	}
	/**
	 * 添加数据. <br/>
	 * @param map
	 */
	public void appendData(Object key, Object value) {
		this.data.put(key, value);
	}

	public String getCode() {
		return code;
	}

	public Map<Object, Object> getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setData(Map<Object, Object> data) {
		this.data = data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
