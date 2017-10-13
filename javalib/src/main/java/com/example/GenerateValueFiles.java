package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * 用于计算屏幕尺寸
 */
public class GenerateValueFiles {

    private int mBaseW;
    private int mBaseH;

    private String mDirStr = "./res";
    /**
     * 模板样式
     */
    private final static String defaultW = "<dimen name=\"res_x_{0}\">{1}dp</dimen>\n";
    private final static String defaultH = "<dimen name=\"res_y_{0}\">{1}dp</dimen>\n";
    private final static String templateW = "<dimen name=\"res_x_{0}\">{1}px</dimen>\n";
    private final static String templateH = "<dimen name=\"res_y_{0}\">{1}px</dimen>\n";

    /**
     * {0}-HEIGHT 值模板values-480x320
     */
    private final static String VALUE_TEMPLATE = "values-{0}x{1}";
    private final static String VALUE_DEFAULT = "values";

    private final static String SUPPORT_DIMENSION = "320,480;" +
            "480,800;480,854;" +
            "540,960;600,1024;" +
            "720,1184;720,1196;720,1280;" +
            "768,1024;768,1280;" +
            "800,1280;" +
            "1080,1794;1080,1812;1080,1920;" +
            "1440,2560;";

    private String mSupportDimension = SUPPORT_DIMENSION;

    public static void main(String[] args) {
        //        int baseW = 1080;
        //        int baseH = 1920;
        int baseW = 750;
        int baseH = 1334;
        /**
         * 额外的尺寸值
         */
        String addition = "";
        try {
            if (args.length >= 3) {
                baseW = Integer.parseInt(args[0]);
                baseH = Integer.parseInt(args[1]);
                addition = args[2];
            } else if (args.length >= 2) {
                baseW = Integer.parseInt(args[0]);
                baseH = Integer.parseInt(args[1]);
            } else if (args.length >= 1) {
                addition = args[0];
            }
        } catch (NumberFormatException e) {
            System.err.println("right input params : java -jar xxx.jar width height w,h_w,h_..._w,h;");
            e.printStackTrace();
            System.exit(-1);
        }

        new GenerateValueFiles(baseW, baseH, addition).generate();
    }

    public GenerateValueFiles(int baseX, int baseY, String supportDimension) {
        this.mBaseW = baseX;
        this.mBaseH = baseY;

        if (!this.mSupportDimension.contains(baseX + "," + baseY)) {
            this.mSupportDimension += baseX + "," + baseY + ";";
        }
        this.mSupportDimension += validateInput(supportDimension);
        System.out.println("额外数值===>" + supportDimension);

        File dir = new File(mDirStr);
        if (!dir.exists()) {
            dir.mkdir();
        }
        System.out.println("输出路径===>" + dir.getAbsoluteFile());
    }

    /**
     * 验证输入 宽 高
     *
     * @param supportStr w,h_...w,h;
     */
    private String validateInput(String supportStr) {
        StringBuffer stringBuffer = new StringBuffer();

        String[] vals;
        if (supportStr.contains("_")) {
            vals = supportStr.split("_");
        } else if (supportStr.contains("&")) {
            vals = supportStr.split("&");
        } else if (supportStr.contains("/")) {
            vals = supportStr.split("/");
        } else {
            vals = supportStr.split(" ");
        }

        int w, h;
        String[] wh;//宽高单对
        for (String value : vals) {
            try {
                if (value == null || value.trim().length() == 0) continue;

                wh = value.split(",");
                w = Integer.parseInt(wh[0]);
                h = Integer.parseInt(wh[1]);
            } catch (Exception e) {
                System.out.println("skip invalidate params : w,h = " + value);
                continue;
            }
            stringBuffer.append(w + "," + h + ";");
        }

        return stringBuffer.toString();
    }

    /**
     * 对所有数值进行差分
     */
    public void generate() {
        String[] vals = mSupportDimension.split(";");
        for (String value : vals) {
            String[] wh = value.split(",");
            generateXmlFile(Integer.parseInt(wh[0]), Integer.parseInt(wh[1]));

        }

        defaultXmlFile(1080, 1920);
    }

    /**
     * 文本内容拼接
     */
    public void generateXmlFile(int w, int h) {
        StringBuffer stringBufferW = new StringBuffer();
        stringBufferW.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        stringBufferW.append("<resources>");
        //宽值比例
        float ratioW = w * 1.0f / mBaseW;
        System.out.println("width ===>: " + w + "," + mBaseW + "," + ratioW);

        for (int i = 1; i < mBaseW; i++) {
            stringBufferW.append(
                    templateW.replace("{0}", i + "").replace("{1}", change(ratioW * i) + ""));
        }
        stringBufferW.append(templateW.replace("{0}", mBaseW + "").replace("{1}", w + ""));
        stringBufferW.append("</resources>");

        StringBuffer stringBufferH = new StringBuffer();
        stringBufferH.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        stringBufferH.append("<resources>");
        float ratioH = h * 1.0f / mBaseH;
        System.out.println("height ===>: " + h + "," + mBaseH + "," + ratioH);

        for (int i = 1; i < mBaseH; i++) {
            stringBufferH.append(templateH.replace("{0}", i + "").replace("{1}", change(ratioH * i) + ""));
        }
        stringBufferH.append(templateH.replace("{0}", mBaseH + "").replace("{1}", h + ""));
        stringBufferH.append("</resources>");
        //TODO 注意这里 宽高显示反调
        File fileDir = new File(mDirStr + File.separator + VALUE_TEMPLATE.replace("{0}", h + "").replace("{1}", w + ""));
        fileDir.mkdir();

        File xFile = new File(fileDir.getAbsolutePath(), "dimen_w.xml");
        File yFile = new File(fileDir.getAbsolutePath(), "dimen_h.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(xFile));
            pw.print(stringBufferW.toString());
            pw.close();
            pw = new PrintWriter(new FileOutputStream(yFile));
            pw.print(stringBufferH.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void defaultXmlFile(int w, int h) {
        StringBuffer stringBufferW = new StringBuffer();
        stringBufferW.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        stringBufferW.append("<resources>");
        //宽值比例
        float ratioW = w * 1.0f / mBaseW;
        System.out.println("width ===>: " + w + "," + mBaseW + "," + ratioW);

        for (int i = 1; i < mBaseW; i++) {
            stringBufferW.append(
                    defaultW.replace("{0}", i + "").replace("{1}", change(px2dip(ratioW * i)) + ""));
        }
        stringBufferW.append(defaultW.replace("{0}", mBaseW + "").replace("{1}", w + ""));
        stringBufferW.append("</resources>");

        StringBuffer stringBufferH = new StringBuffer();
        stringBufferH.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        stringBufferH.append("<resources>");
        float ratioH = h * 1.0f / mBaseH;
        System.out.println("height ===>: " + h + "," + mBaseH + "," + ratioH);

        for (int i = 1; i < mBaseH; i++) {
            stringBufferH.append(
                    defaultH.replace("{0}", i + "").replace("{1}", change(px2dip(ratioH * i)) + ""));
        }
        stringBufferH.append(defaultH.replace("{0}", mBaseH + "").replace("{1}", h + ""));
        stringBufferH.append("</resources>");

        //TODO 注意这里 宽高显示反调
        File fileDir = new File(mDirStr + File.separator + VALUE_DEFAULT);
        fileDir.mkdir();

        File xFile = new File(fileDir.getAbsolutePath(), "dimen_w.xml");
        File yFile = new File(fileDir.getAbsolutePath(), "dimen_h.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(xFile));
            pw.print(stringBufferW.toString());
            pw.close();
            pw = new PrintWriter(new FileOutputStream(yFile));
            pw.print(stringBufferH.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数值 格式化
     */
    public static float change(float a) {
        int temp = (int) (a * 100);
        return temp / 100f;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dip(float pxValue) {
        final float scale = 480 / 160;
        return pxValue / scale + 0.5f;
    }

}