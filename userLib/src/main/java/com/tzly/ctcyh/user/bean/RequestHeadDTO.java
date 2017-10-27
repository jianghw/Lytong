package com.tzly.ctcyh.user.bean;

/**
 * 银行请求实体封装
 */
public class RequestHeadDTO {

    private String ConsumerId;
    private String TransServiceCode;
    private String RequestDate;
    private String RequestTime;
    private String ConsumerSeqNo;
    private String DvcToken;

    public String getConsumerId() {
        return ConsumerId;
    }

    public void setConsumerId(String consumerId) {
        ConsumerId = consumerId;
    }

    public String getTransServiceCode() {
        return TransServiceCode;
    }

    public void setTransServiceCode(String transServiceCode) {
        TransServiceCode = transServiceCode;
    }

    public String getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(String requestDate) {
        RequestDate = requestDate;
    }

    public String getRequestTime() {
        return RequestTime;
    }

    public void setRequestTime(String requestTime) {
        RequestTime = requestTime;
    }

    public String getConsumerSeqNo() {
        return ConsumerSeqNo;
    }

    public void setConsumerSeqNo(String consumerSeqNo) {
        ConsumerSeqNo = consumerSeqNo;
    }

    public String getDvcToken() {
        return DvcToken;
    }

    public void setDvcToken(String dvcToken) {
        DvcToken = dvcToken;
    }
}
