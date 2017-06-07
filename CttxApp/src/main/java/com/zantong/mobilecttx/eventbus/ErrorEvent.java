package com.zantong.mobilecttx.eventbus;

import android.content.Context;

public class ErrorEvent {
	private String status;
	private String msg;
	private Object tag;
	private Context context;

	public ErrorEvent(String status, String msg, Object tag, Context context) {
		super();
		this.status = status;
		this.msg = msg;
		this.tag = tag;
		this.context = context;
	}

	public ErrorEvent(String status, String msg, Context context) {
		super();
		this.status = status;
		this.msg = msg;
		this.context = context;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

}
