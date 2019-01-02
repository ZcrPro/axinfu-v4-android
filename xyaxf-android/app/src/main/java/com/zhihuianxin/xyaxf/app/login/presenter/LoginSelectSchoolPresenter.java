package com.zhihuianxin.xyaxf.app.login.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.zhihuianxin.xyaxf.app.login.contract.ILoginSelectSchoolContract;
import com.zhihuianxin.xyaxf.http.ApiFactory;
import com.zhihuianxin.xyaxf.http.AppConstant;
import com.zhihuianxin.xyaxf.http.BaseResponse;
import com.zhihuianxin.xyaxf.http.BaseSubscriber;
import com.zhihuianxin.xyaxf.http.NetUtils;
import com.zhihuianxin.xyaxf.http.RetrofitFactory;
import com.zhihuianxin.xyaxf.modle.base.service.LoginService;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;
import com.zhihuianxin.xyaxf.modle.base.thrift.resource.School;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vincent on 2016/10/31.
 */

public class LoginSelectSchoolPresenter implements ILoginSelectSchoolContract.ISelectSchoolPresenter{
    private Context mContext;
    private ILoginSelectSchoolContract.ISelectSchoolView mView;

    public static class UpdateSchoolResponse {
        public BaseResponse resp;
        public Customer customer ;
    }

    public static class GetSchoolsResponse {
        public BaseResponse resp;
        public List<School> schools ;
    }

    public LoginSelectSchoolPresenter(Context context, ILoginSelectSchoolContract.ISelectSchoolView view){
        mContext = context;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadSchool(final String cityCode) {
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        Map<String,Object> map = new HashMap<>();
        map.put("city_code",cityCode);
        LoginService loginService = ApiFactory.getFactory().create(LoginService.class);
        loginService.getLoginSchools(NetUtils.getRequestParams(mContext,map),NetUtils.getSign(NetUtils.getRequestParams(mContext,map)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(mContext,false,mView) {
                    @Override
                    public void onNext(Object o) {
                        GetSchoolsResponse baseResponse = new Gson().fromJson(o.toString(),GetSchoolsResponse.class);
                        mView.setSchoolData(baseResponse.schools);
                    }
                });
    }

    @Override
    public void updateSchool(String school_code) {
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        Map<String,Object> map = new HashMap<>();
        map.put("school_code",school_code);
        LoginService loginService = ApiFactory.getFactory().create(LoginService.class);
        loginService.updateSchool(NetUtils.getRequestParams(mContext,map),NetUtils.getSign(NetUtils.getRequestParams(mContext,map)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(mContext,false,mView) {
                    @Override
                    public void onNext(Object o) {
                        UpdateSchoolResponse baseResponse = new Gson().fromJson(o.toString(),UpdateSchoolResponse.class);
                        mView.updateSchoolSuccess(baseResponse.customer);
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.loadError(e.getMessage());
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
