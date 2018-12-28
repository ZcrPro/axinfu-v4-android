package com.zhihuianxin.xyaxf.app.login.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.zhihuianxin.xyaxf.BuildConfig;
import com.zhihuianxin.xyaxf.app.login.contract.ILoginHasPwdContract;
import com.zhihuianxin.xyaxf.http.ApiFactory;
import com.zhihuianxin.xyaxf.http.AppConstant;
import com.zhihuianxin.xyaxf.http.BaseResponse;
import com.zhihuianxin.xyaxf.http.BaseSubscriber;
import com.zhihuianxin.xyaxf.http.NetUtils;
import com.zhihuianxin.xyaxf.http.RetrofitFactory;
import com.zhihuianxin.xyaxf.modle.base.service.CustomerService;
import com.zhihuianxin.xyaxf.modle.base.service.LoginService;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.MobileStatus;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.VerifyField;
import com.zhihuianxin.xyaxf.modle.base.thrift.secure_code.VerifyFor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Vincent on 2016/10/18.
 */

public class LoginHasPwdPresenter implements ILoginHasPwdContract.ILoginHasPwdPresenter {
    private ILoginHasPwdContract.ILoginHasPwdView mView;
    private Context mContext;
    private CompositeSubscription mSubscriptions;

    public static class GetPwdVerInfoResponse {
        public BaseResponse resp;
        public ArrayList<VerifyField> verify_fields;
    }

    public static class HasLoginStatusResponse {
        public BaseResponse resp;
        public MobileStatus status;
    }

    public LoginHasPwdPresenter(Context context, ILoginHasPwdContract.ILoginHasPwdView view) {
        mContext = context;
        mView = view;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void login(String mobile, String pwd, String uuid) {
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("password", pwd);
        map.put("attribute_code", uuid);
        LoginService loginService = ApiFactory.getFactory().create(LoginService.class);
        loginService.login(NetUtils.getRequestParams(mContext, map), NetUtils.getSign(NetUtils.getRequestParams(mContext, map)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(mContext, true, mView) {
                    @Override
                    public void onNext(Object o) {
                        LoginVerPwdPresenter.LoginResponse response = new Gson().fromJson(o.toString(), LoginVerPwdPresenter.LoginResponse.class);
                        mView.loginSuccess(response.customer, response.session);
                    }
                });
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
                                    if (BuildConfig.AnXinDEBUG) {
                                        LoginSetPwdOrRegistPresenter.GetVerCodeResponse vercode = new Gson().fromJson(o.toString(), LoginSetPwdOrRegistPresenter.GetVerCodeResponse.class);
                                        mView.getVerCodeSuccess(vercode.code);
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
                        LoginSetPwdOrRegistPresenter.LoginAndSetPasswordResponse response = new Gson().fromJson(o.toString(), LoginSetPwdOrRegistPresenter.LoginAndSetPasswordResponse.class);
                        mView.setPwdOrRegistAndLoginSuccess(response.customer, response.session);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getmodifyPwdInfo(String mobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        CustomerService loginService = ApiFactory.getFactory().create(CustomerService.class);
        Subscription subscription =
                loginService.modifyPwdGetVerMsg(NetUtils.getRequestParams(mContext, map), NetUtils.getSign(NetUtils.getRequestParams(mContext, map)))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<Object>(mContext, false, mView) {
                            @Override
                            public void onNext(Object o) {
                                GetPwdVerInfoResponse response = new Gson().fromJson(o.toString(), GetPwdVerInfoResponse.class);
                                mView.getmodifyPwdInfoResult(response.verify_fields);
                            }
                        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }
}
