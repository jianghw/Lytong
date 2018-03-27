package com.tzly.ctcyh.java.response.oil;


import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;

/**
 * 油卡
 */

public class OilCardsResponse extends BaseResponse {


    /**
     * responseCode : 2000
     * data : {"SINOPEC":[{"id":39,"merchantId":14,"name":"中石化100元在线充值","type":13,"price":100,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.995,"settlementPrice":0},{"id":40,"merchantId":14,"name":"中石化200元在线充值","type":13,"price":200,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.995,"settlementPrice":0},{"id":41,"merchantId":14,"name":"中石化500元在线充值","type":13,"price":500,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.995,"settlementPrice":0},{"id":42,"merchantId":14,"name":"中石化1000元在线充值","type":13,"price":0.01,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.995,"settlementPrice":0}],"CNPC":[{"id":36,"merchantId":13,"name":"中石油100元在线充值","type":13,"price":100,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":1,"settlementPrice":0},{"id":37,"merchantId":13,"name":"中石油200元在线充值","type":13,"price":200,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":1,"settlementPrice":0},{"id":38,"merchantId":13,"name":"中石油500元在线充值","type":13,"price":500,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":1,"settlementPrice":0}],"CNPCCard":"9282828282223822","SINOPECCard":"1919292292292929292"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * SINOPEC : [{"id":39,"merchantId":14,"name":"中石化100元在线充值","type":13,"price":100,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.995,"settlementPrice":0},{"id":40,"merchantId":14,"name":"中石化200元在线充值","type":13,"price":200,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.995,"settlementPrice":0},{"id":41,"merchantId":14,"name":"中石化500元在线充值","type":13,"price":500,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.995,"settlementPrice":0},{"id":42,"merchantId":14,"name":"中石化1000元在线充值","type":13,"price":0.01,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.995,"settlementPrice":0}]
         * CNPC : [{"id":36,"merchantId":13,"name":"中石油100元在线充值","type":13,"price":100,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":1,"settlementPrice":0},{"id":37,"merchantId":13,"name":"中石油200元在线充值","type":13,"price":200,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":1,"settlementPrice":0},{"id":38,"merchantId":13,"name":"中石油500元在线充值","type":13,"price":500,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":1,"settlementPrice":0}]
         * CNPCCard : 9282828282223822
         * SINOPECCard : 1919292292292929292
         */

        private String CNPCCard;
        private String SINOPECCard;
        private List<SINOPECBean> SINOPEC;
        private List<SINOPECBean> CNPC;

        public String getCNPCCard() {
            return CNPCCard;
        }

        public void setCNPCCard(String CNPCCard) {
            this.CNPCCard = CNPCCard;
        }

        public String getSINOPECCard() {
            return SINOPECCard;
        }

        public void setSINOPECCard(String SINOPECCard) {
            this.SINOPECCard = SINOPECCard;
        }

        public List<SINOPECBean> getSINOPEC() {
            return SINOPEC;
        }

        public void setSINOPEC(List<SINOPECBean> SINOPEC) {
            this.SINOPEC = SINOPEC;
        }

        public List<SINOPECBean> getCNPC() {
            return CNPC;
        }

        public void setCNPC(List<SINOPECBean> CNPC) {
            this.CNPC = CNPC;
        }

    }

}
