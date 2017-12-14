package com.tzly.ctcyh.router.bean.response;

import java.util.List;

/**
 * Created by jianghw on 2017/8/14.
 * Description:
 * Update by:
 * Update day:
 */
public class SubjectGoodsData {
    /**
     * goodsList : [{"goods":{"goodsId":9,"merchantId":3,"name":"A套餐手动挡(工作日)","type":4,"price":960,"description":"A套餐手动挡(工作日)\n课程时间：\n4课时/8小时(原价：1040元，现价：960元)\n1.此套餐只限工作日使用，双休日及国定假日无法使用\n2.著名驾校强强联手\n3.专业驾校培训金牌教练\n4.科目专业练车场地\n5.全程专业指导提高考试合格率\n6.地点接送省心省力","status":1,"createTime":1502260750,"expiredTime":86400,"discount":1},"details":["4","8"]},{"goods":{"goodsId":10,"merchantId":3,"name":"B套餐手动挡(全时段)","type":4,"price":1080,"description":"B套餐手动挡(全时段)\n课程时间：\n4课时/8小时(原价：1120元，现价：1080元)\n1.此套餐在畅通练车服务时间内都可以使用（春节期间除外）\n2.著名驾校强强联手\n3.专业驾校培训金牌教练\n4.科目专业练车场地\n5.全程专业指导提高考试合格率\n6.地点接送省心省力","status":1,"createTime":1502260750,"expiredTime":86400,"discount":1},"details":["4","8"]},{"goods":{"goodsId":11,"merchantId":3,"name":"C套餐自动挡(工作日)","type":4,"price":1080,"description":"C套餐自动挡(工作日)\n课程时间：\n4课时/8小时(原价：1120元，现价：1080元)\n1.此套餐只限工作日使用，双休日及国定假日无法使用\n2.著名驾校强强联手\n3.专业驾校培训金牌教练\n4.科目专业练车场地\n5.全程专业指导提高考试合格率\n6.地点接送省心省力","status":1,"createTime":1502260750,"expiredTime":86400,"discount":1},"details":["4","8"]},{"goods":{"goodsId":12,"merchantId":3,"name":"D套餐自动挡(全时段)","type":4,"price":1200,"description":"D套餐自动挡(全时段)\n课程时间：\n4课时/8小时(原价：1280元，现价：1200元)\n1.此套餐在畅通练车服务时间内都可以使用（春节期间除外）\n2.著名驾校强强联手\n3.专业驾校培训金牌教练\n4.科目专业练车场地\n5.全程专业指导提高考试合格率\n6.地点接送省心省力","status":1,"createTime":1502260750,"expiredTime":86400,"discount":1},"details":["4","8"]}]
     * remark : {"remark2":"补充说明\n1.订单生成后客服将在1小时内电话和您确认，请预留有效手机号码。\n2.若订单支付失败，将在24小时内将原款退换给您。\n3.在教学过程中，您有任何不满意的地方，可随时关注\u201c畅通卡车友会\u201d微信公众号留言，也可拨打021-50898792。"}
     */

    private RemarkBean remark;
    private List<SubjectGoodsBean> goodsList;

    public RemarkBean getRemark() {
        return remark;
    }

    public void setRemark(RemarkBean remark) {
        this.remark = remark;
    }

    public List<SubjectGoodsBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<SubjectGoodsBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class RemarkBean {
        /**
         * remark2 : 补充说明
         * 1.订单生成后客服将在1小时内电话和您确认，请预留有效手机号码。
         * 2.若订单支付失败，将在24小时内将原款退换给您。
         * 3.在教学过程中，您有任何不满意的地方，可随时关注“畅通卡车友会”微信公众号留言，也可拨打021-50898792。
         */

        private String remark2;

        public String getRemark2() {
            return remark2;
        }

        public void setRemark2(String remark2) {
            this.remark2 = remark2;
        }
    }

}