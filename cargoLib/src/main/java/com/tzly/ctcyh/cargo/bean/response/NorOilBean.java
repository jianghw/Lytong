package com.tzly.ctcyh.cargo.bean.response;

import java.util.List;

/**
 * Created by jianghw on 2017/10/24.
 * Description:
 * Update by:
 * Update day:
 */

public class NorOilBean {


    /**
     * SINOPEC : [{"id":36,"merchantId":13,"name":"中石油100","type":13,"price":0.01,"description":"中石油车行易100元","status":1,"createTime":0,"expiredTime":86400,"discount":1},{"id":37,"merchantId":13,"name":"中石油200","type":13,"price":0.02,"description":"中石油车行易200元","status":1,"createTime":0,"expiredTime":86400,"discount":1},{"id":38,"merchantId":13,"name":"中石油500","type":13,"price":0.03,"description":"中石油车行易500元","status":1,"createTime":0,"expiredTime":86400,"discount":1}]
     * oilCard : 1993922929292929292
     * CNPC : [{"id":39,"merchantId":14,"name":"中石化100","type":13,"price":10,"description":"中石化鼎信100元993折","status":1,"createTime":0,"expiredTime":86400,"discount":0.993},{"id":40,"merchantId":14,"name":"中石化200","type":13,"price":0.2,"description":"中石化鼎信200元993折","status":1,"createTime":0,"expiredTime":86400,"discount":0.993},{"id":41,"merchantId":14,"name":"中石化500","type":13,"price":0.3,"description":"中石化鼎信300元993折","status":1,"createTime":0,"expiredTime":86400,"discount":0.993},{"id":42,"merchantId":14,"name":"中石化1000","type":13,"price":0.01,"description":"中石化鼎信300元993折","status":1,"createTime":0,"expiredTime":86400,"discount":0.993}]
     * oilType : 中石化
     */

    private String oilCard;
    private String oilType;
    private String img;
    private List<CNPCBean> SINOPEC;
    private List<CNPCBean> CNPC;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOilCard() {
        return oilCard;
    }

    public void setOilCard(String oilCard) {
        this.oilCard = oilCard;
    }

    public String getOilType() {
        return oilType;
    }

    public void setOilType(String oilType) {
        this.oilType = oilType;
    }

    public List<CNPCBean> getSINOPEC() {
        return SINOPEC;
    }

    public void setSINOPEC(List<CNPCBean> SINOPEC) {
        this.SINOPEC = SINOPEC;
    }

    public List<CNPCBean> getCNPC() {
        return CNPC;
    }

    public void setCNPC(List<CNPCBean> CNPC) {
        this.CNPC = CNPC;
    }

    public static class SINOPECBean {
        /**
         * id : 36
         * merchantId : 13
         * name : 中石油100
         * type : 13
         * price : 0.01
         * description : 中石油车行易100元
         * status : 1
         * createTime : 0
         * expiredTime : 86400
         * discount : 1
         */

        private int id;
        private int merchantId;
        private String name;
        private int type;
        private double price;
        private String description;
        private int status;
        private int createTime;
        private int expiredTime;
        private int discount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public int getExpiredTime() {
            return expiredTime;
        }

        public void setExpiredTime(int expiredTime) {
            this.expiredTime = expiredTime;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }
    }

    public static class CNPCBean {
        /**
         * id : 39
         * merchantId : 14
         * name : 中石化100
         * type : 13
         * price : 10
         * description : 中石化鼎信100元993折
         * status : 1
         * createTime : 0
         * expiredTime : 86400
         * discount : 0.993
         */

        private String id;
        private int merchantId;
        private String name;
        private String type;
        private String price;
        private String description;
        private int status;
        private int createTime;
        private int expiredTime;
        private String discount;

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        private boolean select;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public int getExpiredTime() {
            return expiredTime;
        }

        public void setExpiredTime(int expiredTime) {
            this.expiredTime = expiredTime;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }
    }
}
