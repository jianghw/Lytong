package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.user.bean.MessageCountResponse;
import com.zantong.mobilecttx.user.bean.MessageDetailResponse;
import com.zantong.mobilecttx.order.bean.MessageResponse;
import com.zantong.mobilecttx.user.bean.MessageTypeResponse;
import com.zantong.mobilecttx.user.dto.MegDTO;
import com.zantong.mobilecttx.user.dto.MessageDetailDTO;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface IMessageService {
    /**
     * 2.4.20.1获取消息类别列表
     *
     * @param baseDTO
     * @return
     */
    @POST("message/findAllMessage")
    Observable<MessageTypeResponse> messageFindAll(@Body BaseDTO baseDTO);

    /**
     * 2.4.21获取消息详情列表
     *
     * @param bean
     * @return
     */
    @POST("message/findMessageDetailByMessageId")
    Observable<MessageResponse> findMessageDetailByMessageId(@Body MegDTO bean);

    /**
     * 2.4.22获取消息详情
     *
     * @param bean
     * @return
     */
    @POST("message/findMessageDetail")
    Observable<MessageDetailResponse> findMessageDetail(@Body MessageDetailDTO bean);

    /**
     * 2.4.24删除消息
     *
     * @param megDTO
     * @return
     */
    @POST("message/deleteMessageDetail")
    Observable<MessageResponse> deleteMessageDetail(@Body MegDTO megDTO);

    /**
     * 37.获取所有未读消息数量
     */
    @POST("message/countMessageDetail")
    Observable<MessageCountResponse> countMessageDetail(@Body BaseDTO baseDTO);
}
