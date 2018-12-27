package com.zhihuianxin.xyaxf.modle.base.service;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface WalletService {

    // 开通缴费账户
    @FormUrlEncoded
    @POST("WalletService/upload_credential_photo/")
    Observable<String> upload_credential_photo(@Field("json") String json, @Field("sign") String sign);

    // 开通缴费账户
    @FormUrlEncoded
    @POST("WalletService/get_wallet_index_info/")
    Observable<String> get_wallet_index_info(@Field("json") String json, @Field("sign") String sign);

    // 开通缴费账户
    @FormUrlEncoded
    @POST("WalletService/get_income_records/")
    Observable<String> get_income_records(@Field("json") String json, @Field("sign") String sign);

    // 开通缴费账户
    @FormUrlEncoded
    @POST("WalletService/transfer_in/")
    Observable<String> transfer_in(@Field("json") String json, @Field("sign") String sign);

    // 开通缴费账户
    @FormUrlEncoded
    @POST("WalletService/transfer_out/")
    Observable<String> transfer_out(@Field("json") String json, @Field("sign") String sign);

    // 开通缴费账户
    @FormUrlEncoded
    @POST("WalletService/send_security_code/")
    Observable<String> send_security_code(@Field("json") String json, @Field("sign") String sign);

    // 开通缴费账户
    @FormUrlEncoded
    @POST("WalletService/open_account/")
    Observable<String> open_account(@Field("json") String json, @Field("sign") String sign);

    // 开通缴费账户
    @FormUrlEncoded
    @POST("WalletService/get_trade_limit/")
    Observable<String> get_trade_limit(@Field("json") String json, @Field("sign") String sign);

    // 开通缴费账户
    @FormUrlEncoded
    @POST("WalletService/get_wallet_records/")
    Observable<String> get_wallet_records(@Field("json") String json, @Field("sign") String sign);

    // 开通缴费账户
    @FormUrlEncoded
    @POST("WalletService/get_product_info/")
    Observable<String> get_product_info(@Field("json") String json, @Field("sign") String sign);

    // 开通缴费账户
    @FormUrlEncoded
    @POST("WalletService/get_transfer_status/")
    Observable<String> get_transfer_status(@Field("json") String json, @Field("sign") String sign);

    // 开通缴费账户
    @FormUrlEncoded
    @POST("WalletService/can_open_wallet/")
    Observable<String> can_open_wallet(@Field("json") String json, @Field("sign") String sign);

    // 开通缴费账户
    @FormUrlEncoded
    @POST("WalletService/get_transfer_in_pre_data/")
    Observable<String> get_transfer_in_pre_data(@Field("json") String json, @Field("sign") String sign);
}
