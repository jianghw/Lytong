package com.tzly.ctcyh.cargo.api;


import com.tzly.ctcyh.cargo.bean.response.ScoreCaptchaResponse;
import com.tzly.ctcyh.cargo.bean.response.ScoreResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface ILienseService {
    /**
     * 2.1 获取验证码
     */
    @FormUrlEncoded
    @POST("api/scores_captcha")
    Observable<ScoreCaptchaResponse> scoresCaptcha(@Field("dabh") String code);

    /**
     * 2.2 获取驾照扣分
     */
    @FormUrlEncoded
    @POST("api/scores")
    Observable<ScoreResponse> apiScores(@Field("jszh")String s,
                                        @Field("dabh")String s1,
                                        @Field("captche")String s2,
                                        @Field("cookie")String cookie);
}
