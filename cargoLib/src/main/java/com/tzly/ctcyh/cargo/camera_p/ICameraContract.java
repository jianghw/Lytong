package com.tzly.ctcyh.cargo.camera_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:扫描列表
 * Update by:
 * Update day:
 */

public interface ICameraContract {

    interface ICameraView extends IBaseView<ICameraPresenter> {
    }

    interface ICameraPresenter extends IBasePresenter {
        void bindingDriving(String drivingDTO);

        void addVehicleLicense(String carDTO);
    }

}
