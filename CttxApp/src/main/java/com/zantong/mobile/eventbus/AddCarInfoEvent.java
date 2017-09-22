package com.zantong.mobile.eventbus;

import com.zantong.mobile.car.dto.CarInfoDTO;

public class AddCarInfoEvent {
	private boolean status;
	private CarInfoDTO tag;

	public AddCarInfoEvent(boolean status, CarInfoDTO tag) {
		super();
		this.status = status;
		this.tag = tag;
	}

	public AddCarInfoEvent(boolean status) {
		super();
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


	public CarInfoDTO getTag() {
		return tag;
	}

	public void setTag(CarInfoDTO tag) {
		this.tag = tag;
	}

}
