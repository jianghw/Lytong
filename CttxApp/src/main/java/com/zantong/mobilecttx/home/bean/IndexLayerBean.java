package com.zantong.mobilecttx.home.bean;

/**
 * Created by jianghw on 2017/11/3.
 * Description:
 * Update by:
 * Update day:
 */

public class IndexLayerBean {


    /**
     * id : 1
     * pageIdx : 1
     * pageUrl : www.baidu.com
     * imgUrl : http://n.sinaimg.cn/translate/20171103/D7de-fynmzum9843361.jpg
     * show : true
     */

    private int id;
    private int pageIdx;
    private String pageUrl;
    private String imgUrl;
    private boolean show;

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public int getPageIdx() { return pageIdx;}

    public void setPageIdx(int pageIdx) { this.pageIdx = pageIdx;}

    public String getPageUrl() { return pageUrl;}

    public void setPageUrl(String pageUrl) { this.pageUrl = pageUrl;}

    public String getImgUrl() { return imgUrl;}

    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl;}

    public boolean isShow() { return show;}

    public void setShow(boolean show) { this.show = show;}
}
