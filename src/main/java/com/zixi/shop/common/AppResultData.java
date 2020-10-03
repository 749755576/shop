package com.zixi.shop.common;

import java.io.Serializable;

public class AppResultData<T>  implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final int SUCCESS = 0;
	public static final int ERROR = 1;
	private static final int NEEDLOGIN = 2;
	private static final int WEIFABU = 3;
	/**
	 * 状态码 	说明
	 *  0     成功
	 * 100	服务器错误
 	 * 101	缺失必选参数 (%s)
	 * 102	参数值非法，需为 (%s)，实际为 (%s)
	 */
	public int code;

	/*
	 * 提示信息msg
	 */

	public String info;

	/*
	 * 返回数据data
	 */

	public T data;

	public AppResultData(int status, String info, T data) {
		this.code = status;
		this.info = info;
		this.data = data;
	}
	public  static <T> AppResultData<T> success(String message,T t){
       return  new AppResultData<>(SUCCESS,message,t);
	}
	public  static AppResultData<String> successMessage(String message){
		return  new AppResultData<>(SUCCESS,message,"");
	}
	public  static<T> AppResultData<T> successData(T t){
		return  new AppResultData<>(SUCCESS,"成功",t);
	}
	public  static <T> AppResultData<T> error(String message,T t){
		return  new AppResultData<>(ERROR,message,t);
	}
	public  static <T> AppResultData<T> errorFabu(String message,T t){
		return  new AppResultData<>(WEIFABU,message,t);
	}
	public  static AppResultData<String> errorMessage(String message){
		return  new AppResultData<>(ERROR,message,"");
	}
	public  static AppResultData<String> needLogin(String message){
		return  new AppResultData<>(NEEDLOGIN,message,"");
	}
	public  static AppResultData<String> loginMessage(){
		return  new AppResultData<>(208,"请登录","");
	}
	public int getStatus() {
		return code;
	}

	public void setStatus(int status) {
		this.code = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
