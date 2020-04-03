package com.example.rediscache.model;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCodeEnum {
	NO_PERMISSION("forbidden", "无权限"), LOGIN_WITHOUT("noLogin", "未登陆"),SYSTEM_ERROR("systemError", "系统异常");
	
	public static Map<String,ErrorCodeEnum> ERROR_MAP = null;
	
	static{
		ERROR_MAP = new HashMap<String,ErrorCodeEnum>();
		ERROR_MAP.put(NO_PERMISSION.getCode(), NO_PERMISSION);
		ERROR_MAP.put(LOGIN_WITHOUT.getCode(), LOGIN_WITHOUT);
		ERROR_MAP.put(SYSTEM_ERROR.getCode(), SYSTEM_ERROR);
	}
	
	private String code;
	private String message;

	private ErrorCodeEnum(String code, String message) {

		this.code = code;

		this.message = message;

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
