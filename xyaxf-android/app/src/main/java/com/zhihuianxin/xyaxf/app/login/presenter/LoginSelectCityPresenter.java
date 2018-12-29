package com.zhihuianxin.xyaxf.app.login.presenter;

import android.content.Context;


import com.google.gson.Gson;
import com.zhihuianxin.xyaxf.app.login.contract.ILoginSelectCityContract;
import com.zhihuianxin.xyaxf.http.ApiFactory;
import com.zhihuianxin.xyaxf.http.AppConstant;
import com.zhihuianxin.xyaxf.http.BaseResponse;
import com.zhihuianxin.xyaxf.http.BaseSubscriber;
import com.zhihuianxin.xyaxf.http.NetUtils;
import com.zhihuianxin.xyaxf.http.RetrofitFactory;
import com.zhihuianxin.xyaxf.modle.base.service.LoginService;
import com.zhihuianxin.xyaxf.modle.base.thrift.resource.City;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vincent on 2016/10/17.
 */

public class LoginSelectCityPresenter implements ILoginSelectCityContract.ISelectCityPresenter {

    private Context mContext;
    private ILoginSelectCityContract.ISelectCityView mView;

    public static class GetCitiesResponse {
        public BaseResponse resp;
        public List<City> cities ;
    }

    public LoginSelectCityPresenter(Context context, ILoginSelectCityContract.ISelectCityView view){
        mContext = context;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadCity() {
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        Map<String,Object> map = new HashMap<>();
        LoginService loginService = ApiFactory.getFactory().create(LoginService.class);
        loginService.getLoginCities(NetUtils.getRequestParams(mContext,map),NetUtils.getSign(NetUtils.getRequestParams(mContext,map)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(mContext,false,mView){
                    @Override
                    public void onNext(Object o) {
                        GetCitiesResponse citiesResponse = new Gson().fromJson(o.toString(),GetCitiesResponse.class);
                        mView.setCityData(citiesResponse.cities);
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
