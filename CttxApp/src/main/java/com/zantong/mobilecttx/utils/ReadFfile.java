package com.zantong.mobilecttx.utils;

import android.content.Context;
import android.text.TextUtils;

import com.zantong.mobilecttx.map.bean.NetLocationBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadFfile {

    /**
     * 读取领卡网点文件
     */
    public static NetLocationBean readNetLocationFile(Context context) {
        String historyStr;
        boolean flag = false;
        NetLocationBean bean = new NetLocationBean();

        try {
//            File urlFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "networktable.txt");
            String filePath = cn.qqtheme.framework.util.FileUtils.icbTxtFilePath(context, cn.qqtheme.framework.util.FileUtils.DOWNLOAD_DIR);
            File urlFile = new File(filePath);

            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);

            List<NetLocationBean.NetLocationElement> list = new ArrayList<>();

            String mimeTypeLine;
            while ((mimeTypeLine = reader.readLine()) != null) {

                NetLocationBean.NetLocationElement element = new NetLocationBean.NetLocationElement();
                NetLocationBean.NetLocationElement.NetQuBean mNetQuBean = new NetLocationBean.NetLocationElement.NetQuBean();
                mimeTypeLine = mimeTypeLine.trim();

                if (!TextUtils.isEmpty(mimeTypeLine)) {
                    String[] demo = mimeTypeLine.split("\\|");
                    mNetQuBean.setNetLocationNumber(demo[4]);
                    mNetQuBean.setNetLocationXiang(demo[3]);
                    mNetQuBean.setNetLocationName(demo[2]);
                    mNetQuBean.setNetLocationCode(demo[1]);
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getNetLocationQu().equals(demo[0])) {
                            list.get(i).getListNet().add(mNetQuBean);
                            flag = true;
                        }
                    }

                    if (!flag) {
                        List<NetLocationBean.NetLocationElement.NetQuBean> listNetQuBean = new ArrayList<>();
                        historyStr = demo[0];
                        historyStr = historyStr.replace('\uFEFF', ' ');
                        historyStr = historyStr.trim();
                        element.setNetLocationQu(historyStr);
                        listNetQuBean.add(mNetQuBean);
                        element.setListNet(listNetQuBean);
                        list.add(element);
                    } else {
                        flag = false;
                    }
                }
            }
            bean.setNetLocationlist(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
