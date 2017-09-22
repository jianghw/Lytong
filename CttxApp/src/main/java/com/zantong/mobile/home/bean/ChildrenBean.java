package com.zantong.mobile.home.bean;

/**
 * Created by jianghw on 2017/8/25.
 * Description:
 * Update by:
 * Update day:
 */
public class ChildrenBean {
    /**
     * id : 9
     * parentId : 1
     * title : 子目录12
     * subTitle : 副标题
     * showType : 0
     * moduleType : 0
     * img : http://h5.liyingtong.com/alipay/img/pic_2.jpg
     * targetType : 1
     * targetPath : http://h5.liyingtong.com/alipay/
     * state : 1
     * sort : 2
     */

    private int id;
    private int parentId;
    private String title;
    private String subTitle;
    private int showType;
    private int moduleType;
    private String img;
    private int targetType;
    private String targetPath;
    private int state;
    private int sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public int getModuleType() {
        return moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
