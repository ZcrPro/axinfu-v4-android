package com.zhihuianxin.xyaxf.app.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.axinfu.basetools.base.BaseActionBarActivity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhihuianxin.xyaxf.App;
import com.zhihuianxin.xyaxf.R;
import com.zhihuianxin.xyaxf.http.ApiFactory;
import com.zhihuianxin.xyaxf.http.AppConstant;
import com.zhihuianxin.xyaxf.http.BaseResponse;
import com.zhihuianxin.xyaxf.http.BaseSubscriber;
import com.zhihuianxin.xyaxf.http.NetUtils;
import com.zhihuianxin.xyaxf.http.RetrofitFactory;
import com.zhihuianxin.xyaxf.modle.base.service.CustomerService;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.VerifyField;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginVerMobileActivity extends BaseActionBarActivity {

    @InjectView(R.id.ed_mobile)
    EditText edMobile;
    @InjectView(R.id.next)
    Button next;

    public static final String EXTRA_VERIFY_DATA = "pwd_ver_firld";
    public static final String EXTRA_FROM_UNLOGIN = "from_unlogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        edMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null) {
                    next.setEnabled(true);
                } else {
                    next.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取
                getmodifyPwdInfo(edMobile.getText().toString().trim());
            }
        });
    }


    public void getmodifyPwdInfo(final String mobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        CustomerService loginService = ApiFactory.getFactory().create(CustomerService.class);
        loginService.modifyPwdGetVerMsg(NetUtils.getRequestParams(this, map), NetUtils.getSign(NetUtils.getRequestParams(this, map)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(this, true, null) {
                    @Override
                    public void onNext(Object o) {
                        //将手机号存下来
                        try {
                            App.mAxLoginSp.setUserMobil(mobile);
                            GetPwdVerInfoResponse response = new Gson().fromJson(o.toString(), GetPwdVerInfoResponse.class);
                            if (response.verify_fields != null && response.verify_fields.size() > 0) {
                                Intent intent = new Intent(LoginVerMobileActivity.this, LoginGetPwdActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(EXTRA_VERIFY_DATA, response.verify_fields);
                                bundle.putString(LoginGetPwdActivity.EXTRA_FROM_UNLOGIN, "from_unlogin");
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                startActivity(new Intent(LoginVerMobileActivity.this, LoginGetPwdByCodeActivity.class));
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.login_ver_mobile_activity;
    }

    public static class GetPwdVerInfoResponse {
        public BaseResponse resp;
        public ArrayList<VerifyField> verify_fields;
    }

    @Override
    public void onLeftButtonClick(View view) {
        super.onLeftButtonClick(view);
        finish();
    }

    @Override
    public boolean leftButtonEnabled() {
        return true;
    }

    @Override
    public int getLeftButtonImageId() {
        return R.mipmap.back;
    }
}
