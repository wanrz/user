package com.common;

import java.util.ArrayList;
import java.util.List;
/**
 * Project Name：abc-ibis20170115  <br/>
 * File Name：JsonListResult.java  <br/>
 * Package Name：com.cloudwalk.common.common  <br/>
 * Description: 响应结果类.  <br/>
 * @date: 2018年4月11日 下午2:06:42 
 * @version @param <T>
 * @since JDK 1.7
 ************************************************************
 *序号       修改时间            修改人           修改内容
 * 1
 ************************************************************
 *@Copyright: @2010-2018 重庆中科云丛科技有限公司  All Rights Reserved.
 */
public class JsonListResult<T> extends JsonResult {

	private Long total = 0L;
	private List<T> rows = new ArrayList<T>();

	/**
	 * Creates a new instance of JsonListResult.
	 */
	public JsonListResult() {
		super();
	}
	/**
	 * Creates a new instance of JsonListResult.
	 */
	public JsonListResult(Long total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	
	/**
	 * set
	 */
	public void set(Long total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	public List<T> getRows() {
		return rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
