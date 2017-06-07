package com.zantong.mobilecttx.base.bean;

/**
 * 分组项
 * @author zyb 
 *
 *  
 *    *  *   *  *     
 *  *      *      *   
 *  *             *   
 *   *           *    
 *      *     *       
 *         *          
 * 
 *
 * create at 17/5/4 下午4:54
 */
public class SuperBean {
    String name;
    boolean hasArrow;

    public SuperBean(String name, boolean hasArrow) {
        this.name = name;
        this.hasArrow = hasArrow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasArrow() {
        return hasArrow;
    }

    public void setHasArrow(boolean hasArrow) {
        this.hasArrow = hasArrow;
    }
}
