package com.zantong.mobile.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * apk文件下载
 */
public interface FileDownloadApi {
    /**
     * 大文件读取
     */
    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithFixedUrl(@Url String url);
}
