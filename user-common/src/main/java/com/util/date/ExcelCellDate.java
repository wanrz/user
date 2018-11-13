package com.util.date;
/**
 * Project Name：abc-ibis20170115  <br/>
 * File Name：ExcelCellDate.java  <br/>
 * Package Name：com.cloudwalk.common.util.date  <br/>
 * Description: Excel实体.  <br/>
 * @date: 2018年4月11日 下午4:34:18 
 * @author 冯德志
 * @version 
 * @since JDK 1.7
 ************************************************************
 *序号       修改时间            修改人           修改内容
 * 1
 ************************************************************
 *@Copyright: @2010-2018 重庆中科云丛科技有限公司  All Rights Reserved.
 */
public class ExcelCellDate implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6266091288147144245L;

	/*
	 * 行
	 */
	private int row;
	
	/*
	 * 列
	 */
	private int col;
	
	/*
	 * 内容
	 */
	private String cellContents;
	
	
	public void setRow(int row){
		this.row = row;
	}

	public int getRow(){
		return this.row;
	}
	
	public void setCol(int col){
		this.col = col;
	}
	
	public int getCol(){
		return this.col;
	}
	
	public void setCellContents(String cellContents){
		this.cellContents = cellContents;
	}
	
	public String getCellContents(){
		return this.cellContents;
	}
}
