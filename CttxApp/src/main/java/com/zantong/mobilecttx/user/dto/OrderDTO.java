package com.zantong.mobilecttx.user.dto;

/**
 * 实体封装
 * @author Sandy
 * create at 16/6/1 下午7:34
 */
public class OrderDTO {

    private String usrid; //用户ID
    private int pageflag;//分页标识 1-最后一页 2-上一页 3-下一页 4-当前页  目前固定值为4 2016-06-12 11:55:19
    private int currpage;//当前页
    private int pagenum;//每页总数

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }

    public int getPageflag() {
        return pageflag;
    }

    public void setPageflag(int pageflag) {
        this.pageflag = pageflag;
    }

    public int getCurrpage() {
        return currpage;
    }

    public void setCurrpage(int currpage) {
        this.currpage = currpage;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }
}
