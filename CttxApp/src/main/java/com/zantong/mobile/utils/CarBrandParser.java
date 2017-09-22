package com.zantong.mobile.utils;

import android.util.Xml;

import com.zantong.mobile.car.bean.CarBrandBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoujie on 2016/11/16.
 */

public class CarBrandParser {

    public static List<CarBrandBean> parse(InputStream is) throws Exception {
        List<CarBrandBean> carBrandBeanList = null;
        CarBrandBean book = null;

        XmlPullParser parser = Xml.newPullParser(); //由android.util.Xml创建一个XmlPullParser实例
        parser.setInput(is, "UTF-8");               //设置输入流 并指明编码方式

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    carBrandBeanList = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("carbrand")) {
                        book = new CarBrandBean();
                    } else if (parser.getName().equals("id")) {
                        eventType = parser.next();
                        book.setId(Integer.parseInt(parser.getText()));
                    } else if (parser.getName().equals("name")) {
                        eventType = parser.next();
                        book.setBrandName(parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("carbrand")) {
                        carBrandBeanList.add(book);
                        book = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return carBrandBeanList;
    }

    public static String serialize(List<CarBrandBean> carBrandBeanList) throws Exception {

        XmlSerializer serializer = Xml.newSerializer(); //由android.util.Xml创建一个XmlSerializer实例
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);   //设置输出方向为writer
        serializer.startDocument("UTF-8", true);
        serializer.startTag("", "carbrands");
        for (CarBrandBean carBrandBean : carBrandBeanList) {
            serializer.startTag("", "carbrand");

            serializer.startTag("", "id");
            serializer.text(carBrandBean.getId() + "");
            serializer.endTag("", "id");

            serializer.startTag("", "name");
            serializer.text(carBrandBean.getBrandName());
            serializer.endTag("", "name");

            serializer.endTag("", "carbrand");
        }
        serializer.endTag("", "carbrands");
        serializer.endDocument();

        return writer.toString();
    }
}
