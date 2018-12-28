package com.zhihuianxin.xyaxf.app.login.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.zhihuianxin.xyaxf.App;
import com.zhihuianxin.xyaxf.BuildConfig;
import com.zhihuianxin.xyaxf.app.login.contract.ILoginSetPwdOrRegistContract;
import com.zhihuianxin.xyaxf.http.ApiFactory;
import com.zhihuianxin.xyaxf.http.AppConstant;
import com.zhihuianxin.xyaxf.http.BaseResponse;
import com.zhihuianxin.xyaxf.http.BaseSubscriber;
import com.zhihuianxin.xyaxf.http.NetUtils;
import com.zhihuianxin.xyaxf.http.RetrofitFactory;
import com.zhihuianxin.xyaxf.modle.base.service.LoginService;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.MobileStatus;
import com.zhihuianxin.xyaxf.modle.base.thrift.secure_code.VerifyFor;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by Vincent on 2016/10/17.
 */

public class LoginSetPwdOrRegistPresenter implements ILoginSetPwdOrRegistContract.ILoginSetPwdOrRegistPresenter {
    private Context mContext;
    private ILoginSetPwdOrRegistContract.ILoginSetPwdOrRegistView mView;
    private CompositeSubscription mSubscriptions;

    public static class LoginAndSetPasswordResponse {
        public BaseResponse resp;
        public Customer customer;
        public String session;
    }

    public class GetVerCodeResponse {
        public BaseResponse resp;
        public String code;
    }

    public LoginSetPwdOrRegistPresenter(Context context, ILoginSetPwdOrRegistContract.ILoginSetPwdOrRegistView view) {
        mContext = context;
        mView = view;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void getVerCode(String mobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("verify_for", VerifyFor.Register.name());
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        LoginService loginService = ApiFactory.getFactory().create(LoginService.class);
        Subscription subscription =
                loginService.getVerCode(NetUtils.getRequestParams(mContext, map), NetUtils.getSign(NetUtils.getRequestParams(mContext, map)))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<Object>(mContext, false, mView) {
                            @Override
                            public void onNext(Object o) {
                                if (BuildConfig.BUILD_TYPE.equals("debug")) {
                                    if (BuildConfig.AnXinDEBUG) {
                                        GetVerCodeResponse vercode = new Gson().fromJson(o.toString(), GetVerCodeResponse.class);
                                        mView.getVerCodeSuccess(vercode.code);
                                    }
                                }
                            }
                        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void setPwdOrRegistAndLogin(String mobile, String verCode, String pwd, String machineId) {
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("secureity_code", verCode);
        map.put("attribute_code", machineId);
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        LoginService loginService = ApiFactory.getFactory().create(LoginService.class);
        Observable<String> objectObservable;
        map.put("password", pwd);
        objectObservable = loginService.regist(NetUtils.getRequestParams(mContext, map), NetUtils.getSign(NetUtils.getRequestParams(mContext, map)));
        Subscription subscription = objectObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(mContext, true, mView) {
                    @Override
                    public void onNext(Object o) {
                        LoginAndSetPasswordResponse response = new Gson().fromJson(o.toString(), LoginAndSetPasswordResponse.class);
                        mView.setPwdOrRegistAndLoginSuccess(response.customer, response.session);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
