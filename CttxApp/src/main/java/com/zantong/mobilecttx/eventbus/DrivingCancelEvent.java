package com.zantong.mobilecttx.eventbus;

public class DrivingCancelEvent {

	private boolean status;

	public DrivingCancelEvent(boolean status) {
		super();
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
