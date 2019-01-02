package com.zhihuianxin.xyaxf.app.login;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.axinfu.basetools.base.BaseActionBarActivity;
import com.google.gson.Gson;
import com.zhihuianxin.axutil.Util;
import com.zhihuianxin.xyaxf.App;
import com.zhihuianxin.xyaxf.BuildConfig;
import com.zhihuianxin.xyaxf.R;
import com.zhihuianxin.xyaxf.app.login.contract.ILoginGetPwdContract;
import com.zhihuianxin.xyaxf.app.login.presenter.LoginGetPwdPresenter;
import com.zhihuianxin.xyaxf.app.login.presenter.LoginVerPwdPresenter;
import com.zhihuianxin.xyaxf.app.main.MainActivity;
import com.zhihuianxin.xyaxf.http.ApiFactory;
import com.zhihuianxin.xyaxf.http.AppConstant;
import com.zhihuianxin.xyaxf.http.BaseSubscriber;
import com.zhihuianxin.xyaxf.http.NetUtils;
import com.zhihuianxin.xyaxf.modle.base.service.LoginService;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;
import com.zhihuianxin.xyaxf.modle.base.thrift.secure_code.VerifyFor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vincent on 2017/12/20.
 */

public class LoginGetPwdByCodeActivity extends BaseActionBarActivity implements ILoginGetPwdContract.ILoginGetPwdView {
    public static final int DEFAULT_COUNT = AppConstant.DEFAULT_COUNT;
    private static final HashMap<String, Long> sStartTicks = new HashMap<String, Long>();
    @InjectView(R.id.editText_layout)
    RelativeLayout editTextLayout;
    @InjectView(R.id.pwdlook)
    RelativeLayout pwdlook;
    @InjectView(R.id.next)
    Button next;
    @InjectView(R.id.share_title)
    TextView shareTitle;

    private int mCountDownDuration = DEFAULT_COUNT;

    @InjectView(R.id.editText_getpwd)
    EditText mMobileEdit;
    @InjectView(R.id.edit_ver)
    EditText mVerEdit;
    @InjectView(R.id.editText_pwd)
    EditText mPwdEdit;
    @InjectView(R.id.getVerCode)
    TextView mVerText;
    @InjectView(R.id.pwdlookok)
    ImageView mPwdLookOkImg;
    @InjectView(R.id.pwdlookun)
    ImageView mPwdLookunImg;
    @InjectView(R.id.backAnimView)
    View mBackAlertView;
    @InjectView(R.id.grayBg)
    View mGrayBg;
    @InjectView(R.id.click_focus)
    Button closeAniBtn;
    @InjectView(R.id.tel_desc)
    TextView errorTxt;

    private VerController verController;
    private DisplayMetrics metrics;
    private ILoginGetPwdContract.ILoingGetPwdPresenter mPresenter;

    private boolean ver_status;
    private boolean password_status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        new LoginGetPwdPresenter(this, this);
        verController = new VerController();
        //mMobileEdit.addTextChangedListener(watcherMobile);
        mVerEdit.addTextChangedListener(watcherVer);
        //mMobileEdit.requestFocus();
        if (!TextUtils.isEmpty(App.mAxLoginSp.getUserMobil())) {
            mMobileEdit.setText(App.mAxLoginSp.getUserMobil().substring(0, 3) + "****" + App.mAxLoginSp.getUserMobil().substring(7));
            mMobileEdit.setEnabled(false);
        }
        verController.reset();

        mVerEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ver_status = !TextUtils.isEmpty(s.toString().trim());
                if (ver_status && password_status) {
                    next.setEnabled(true);
                }else {
                    next.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPwdEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password_status = !TextUtils.isEmpty(s.toString().trim());
                if (ver_status && password_status) {
                    next.setEnabled(true);
                }else {
                    next.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.login_getpwd_bycode;
    }

    @Override
    public boolean leftButtonEnabled() {
        return true;
    }

    @Override
    public int getLeftButtonImageId() {
        return R.mipmap.back;
    }

    @Override
    public void onLeftButtonClick(View view) {
        finish();
    }

    TextWatcher watcherVer = new TextWatcher() {
        private String lastInput;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            if (text.equals(lastInput)) {
                return;
            }

            if (text.length() == 4) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//Context.INPUT_METHOD_SERVICE
                imm.hideSoftInputFromWindow(mVerEdit.getWindowToken(), 0);
                mVerEdit.clearFocus();
            }

            lastInput = text;
        }
    };

    private void showAlertAnim() {
        int halfScreen = (metrics.heightPixels / 2) + 200;
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mBackAlertView, "translationY", 0,
                halfScreen, halfScreen + 50, halfScreen + 10, halfScreen + 35, halfScreen + 30);//450 410 435 430
        animator2.setDuration(700);
        animator2.start();
        mGrayBg.setVisibility(View.VISIBLE);

        closeAniBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBackAlertAnim();
            }
        });
    }

    private void hideBackAlertAnim() {
        int halfScreen = (metrics.heightPixels / 2) + 200;
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mBackAlertView, "translationY", halfScreen + 30, 0);
        animator2.setDuration(600);
        animator2.start();
        mGrayBg.setVisibility(View.GONE);
    }

    private boolean isHidden = false;

    @OnClick(R.id.pwdlook)
    public void onBtnPwdLook() {
        if (isHidden) {
            //设置EditText文本为可见的
            mPwdEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mPwdLookOkImg.setVisibility(View.VISIBLE);
            mPwdLookunImg.setVisibility(View.GONE);
        } else {
            //设置EditText文本为隐藏的
            mPwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mPwdLookOkImg.setVisibility(View.GONE);
            mPwdLookunImg.setVisibility(View.VISIBLE);
        }
        isHidden = !isHidden;
        mPwdEdit.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = mPwdEdit.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    @OnClick(R.id.next)
    public void onBtnGetNewPwd() {
        String mobile;
        if (TextUtils.isEmpty(App.mAxLoginSp.getUserMobil())) {
            mobile = mMobileEdit.getText().toString().trim();
        } else {
            mobile = App.mAxLoginSp.getUserMobil();
        }
        String pwd = mPwdEdit.getText().toString().trim();
        String verCode = mVerEdit.getText().toString().trim();
        Pattern p = Pattern.compile(BuildConfig.AnXinDEBUG ? Util.REGEX_MOBILE_DEBUG : Util.REGEX_MOBILE);
        if (!p.matcher(mobile).matches()) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
            return;
        }
        if (Util.isEmpty(pwd) || pwd.length() < 6) {
            Toast.makeText(this, "请输入正确格式的密码", Toast.LENGTH_LONG).show();
            return;
        }
        if (Util.isEmpty(verCode) || verCode.length() != 4) {
            Toast.makeText(this, "请输入正确格式的短信验证码", Toast.LENGTH_LONG).show();
            return;
        }
        mPresenter.getPwd(mobile, verCode, pwd, App.mAxLoginSp.getUUID().replace("-", ""));

    }

    @OnClick(R.id.getVerCode)
    public void onBtnGetVer() {
        if (Util.isEmpty(mMobileEdit.getText().toString().trim())) {
            Toast.makeText(this, "请输入手机号!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(App.mAxLoginSp.getUserMobil())) {
            mPresenter.getVerCode(VerifyFor.ResetPassword.name(), mMobileEdit.getText().toString().trim());
        } else {
            mPresenter.getVerCode(VerifyFor.ResetPassword.name(), App.mAxLoginSp.getUserMobil());
        }
        LoginSendVerTask task = new LoginSendVerTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public class LoginSendVerTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            Long startMilli = sStartTicks.get(verController.getCountDownTag());
            if (startMilli == null || System.currentTimeMillis() - startMilli > mCountDownDuration) {
                startMilli = System.currentTimeMillis();
                sStartTicks.put(verController.getCountDownTag(), startMilli);
            }

            while (true) {
                long currentMilli = System.currentTimeMillis();

                long duration = currentMilli - startMilli;
                long left = mCountDownDuration - duration;
                if (left <= 0) {
                    publishProgress(0l);

                    break;
                }
                publishProgress(left);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            verController.verStart();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            verController.reset();
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);

            if (values.length > 0 && mVerText != null) {
                long left = (Long) values[0];
                mVerText.setText(Math.round((float) left / 1000f) + "s后重新获取");
            }
        }
    }

    public class VerController {
        private String mNormalText;
        private String mCountDownTag = getClass().getName();

        public VerController() {
            mCountDownTag = getClass().getName();
            mNormalText = mVerText.getText().toString();
        }

        void verStart() {
            mVerText.setTextColor(getResources().getColor(R.color.axf_light_gray));
            setState(false);
        }

        void reset() {
            setState(true);
            mVerText.setText(mNormalText);
            mVerText.setTextColor(getResources().getColor(R.color.axf_common_blue));
            sStartTicks.remove(mCountDownTag);
        }

        public void setState(boolean enabled) {
            mVerText.setEnabled(enabled);
        }

        public String getCountDownTag() {
            return mCountDownTag;
        }
    }


    @Override
    public void getVerCodeSuccess(String code) {
        Toast.makeText(this, code, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getPwdSuccess(final Customer customer, String session) {
        if (customer == null) {
            showAlertAnim();
            errorTxt.setText(session);
        } else {
            LoginChangeDataForApp.updateDataForApp(customer, session);
            Toast.makeText(LoginGetPwdByCodeActivity.this, "修改密码成功", Toast.LENGTH_LONG).show();
            if (getIntent().getExtras() != null && getIntent().getExtras().getString(LoginGetPwdActivity.EXTRA_FROM_UNLOGIN) != null) {
                if (TextUtils.isEmpty(App.mAxLoginSp.getUserMobil())) {
                    login(mMobileEdit.getText().toString().trim(), mPwdEdit.getText().toString().trim(), App.mAxLoginSp.getUUID().replace("-", ""));
                } else {
                    login(App.mAxLoginSp.getUserMobil(), mPwdEdit.getText().toString().trim(), App.mAxLoginSp.getUUID().replace("-", ""));
                }
            } else {
                finish();
            }
        }
    }

    @Override
    public void setPresenter(ILoginGetPwdContract.ILoingGetPwdPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadStart() {

    }

    @Override
    public void loadError(String errorMsg) {

    }

    @Override
    public void loadComplete() {

    }

    public void login(String mobile, String pwd, String uuid) {
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("password", pwd);
        map.put("attribute_code", uuid);
        LoginService loginService = ApiFactory.getFactory().create(LoginService.class);
        Subscription subscription =
                loginService.login(NetUtils.getRequestParams(this, map), NetUtils.getSign(NetUtils.getRequestParams(this, map)))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<Object>(this, true, this) {
                            @Override
                            public void onNext(Object o) {
                                final LoginVerPwdPresenter.LoginResponse response = new Gson().fromJson(o.toString(), LoginVerPwdPresenter.LoginResponse.class);
                                LoginChangeDataForApp.updateDataForApp(response);
                                if (response.customer.school != null) {
                                    startActivity(new Intent(LoginGetPwdByCodeActivity.this, MainActivity.class));
                                } else {
                                    startActivity(new Intent(LoginGetPwdByCodeActivity.this, LoginSelectCityActivity.class));
                                }
                                finish();
                            }
                        });
    }
}
