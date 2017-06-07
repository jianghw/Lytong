package com.zantong.mobilecttx.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class CallBack<T> implements Handler.Callback {

	protected static final int SUCCESS = 0;

	protected static final int FAIL = 1;

	protected static final int START = 2;

	protected static final int FINISH = 3;
	private Handler mHandler;

	public CallBack() {
		if (Looper.myLooper() != null)
			mHandler = new Handler(this);
	}

	public void onStart() {
	}

	public void onFinish() {
	}

	public abstract void onSuccess(T result);

	public void onError(String errorCode, String msg) {
	}

	protected void sendSuccessMessage(T result) {
		sendMessage(obtainMessage(SUCCESS, new Object[] { result }));
	}

	protected void sendFailMessage(String errorCode, String msg) {
		sendMessage(obtainMessage(FAIL, new Object[] { errorCode, msg }));
	}

	protected void sendStartMessage() {
		sendMessage(obtainMessage(START, null));
	}

	protected void sendEndMessage() {
		sendMessage(obtainMessage(FINISH, null));
	}

	protected void handleSuccessMessage(T result) {
		onSuccess(result);
	}

	protected void handleFailMessage(String errorCode, String msg) {
		onError(errorCode, msg);
	}

	@Override
	public boolean handleMessage(Message message) {
		switch (message.what) {
		case START:
			onStart();
			return true;
		case FINISH:
			onFinish();
			return true;
		case SUCCESS:
			Object[] successResponse = (Object[]) message.obj;
			handleSuccessMessage(((T) successResponse[0]));
			return true;
		case FAIL:
			Object[] failResponse = (Object[]) message.obj;
			handleFailMessage((failResponse[0].toString()),
					failResponse[1].toString());
			return true;
		}
		return false;
	}

	protected void sendMessage(Message message) {
		if (mHandler != null) {
			mHandler.sendMessage(message);
		} else {
			handleMessage(message);
		}
	}

	protected Message obtainMessage(int responseMessage, Object response) {
		Message message = null;
		if (mHandler != null) {
			message = mHandler.obtainMessage(responseMessage, response);
		} else {
			message = Message.obtain();
			message.what = responseMessage;
			message.obj = response;
		}
		return message;
	}
}