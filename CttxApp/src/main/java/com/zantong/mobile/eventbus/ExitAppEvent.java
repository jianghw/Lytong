package com.zantong.mobile.eventbus;

public class ExitAppEvent {
	private boolean isExit;
	private String message;

	public ExitAppEvent(boolean isExit, String message) {
		super();
		this.isExit = isExit;
		this.message = message;
	}

	public ExitAppEvent(boolean isExit) {
		super();
		this.isExit = isExit;
	}

	public boolean isExit() {
		return isExit;
	}

	public void setExit(boolean isExit) {
		this.isExit = isExit;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
