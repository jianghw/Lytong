package com.zantong.mobilecttx.eventbus;

import android.content.Context;

public class SelectEvent {
	private int type;// 0 选择加油站  1选择支付方式
	private int imgId;
	private int id;
	private String content;
	private Context context;

	public SelectEvent(int type, int imgId, int id, String content, Context context) {
		this.type = type;
		this.imgId = imgId;
		this.id = id;
		this.content = content;
		this.context = context;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
