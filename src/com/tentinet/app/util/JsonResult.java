package com.tentinet.app.util;
import java.io.Serializable;
/**
 * 封装请求的Json结果，前台通过Json result控制进行处理
 * @author jobs1127
 *
 */
public class JsonResult implements Serializable {
	private static final long serialVersionUID = 3867798228945734416L;
	public static final int ERROR_CODE_NO_OPERATOR_SECURITY = -2000;
	public static final JsonResult SUCCESS = new JsonResult();
	private boolean result = true;
	private String message = "";
	private Object data = "";
	private int errorCode = 0;

	private JsonResult() {
	}

	public JsonResult(Object parData) {
		this.data = parData;
	}

	public static JsonResult failure(String message) {
		JsonResult rtn = new JsonResult();
		rtn.result = false;
		rtn.message = message;
		return rtn;
	}

	public static JsonResult failure(int errorCode, String message) {
		JsonResult rtn = new JsonResult();
		rtn.result = false;
		rtn.message = message;
		rtn.errorCode = errorCode;
		return rtn;
	}

	public boolean isResult() {
		return this.result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}