package com.zhihuianxin.xyaxf.app.home.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.zhihuianxin.xyaxf.app.home.contract.HomeContract;
import com.zhihuianxin.xyaxf.http.ApiFactory;
import com.zhihuianxin.xyaxf.http.AppConstant;
import com.zhihuianxin.xyaxf.http.BaseResponse;
import com.zhihuianxin.xyaxf.http.BaseSubscriber;
import com.zhihuianxin.xyaxf.http.NetUtils;
import com.zhihuianxin.xyaxf.http.RetrofitFactory;
import com.zhihuianxin.xyaxf.modle.base.service.CustomerService;
import com.zhihuianxin.xyaxf.modle.base.service.EcardService;
import com.zhihuianxin.xyaxf.modle.base.service.MessageService;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;
import com.zhihuianxin.xyaxf.modle.base.thrift.ecard.ECard;
import com.zhihuianxin.xyaxf.modle.base.thrift.message.Advertise;
import com.zhihuianxin.xyaxf.modle.base.thrift.payment.PayLimit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by zcrpro on 2016/11/4.
 */
public class HomePresenter implements HomeContract.HomePresenter {

    private HomeContract.HomeView mView;
    private Context mContext;
    private CompositeSubscription mSubscriptions;

    public static class EcardResponse  {
        public BaseResponse resp;
        public ECard ecard;
    }

    public static class BankResponse  {
        public BaseResponse resp;
        public PayLimit limit;
    }

    public static class CustomerResponse  {
        public BaseResponse resp;
        public Customer customer;
    }

    public static class BannerResponse {
        public BaseResponse resp;
        public List<Advertise> advertises;
    }

    public HomePresenter(HomeContract.HomeView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void loadBannerData() {
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        Map<String, Object> map = new HashMap<>();
        map.put("advertise_position","Banner");
        MessageService messageService = ApiFactory.getFactory().create(MessageService.class);
        messageService.getAdvertises(NetUtils.getRequestParams(mContext, map), NetUtils.getSign(NetUtils.getRequestParams(mContext, map)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(mContext,false,mView) {
                    @Override
                    public void onNext(Object o) {
                        BannerResponse bannnerResponse = new Gson().fromJson(o.toString(),BannerResponse.class);
                        mView.bannerSuccess(bannnerResponse.advertises);
                    }
                });

    }

    @Override
    public void loadCustomerData() {
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        Map<String, Object> map = new HashMap<>();
        CustomerService customerService = ApiFactory.getFactory().create(CustomerService.class);
        customerService.getCustomer(NetUtils.getRequestParams(mContext, map), NetUtils.getSign(NetUtils.getRequestParams(mContext, map)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(mContext,false,mView) {
                    @Override
                    public void onNext(Object o) {
                        CustomerResponse customerResponse = new Gson().fromJson(o.toString(), CustomerResponse.class);
                        mView.customerSuccess(customerResponse.customer);
                        Log.d("fee_account", "onNext: "+new Gson().toJson(customerResponse.customer.fee_account));
                    }
                });
    }

    @Override
    public void getEcardData() {
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        Map<String, Object> map = new HashMap<>();
        EcardService ecardService = ApiFactory.getFactory().create(EcardService.class);
        ecardService.getEcard(NetUtils.getRequestParams(mContext, map), NetUtils.getSign(NetUtils.getRequestParams(mContext, map)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(mContext, false, mView) {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {}
                    @Override
                    public void onNext(Object o) {
                        EcardResponse ecardResponse = new Gson().fromJson(o.toString(), EcardResponse.class);
                        mView.getEcardDataSuccess(ecardResponse);
                    }
                });
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }
}
