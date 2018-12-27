package com.zhihuianxin.xyaxf.modle.base.service;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface CashierService {

    //获取Customer
    @FormUrlEncoded
    @POST("CashierService/get_pay_methods/")
    Observable<String> get_pay_methods(@Field("json") String json, @Field("sign") String sign);
}
