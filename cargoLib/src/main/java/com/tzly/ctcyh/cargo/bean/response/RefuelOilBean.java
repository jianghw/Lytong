package com.tzly.ctcyh.cargo.bean.response;

import java.util.List;

/**
 * Created by jianghw on 2017/10/24.
 * Description:
 * Update by:
 * Update day:
 */

public class RefuelOilBean {


    /**
     * oilCard : 1111222222222222222
     * oilType : 中石化
     * img : static-resources/images/oil.png
     * cardInfo : [{"price":"10.00","goodsId":"40","discount":"0.993","type":"13"},{"price":"0.20","goodsId":"41","discount":"0.993","type":"13"},{"price":"0.30","goodsId":"42","discount":"0.993","type":"13"}]
     */

    private String oilCard;
    private String oilType;
    private String img;
    private List<CardInfoBean> cardInfo;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<CardInfoBean> getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(List<CardInfoBean> cardInfo) {
        this.cardInfo = cardInfo;
    }

    public static class CardInfoBean {
        /**
         * price : 10.00
         * goodsId : 40
         * discount : 0.993
         * type : 13
         */

        private String price;
        private String goodsId;
        private String discount;
        private String type;

        private boolean select;

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
