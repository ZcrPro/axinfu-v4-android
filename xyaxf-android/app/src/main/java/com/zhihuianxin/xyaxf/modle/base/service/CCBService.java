package com.zhihuianxin.xyaxf.modle.base.service;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface CCBService {

    //获取加密参数
    @FormUrlEncoded
    @POST("CCBService/get_ccb_param/")
    Observable<String> get_ccb_param(@Field("json") String json, @Field("sign") String sign);
}
