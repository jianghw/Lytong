package cn.qqtheme.framework.bean.response;

import java.util.List;

/**
 * Created by jianghw on 2017/7/6.
 * Description:
 * Update by:
 * Update day:
 */

public class SubjectGoodsBean {

    /**
     * goods : {"goodsId":9,"merchantId":3,
     * "name":"A套餐手动挡(工作日)","type":4,"price":960,
     * "description":"A套餐手动挡(工作日)\n课程时间：\n4课时/8小时(原价：1040元，现价：960元)\n1.此套餐只限工作日使用，双休日及国定假日无法使用\n2.著名驾校强强联手\n3.专业驾校培训金牌教练\n4.科目专业练车场地\n5.全程专业指导提高考试合格率\n6.地点接送省心省力",
     * "status":1,"createTime":1502260750,"expiredTime":86400,"discount":1}
     * details : ["4","8"]
     */

    private GoodsBean goods;
    private List<String> details;

    private boolean isChoice;

    public SubjectGoodsBean(GoodsBean goods, boolean isChoice) {
        this.goods = goods;
        this.isChoice = isChoice;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public static class GoodsBean {
        /**
         * goodsId : 9
         * merchantId : 3
         * name : A套餐手动挡(工作日)
         * type : 4
         * price : 960
         * description : A套餐手动挡(工作日)
         课程时间：
         4课时/8小时(原价：1040元，现价：960元)
         1.此套餐只限工作日使用，双休日及国定假日无法使用
         2.著名驾校强强联手
         3.专业驾校培训金牌教练
         4.科目专业练车场地
         5.全程专业指导提高考试合格率
         6.地点接送省心省力
         * status : 1
         * createTime : 1502260750
         * expiredTime : 86400
         * discount : 1
         */

        private int goodsId;
        private int merchantId;
        private String name;
        private int type;
        private int price;
        private String description;
        private int status;
        private int createTime;
        private int expiredTime;
        private int discount;

        public GoodsBean(String name) {
            this.name = name;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
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
}
