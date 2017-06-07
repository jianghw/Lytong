package com.zantong.mobilecttx.eventbus;

public class OrderCancelEvent {

	private boolean status;

	public OrderCancelEvent(boolean status) {
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
