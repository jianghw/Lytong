package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.user.bean.MessageCountResult;
import com.zantong.mobilecttx.user.bean.MessageDetailResult;
import com.zantong.mobilecttx.order.bean.MessageResult;
import com.zantong.mobilecttx.user.bean.MessageTypeResult;
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
    Observable<MessageTypeResult> messageFindAll(@Body BaseDTO baseDTO);

    /**
     * 2.4.21获取消息详情列表
     *
     * @param bean
     * @return
     */
    @POST("message/findMessageDetailByMessageId")
    Observable<MessageResult> findMessageDetailByMessageId(@Body MegDTO bean);

    /**
     * 2.4.22获取消息详情
     *
     * @param bean
     * @return
     */
    @POST("message/findMessageDetail")
    Observable<MessageDetailResult> findMessageDetail(@Body MessageDetailDTO bean);

    /**
     * 2.4.24删除消息
     *
     * @param megDTO
     * @return
     */
    @POST("message/deleteMessageDetail")
    Observable<MessageResult> deleteMessageDetail(@Body MegDTO megDTO);

    /**
     * 37.获取所有未读消息数量
     */
    @POST("message/countMessageDetail")
    Observable<MessageCountResult> countMessageDetail(@Body BaseDTO baseDTO);
}
