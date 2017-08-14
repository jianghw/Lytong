package cn.qqtheme.framework.contract.bean;

/**
 * Created by zhengyingbing on 16/9/13.
 * Description:
 * Update by:
 * Update day:
 */
public class BaseResult {
    int responseCode;
    String responseDesc;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }
}
