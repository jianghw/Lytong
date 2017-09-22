package com.zantong.mobile.common.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zantong.mobile.common.adapter.CommonTwoLevelMenuAdapter;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobile.base.fragment.BaseListFragment;
import com.zantong.mobile.common.bean.CommonTwoLevelMenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoujie on 2017/1/3.
 */

public class CommonTwoLevelMenuFragment extends BaseListFragment<CommonTwoLevelMenuBean> {

    public static CommonTwoLevelMenuFragment newInstance(int typeId) {
        CommonTwoLevelMenuFragment f = new CommonTwoLevelMenuFragment();
        Bundle args = new Bundle();
        args.putInt("type", typeId);
        f.setArguments(args);
        return f;
    }

    private List<CommonTwoLevelMenuBean> commonTwoLevelMenuBeanList;
    private int type;

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getArguments();
        type = bundle.getInt("type", 0);
        commonTwoLevelMenuBeanList = new ArrayList<>();
        showData();
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        onItemClick(data);
    }

    private void onItemClick(Object data) {
        CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean) data;

        int resultcode = 0;
        if (type == 0) {  //婚姻状况
            resultcode = 1000;
        } else if (type == 1) {//教育程度
            resultcode = 1001;
        } else if (type == 2) {//住宅类型
            resultcode = 1002;
        } else if (type == 3) {//行业类别
            resultcode = 1003;
        } else if (type == 4) {//职业
            resultcode = 1004;
        } else if (type == 5) {//职务
            resultcode = 1005;
        } else if (type == 6) {//单位性质
            resultcode = 1006;
        } else if (type == 7) {//性别
            resultcode = 1007;
        } else if (type == 8) {//联系人
            resultcode = 1008;
        } else if (type == 9) {//自动还款类型
            resultcode = 1009;
        } else if (type == 10) {//电子对账单标识
            resultcode = 1010;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", commonTwoLevelMenuBean);
        intent.putExtras(bundle);
        getActivity().setResult(resultcode, intent);
        getActivity().finish();
    }

    /**
     * 显示数据
     */
    private void showData() {
        if (type == 0) {  //婚姻状况
            maritalStatus();
        } else if (type == 1) {//教育程度
            educationLevel();
        } else if (type == 2) {//住宅类型
            housingType();
        } else if (type == 3) {//行业类别
            industryCategories();
        } else if (type == 4) {//职业
            occupation();
        } else if (type == 5) {//职务
            post();
        } else if (type == 6) {//单位性质
            unitProperty();
        } else if (type == 7) {//性别
            gender();
        } else if (type == 8) {//联系人
            contacts();
        } else if (type == 9) {//自动还款类型
            automaticRepaymentType();
        } else if (type == 10) {//电子对账单标识
            ElectronicBillIdentification();
        }
        setDataResult(commonTwoLevelMenuBeanList);
    }

    /**
     * 电子对账单标识
     */
    private void ElectronicBillIdentification() {
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(0, "否"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(1, "是"));
    }

    /**
     * 自动还款类型
     */
    private void automaticRepaymentType() {
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(9, "不开通"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(0, "人民币自动转存"));
    }

    /**
     * 联系人
     */
    private void contacts() {
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(1, "父子"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(2, "母子"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(3, "兄弟姐妹"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(5, "夫妻"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(8, "朋友"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(9, "同事"));
    }

    /**
     * 性别
     */
    private void gender() {
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(1, "男"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(2, "女"));
    }

    /**
     * 单位性质
     */
    private void unitProperty() {
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(20, "集体"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(30, "国有控股"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(40, "集体控股"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(50, "三资"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(60, "私营"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(70, "个体"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(80, "外贸"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(90, "股份合作"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(110, "民营"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(120, "联营"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(130, "乡镇企业;"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(190, "其它;"));
    }

    /**
     * 职务
     */
    private void post() {
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(2, "部、省级、副部、副省级"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(3, "董事/司、局、地、厅级 "));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(4, "总经理/县处级"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(5, "科级/部门经理"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(6, "职员/科员级"));
    }

    /**
     * 职业
     */
    private void occupation() {
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(1, "公务员"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(2, "事业单位员工;"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(3, "职员"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(4, "军人"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(5, "自由职业者"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(6, "工人"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(7, "农民"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(30, "私人业主"));
    }

    /**
     * 行业类别
     */
    private void industryCategories() {
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(1, "证券"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(2, "财政"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(3, "商业、个体、租赁"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(4, "机关团体"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(5, "工业"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(6, "保险"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(7, "房地产"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(8, "合资"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(9, "其他"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(10, "邮电、计算机、通讯"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(11, "水电气供应"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(12, "科教文卫"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(13, "部队系统"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(14, "农林牧渔"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(15, "社会服务业"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(16, "银行"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(17, "采矿业"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(18, "制造业"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(19, "建筑业"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(20, "交通、仓储、物流"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(21, "其它金融业"));
    }

    /**
     * 婚姻状况
     */
    private void maritalStatus() {
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(1, "未婚"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(2, "已婚"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(6, "其它"));
    }

    /**
     * 教育程度
     */
    private void educationLevel() {
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(1, "博士及以上"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(2, "硕士研究生"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(3, "大学本科"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(4, "大学大专/电大"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(5, "中专"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(6, "技工学校"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(7, "高中"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(8, "初中"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(9, "小学及以下"));
    }

    /**
     * 住宅类型
     */
    private void housingType() {
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(1, "自有住房"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(2, "分期付款购房"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(3, "租房"));
        commonTwoLevelMenuBeanList.add(new CommonTwoLevelMenuBean(4, "其它"));
    }

    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    public BaseAdapter<CommonTwoLevelMenuBean> createAdapter() {
        return new CommonTwoLevelMenuAdapter();
    }
}
