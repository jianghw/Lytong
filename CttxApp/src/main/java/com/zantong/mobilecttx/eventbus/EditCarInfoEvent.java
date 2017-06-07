package com.zantong.mobilecttx.eventbus;

import com.zantong.mobilecttx.car.dto.CarInfoDTO;

/**
 * Created by zhoujie on 2016/10/15.
 */

public class EditCarInfoEvent {

    private boolean status;
    private CarInfoDTO oldDto;
    private CarInfoDTO newDto;

    public EditCarInfoEvent(boolean status, CarInfoDTO oldDto, CarInfoDTO newDto) {
        this.status = status;
        this.oldDto = oldDto;
        this.newDto = newDto;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public CarInfoDTO getOldDto() {
        return oldDto;
    }

    public void setOldDto(CarInfoDTO oldDto) {
        this.oldDto = oldDto;
    }

    public CarInfoDTO getNewDto() {
        return newDto;
    }

    public void setNewDto(CarInfoDTO newDto) {
        this.newDto = newDto;
    }
}
