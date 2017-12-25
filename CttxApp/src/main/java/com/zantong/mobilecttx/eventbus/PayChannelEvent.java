package com.zantong.mobilecttx.eventbus;

/**
 * 跳转支付页面
 */
public class PayChannelEvent {

    private final String channel;

    public PayChannelEvent(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }
}
