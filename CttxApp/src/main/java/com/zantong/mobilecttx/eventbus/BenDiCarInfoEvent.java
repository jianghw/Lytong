package com.zantong.mobilecttx.eventbus;

import com.zantong.mobilecttx.car.dto.CarInfoDTO;

/**
 * Created by zhoujie on 2016/10/15.
 */

public class BenDiCarInfoEvent {
    private boolean status;
    private CarInfoDTO dto;

    public BenDiCarInfoEvent(boolean status,CarInfoDTO dto) {
        super();
        this.status = status;
        this.dto = dto;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public CarInfoDTO getDto() {
        return dto;
    }

    public void setDto(CarInfoDTO dto) {
        this.dto = dto;
    }
}
