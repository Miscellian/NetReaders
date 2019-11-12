package com.netreaders.models;

public class ResponseMessage<T> {

	private boolean isSuccessful = true;
	private String errorMessage = "";
	private T obj = null;
	public boolean isSuccessful() {
		return isSuccessful;
	}

	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
	
	public ResponseMessage() {};
	
	public ResponseMessage(boolean isSuccessful, String errorMessage, T obj) {
		this.isSuccessful = isSuccessful;
		this.errorMessage = errorMessage;
		this.obj = obj;
	}
	
	
}
