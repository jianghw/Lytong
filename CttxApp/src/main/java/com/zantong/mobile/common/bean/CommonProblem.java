package com.zantong.mobile.common.bean;

import java.io.Serializable;

/**
 * Created by zhoujie on 2016/12/23.
 */

public class CommonProblem implements Serializable{
    private int problemId;
    private String problemTitle;
    private String problemChileTitle;
    private String problemDetail;

    public CommonProblem(int problemId, String problemTitle, String problemChileTitle, String problemDetail){
        this.problemDetail = problemDetail;
        this.problemId = problemId;
        this.problemTitle = problemTitle;
        this.problemChileTitle = problemChileTitle;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public String getProblemDetail() {
        return problemDetail;
    }

    public void setProblemDetail(String problemDetail) {
        this.problemDetail = problemDetail;
    }

    public String getProblemChileTitle() {
        return problemChileTitle;
    }

    public void setProblemChileTitle(String problemChileTitle) {
        this.problemChileTitle = problemChileTitle;
    }
}
