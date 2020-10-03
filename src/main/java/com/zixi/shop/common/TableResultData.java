package com.zixi.shop.common;

import java.io.Serializable;
import java.util.List;

public class TableResultData<T>  implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 状态码 	说明
	 *  0     成功
	 * 100	服务器错误
 	 * 101	缺失必选参数 (%s)
	 * 102	参数值非法，需为 (%s)，实际为 (%s)
	 */
	private long total;

	private  int status=200;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	/*
	 * 返回数据data
	 */

	private List<T> rows;

	private TableResultData(long total, List<T> rows) {
		this.total = total;

		this.rows = rows;
	}
	public  static <T> TableResultData<T> success(long total, List<T> rows){
       return  new TableResultData<T>(total,rows);
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
