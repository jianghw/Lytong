package com.zantong.mobilecttx.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by zhengyingbing on 16/11/18.
 */

public class BaiduIntentUtil {
    /**
     * 启动BaiduApp进行导航
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,11:23
     * <h3>UpdateTime</h3> 2016/6/27,11:23
     * <h3>CreateAuthor</h3> luzhenbang
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @param context 上下文
     * @param origin 必选  起点名称或经纬度，或者可同时提供名称和经纬度，此时经纬度优先级高，将作为导航依据，名称只负责展示。例如： latlng:34.264642646862,108.95108518068|name:我家
     * @param destination 必选 终点名称或经纬度，或者可同时提供名称和经纬度，此时经纬度优先级高，将作为导航依据，名称只负责展示。
     * @param mode 必选 导航模式，固定为transit、driving、walking，分别表示公交、驾车和步行
     * @param region 必选 城市名或县名 当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市。
     * @param origin_region 必选  起点所在城市或县
     * @param destination_region 必选  终点所在城市或县
     * @param coord_type 可选 坐标类型，可选参数，默认为bd09经纬度坐标。
     * @param zoom 可选 展现地图的级别，默认为视觉最优级别。
     * @param src 必选 调用来源，规则：companyName|appName。
     */
    public static  void goToNaviActivity(Context context, String origin , String destination  , String mode , String region , String origin_region , String destination_region
            , String coord_type , String zoom , String src){
        StringBuffer stringBuffer  = new StringBuffer("intent://map/direction?origin=");
        stringBuffer.append(origin)
                .append("&destination=").append(destination)
                .append("&mode=").append(mode);
        if (!TextUtils.isEmpty(region)){
            stringBuffer.append("&region=").append(region);
        }
        if (!TextUtils.isEmpty(origin_region)){
            stringBuffer.append("&origin_region=").append(origin_region);
        }
        if (!TextUtils.isEmpty(destination_region)){
            stringBuffer.append("&destination_region=").append(destination_region);
        }
        if (!TextUtils.isEmpty(coord_type)){
            stringBuffer.append("&coord_type=").append(coord_type);
        }


        if (!TextUtils.isEmpty(zoom)){
            stringBuffer.append("&zoom=").append(zoom);
        }
        stringBuffer.append("&src=").append(src).append("#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
        String intentString = stringBuffer.toString();
        try {
            Intent intent  = Intent.getIntent(intentString);
            context.startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }


    /**
     * 根据包名检测某个APP是否安装
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,13:02
     * <h3>UpdateTime</h3> 2016/6/27,13:02
     * <h3>CreateAuthor</h3> luzhenbang
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @param packageName 包名
     * @return true 安装 false 没有安装
     */
    public static boolean isInstallByRead(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }
}
