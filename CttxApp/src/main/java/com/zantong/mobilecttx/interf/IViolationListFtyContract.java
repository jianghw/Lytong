package com.zantong.mobilecttx.interf;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.car.bean.PayCarBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResult;
import com.zantong.mobilecttx.weizhang.dto.ViolationCarDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import java.util.List;

/**
 * 违章列表页面
 */

public interface IViolationListFtyContract {

    interface IViolationListFtyView extends IMvpView<IViolationListFtyPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void searchViolationError(String message);

        void dataDistributionError(String message, int value);

        void nonPaymentData(List<ViolationBean> beanList);

        void havePaymentData(List<ViolationBean> beanList);

        void allPaymentData(ViolationResult rspInfo);

        ViolationDTO getViolationDTO();

        ViolationCarDTO getViolationCarDTO();

        void getPayCarsError(String message);

        void getPayCarsSucceed(PayCarBean rspInfo);
    }

    interface IViolationListFtyPresenter extends IMvpPresenter {

        void searchViolation();

        String initViolationDTO();

        void handleViolations(List<ViolationBean> beanList);

        String initHomeDataDTO();

        void getPayCars();
    }
}
