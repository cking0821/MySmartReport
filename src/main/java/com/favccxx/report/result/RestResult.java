package com.favccxx.report.result;

import com.favccxx.report.constants.SysConstants;

public class RestResult<T> {
	
	public static final int SUCCESS = 0;
	
	int statusCode;
	
	String message;
	
	T data;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
	
	 public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public static <T> RestResult<T> lackParams(){
		 RestResult<T> res = new RestResult<T>();
		 res.setStatusCode(SysConstants.STATUS_CODE_LACK_PARAMS);
		 res.setMessage(SysConstants.RESPONSE_MESSAGE_LACK_PARAMS);
		 return res;
	}
	
	public static <T> RestResult<T> invalidParams(){
		 RestResult<T> res = new RestResult<T>();
		 res.setStatusCode(SysConstants.STATUS_CODE_INVALID_PARAMS);
		 res.setMessage(SysConstants.RESPONSE_MESSAGE_INVALID_PARAMS);
		 return res;
	}

	public static <T> RestResult<T> success(T data){
		 RestResult<T> res = new RestResult<T>();
		 res.setStatusCode(SUCCESS);
		 res.setData(data);
		 res.setMessage(SysConstants.RESPONSE_MESSAGE_SUCCESS);
		 return res;
	}
	
	public static <T> RestResult<T> success(T data, String message){
		 RestResult<T> res = new RestResult<T>();
		 res.setStatusCode(SUCCESS);
		 res.setData(data);
		 res.setMessage(message);
		 return res;
	}
	
	public static <T> RestResult<T> success(String message){
		 RestResult<T> res = new RestResult<T>();
		 res.setStatusCode(SUCCESS);
		 res.setMessage(message);
		 return res;
	}
	
	
	public static RestResult<?> error(int statusCode, String message){
		RestResult<String> res = new RestResult<String>();
		 res.setStatusCode(statusCode);
		 res.setMessage(message);
		 return res;
	}

}
