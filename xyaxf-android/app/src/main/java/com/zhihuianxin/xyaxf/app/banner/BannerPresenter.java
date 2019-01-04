package com.zhihuianxin.xyaxf.app.banner;

import android.content.Context;
import android.util.Log;


import com.google.gson.Gson;
import com.zhihuianxin.axutil.BaseSchedulerProvider;
import com.zhihuianxin.xyaxf.http.ApiFactory;
import com.zhihuianxin.xyaxf.http.AppConstant;
import com.zhihuianxin.xyaxf.http.BaseResponse;
import com.zhihuianxin.xyaxf.http.BaseSubscriber;
import com.zhihuianxin.xyaxf.http.NetUtils;
import com.zhihuianxin.xyaxf.http.RetrofitFactory;
import com.zhihuianxin.xyaxf.modle.base.service.MessageService;
import com.zhihuianxin.xyaxf.modle.base.thrift.message.Advertise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vincent on 2016/10/11.
 */

public class BannerPresenter implements IBannerContract.IBannerPresenter {

    private IBannerContract.IBannerView mView;
    private Context mContext;

    public static class BannerResponse {
        public BaseResponse resp;
        public List<Advertise> advertises;
    }

    public BannerPresenter(IBannerContract.IBannerView mView, Context mContext, BaseSchedulerProvider mSchedulerProvider) {
        this.mView = mView;
        this.mContext = mContext;
        mView.setPresenter(this);
    }

    @Override
    public void loadBannerData() {
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        Map<String, Object> map = new HashMap<>();
        map.put("advertise_position","Banner");
        MessageService messageService = ApiFactory.getFactory().create(MessageService.class);
        Log.d("BannerPresenter", NetUtils.getRequestParams(mContext, map));
        messageService.getAdvertises(NetUtils.getRequestParams(mContext, map), NetUtils.getSign(NetUtils.getRequestParams(mContext, map)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(mContext,false,mView) {
                    @Override
                    public void onNext(Object o) {
                        BannerResponse bannnerResponse = new Gson().fromJson(o.toString(), BannerResponse.class);
                        mView.bannerSuccess(bannnerResponse.advertises);
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
    }
}
