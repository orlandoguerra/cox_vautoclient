package com.vauto.vautoclient.model;

public class ResultModel {

	private boolean success;
	private String message;
	private int totalMilliseconds;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getTotalMilliseconds() {
		return totalMilliseconds;
	}
	public void setTotalMilliseconds(int totalMilliseconds) {
		this.totalMilliseconds = totalMilliseconds;
	}
	@Override
	public String toString() {
		return "ResultModel [success=" + success + ", message=" + message + ", totalMilliseconds=" + totalMilliseconds
				+ "]";
	}

	
}
