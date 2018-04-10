package com.zantong.mobilecttx.violation_p;

import com.tzly.ctcyh.java.response.violation.ValidAdvResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResult;
import com.zantong.mobilecttx.weizhang.dto.ViolationCarDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import java.util.List;

/**
 * 违章列表页面
 */

public interface IViolationListContract {

    interface IViolationListView extends IBaseView<IViolationListPresenter>{

        void searchViolationError(String message);

        void dataDistributionError(String message, int value);

        void nonPaymentData(List<ViolationBean> beanList);

        void havePaymentData(List<ViolationBean> beanList);

        void allPaymentData(ViolationResult rspInfo);

        ViolationDTO getViolationDTO();

        ViolationCarDTO getViolationCarDTO();

        void validAdvertError(String message);

        void validAdvertSucceed(ValidAdvResponse result);
    }

    interface IViolationListPresenter extends IBasePresenter {

        void searchViolation();

        String initViolationDTO();

        void handleViolations(List<ViolationBean> beanList);

        void findIsValidAdvert();
    }
}
