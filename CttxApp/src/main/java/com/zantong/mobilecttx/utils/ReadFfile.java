package com.zantong.mobilecttx.utils;

import android.content.Context;

import com.zantong.mobilecttx.map.bean.NetLocationBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadFfile {

/*    public static String readFile() {
        String str = "";
        try {

            File urlFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "networktable.txt");
//            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "GBK");
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
//            String str = "";
            String mimeTypeLine = null;
            while ((mimeTypeLine = br.readLine()) != null) {
                str = str + mimeTypeLine + "#";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }*/

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

            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            List<NetLocationBean.NetLocationElement> list = new ArrayList<>();

            String mimeTypeLine;
            while ((mimeTypeLine = br.readLine()) != null) {
                NetLocationBean.NetLocationElement element = new NetLocationBean.NetLocationElement();
                NetLocationBean.NetLocationElement.NetQuBean mNetQuBean = new NetLocationBean.NetLocationElement.NetQuBean();
                mimeTypeLine = mimeTypeLine.trim();

                if (!"".equals(mimeTypeLine)) {
                    String[] demo = mimeTypeLine.split("\\|");
                    mNetQuBean.setNetLocationNumber(demo[4]);
                    mNetQuBean.setNetLocationXiang(demo[3]);
                    mNetQuBean.setNetLocationName(demo[2]);
                    mNetQuBean.setNetLocationCode(demo[1]);
//                if(historyStr.equals(demo[0])){
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getNetLocationQu().equals(demo[0])) {
                            list.get(i).getListNet().add(mNetQuBean);
                            flag = true;
                        }
                    }
//                }else{
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

//                }
//                e、lement.setNetLocationQu(demo[0]);
//                list.add(element);
//                str = str+mimeTypeLine+"#";
            }
            bean.setNetLocationlist(list);
//            PublicData.getInstance().mNetLocationBean.setNetLocationlist(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        LogUtils.i("ReadFfile:" + bean.getNetLocationlist().get(0).getListNet().get(0).getNetLocationXiang());
        return bean;
    }
}
