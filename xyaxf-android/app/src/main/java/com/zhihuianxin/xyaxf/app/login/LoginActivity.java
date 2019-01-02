package com.zhihuianxin.xyaxf.app.login;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhihuianxin.xyaxf.App;
import com.zhihuianxin.xyaxf.R;
import com.zhihuianxin.xyaxf.app.login.contract.ILoginHasPwdContract;
import com.zhihuianxin.xyaxf.app.login.presenter.LoginHasPwdPresenter;
import com.zhihuianxin.xyaxf.app.login.presenter.LoginVerPwdPresenter;
import com.zhihuianxin.xyaxf.app.main.MainActivity;
import com.zhihuianxin.xyaxf.app.main.resp.CustomerResponse;
import com.zhihuianxin.xyaxf.cooperate.CooperateAppId;
import com.zhihuianxin.xyaxf.cooperate.wuhan.WuhanDataBean;
import com.zhihuianxin.xyaxf.http.ApiFactory;
import com.zhihuianxin.xyaxf.http.AppConstant;
import com.zhihuianxin.xyaxf.http.BaseResponse;
import com.zhihuianxin.xyaxf.http.BaseSubscriber;
import com.zhihuianxin.xyaxf.http.NetUtils;
import com.zhihuianxin.xyaxf.http.RetrofitFactory;
import com.zhihuianxin.xyaxf.modle.base.service.CustomerService;
import com.zhihuianxin.xyaxf.modle.base.service.LoginService;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.VerifyField;
import com.zhihuianxin.xyaxf.util.AxToast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.zhihuianxin.axutil.Util.Decrypt;
import static com.zhihuianxin.axutil.Util.md5;

public class LoginActivity extends Activity implements ILoginHasPwdContract.ILoginHasPwdView {

    //todo 每次登陆都刷新push_id 避免顶号的推送下发到老账号上面

    @InjectView(R.id.input_back)
    ImageView inputBack;
    @InjectView(R.id.back_icon)
    RelativeLayout backIcon;
    @InjectView(R.id.top_title)
    TextView topTitle;
    @InjectView(R.id.iv_view)
    ImageView ivView;
    @InjectView(R.id.regi_mobile)
    EditText regiMobile;
    @InjectView(R.id.regi_ver_code)
    EditText regiVerCode;
    @InjectView(R.id.getver)
    TextView getver;
    @InjectView(R.id.editText_layout)
    RelativeLayout editTextLayout;
    @InjectView(R.id.input_pwd_view)
    LinearLayout inputPwdView;
    @InjectView(R.id.setPwdCoverView)
    View setPwdCoverView;
    @InjectView(R.id.tips)
    TextView tips;
    @InjectView(R.id.pwdlookok)
    ImageView pwdlookok;
    @InjectView(R.id.pwdlookun)
    ImageView pwdlookun;
    @InjectView(R.id.pwdlook)
    RelativeLayout pwdlook;
    @InjectView(R.id.regi_password)
    EditText regiPassword;
    @InjectView(R.id.setpass)
    RelativeLayout setpass;
    @InjectView(R.id.progressBar_changepassword)
    ProgressBar progressBarChangepassword;
    @InjectView(R.id.tv_qiangdu)
    TextView tvQiangdu;
    @InjectView(R.id.setPwdAllView)
    RelativeLayout setPwdAllView;
    @InjectView(R.id.pwdlookok_ver)
    ImageView mPwdLookOkImg;
    @InjectView(R.id.pwdlookun_ver)
    ImageView mPwdLookunImg;
    @InjectView(R.id.pwdlookok)
    ImageView mPwdLookOkImgSet;
    @InjectView(R.id.pwdlookun)
    ImageView mPwdLookunImgSet;
    @InjectView(R.id.verpwd_view)
    RelativeLayout verpwdView;
    @InjectView(R.id.login_ed_mobile)
    EditText loginEdMobile;
    @InjectView(R.id.login_ed_password)
    EditText loginEdPassword;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.login_forgetpwd)
    TextView loginForgetpwd;
    @InjectView(R.id.login_new_user)
    TextView loginNewUser;
    @InjectView(R.id.bottom_view)
    LinearLayout bottomView;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.tv_version)
    TextView tvVersion;
    @InjectView(R.id.check_update_next)
    TextView checkUpdateNext;
    @InjectView(R.id.ll)
    LinearLayout ll;
    private DisplayMetrics metrics;

    private long exitTime = 0;

    private boolean login_mobile_status;
    private boolean login_password_status;

    private boolean regi_mobile_status;
    private boolean regi_sms_code_status;
    private boolean regi_password_status;

    @Override
    public void loginSuccess(Customer customer, String session) {
        LoginChangeDataForApp.updateDataForApp(customer, session);
        if (customer.school != null) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            goLoginSelectCityActivity();
        }
        finish();
    }

    @Override
    public void setPwdOrRegistAndLoginSuccess(Customer customer, String session) {

    }

    @Override
    public void getVerCodeSuccess(String verCode) {
        if (verCode != null)
            Toast.makeText(this, verCode, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getmodifyPwdInfoResult(ArrayList<VerifyField> verify_fields) {

    }

    @Override
    public void setPresenter(ILoginHasPwdContract.ILoginHasPwdPresenter presenter) {
        this.presenter = presenter;
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

    //保存页面状态
    enum STATUE {
        INPUT(), SETPWD()
    }

    private STATUE mCurrStatus = STATUE.INPUT;

    private boolean isHiddenVer = true;

    private boolean isSetPwdHiddenVer = true;

    private String targeMobile; //处理app内部传来的手机号 帮用户预填手机号

    private VerController verController;
    private HashMap<String, Long> sStartTicks = new HashMap<String, Long>();

    public static final int DEFAULT_COUNT = AppConstant.DEFAULT_COUNT;
    private int mCountDownDuration = DEFAULT_COUNT;

    private ILoginHasPwdContract.ILoginHasPwdPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.inject(this);
        new LoginHasPwdPresenter(this, this);
        initUI();
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        verController = new VerController();

        //处理app内部传来的手机号做预填手机
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bundle.getString("mobile");
            if (bundle.getString("mobile") != null) {
                loginEdMobile.setText(bundle.getString("mobile"));
                targeMobile = bundle.getString("mobile");
            }
        }
    }

    private void initUI() {

        regiMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    regi_mobile_status = true;
                    getver.setTextColor(getResources().getColor(R.color.axf_common_blue));
                } else {
                    regi_mobile_status = false;
                    getver.setTextColor(getResources().getColor(R.color.gray_light));
                }

                if (regi_mobile_status && regi_sms_code_status && regi_password_status) {
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        regiVerCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    regi_sms_code_status = true;
                } else {
                    regi_sms_code_status = false;
                }

                if (regi_mobile_status && regi_sms_code_status && regi_password_status) {
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        regiPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    regi_password_status = true;
                } else {
                    regi_password_status = false;
                }

                if (regi_mobile_status && regi_sms_code_status && regi_password_status) {
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginEdMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    login_mobile_status = true;
                } else {
                    login_mobile_status = false;
                }

                if (login_mobile_status && login_password_status) {
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginEdPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    login_password_status = true;
                } else {
                    login_password_status = false;
                }

                if (login_mobile_status && login_password_status) {
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.login_new_user)
    public void onNewUser() {
        //新用户注册去 清除输入的信息
        regiMobile.setText("");
        showSetPwdView();
    }

    private void showSetPwdView() {
        backIcon.setVisibility(View.VISIBLE);
        loginEdPassword.setVisibility(View.GONE);
        setpass.setVisibility(View.VISIBLE);
        tips.setVisibility(View.VISIBLE);
        loginNewUser.setVisibility(View.GONE);
        loginForgetpwd.setVisibility(View.GONE);
        mCurrStatus = STATUE.SETPWD;
        progressBarChangepassword.setVisibility(View.VISIBLE);
        tvQiangdu.setVisibility(View.VISIBLE);
        progressBarChangepassword.setProgress(0);
        btnLogin.setText("完成");
        topTitle.setText("注册");
        ivView.setImageResource(R.mipmap.regi_bg_logo);
        verpwdView.setVisibility(View.GONE);
        loginEdMobile.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(loginEdMobile, "alpha", 1f, 0f).setDuration(1000).start();
        ObjectAnimator animator = ObjectAnimator.ofFloat(setPwdCoverView, "alpha", 1f, 0f);
        animator.setDuration(1000);
        animator.setDuration(1000);
        animator.start();
        setPwdCoverView.setVisibility(View.GONE);
        ObjectAnimator animBottom = ObjectAnimator.ofFloat(bottomView, "translationY", 0f, metrics.density);
        animBottom.setDuration(1000);
        animBottom.setDuration(1000);
        animBottom.start();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && TextUtils.isEmpty(targeMobile)) {
            if (mCurrStatus.equals(STATUE.SETPWD)) {
                backToInputMobilResetVer();
                topTitle.setText("登录");
                loginEdMobile.setText("");
                showBackToInputAnim();
                return false;
            }
            return super.onKeyDown(keyCode, event);
        } else if (keyCode == KeyEvent.KEYCODE_BACK && !TextUtils.isEmpty(targeMobile)) {
            exit();
            return false;
        } else {
            return false;
        }
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    private void backToInputMobilResetVer() {
        try {
            Thread.currentThread().sleep(300);//毫秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        verController.reset();
    }

    private boolean stopVerCode = false;

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
                if (left <= 0 || stopVerCode) {
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

            if (values.length > 0 && getver != null) {
                long left = (Long) values[0];
                getver.setText(Math.round((float) left / 1000f) + "s后重新获取");
            }
        }
    }

    /**
     * 验证码处理
     */
    public class VerController {
        private String mNormalText;
        private String mCountDownTag = getClass().getName();

        public VerController() {
            mCountDownTag = getClass().getName();
            mNormalText = getver.getText().toString();
        }

        void verStart() {
            stopVerCode = false;
            getver.setTextColor(getResources().getColor(R.color.axf_light_gray));
            setState(false);
        }

        void reset() {
            stopVerCode = true;
            setState(true);
            getver.setText(mNormalText);
            getver.setTextColor(getResources().getColor(R.color.axf_common_blue));
            sStartTicks.remove(mCountDownTag);
        }

        public void setState(boolean enabled) {
            getver.setEnabled(enabled);
        }

        public String getCountDownTag() {
            return mCountDownTag;
        }
    }


    /**
     * 回到登录界面
     */
    private void showBackToInputAnim() {
        backIcon.setVisibility(View.GONE);
        loginEdPassword.setVisibility(View.VISIBLE);
        setpass.setVisibility(View.GONE);
        tips.setVisibility(View.GONE);
        tvQiangdu.setVisibility(View.GONE);
        loginNewUser.setVisibility(View.VISIBLE);
        loginForgetpwd.setVisibility(View.VISIBLE);
        progressBarChangepassword.setProgress(0);
        progressBarChangepassword.setVisibility(View.GONE);
        btnLogin.setText("登录");
        ivView.setImageResource(R.mipmap.login_bg_logo);
        regiMobile.setText("");
        regiPassword.setText("");
        regiVerCode.setText("");
        loginEdPassword.setText("");
        loginEdMobile.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(loginEdMobile, "alpha", 0f, 1f).setDuration(1000).start();
        setPwdCoverView.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(setPwdCoverView, "alpha", 0f, 1f);
        animator.setDuration(1000);
        animator.setDuration(1000);
        animator.start();

        ObjectAnimator animBottom = ObjectAnimator.ofFloat(bottomView, "translationY", metrics.density * 40, 0);
        animBottom.setDuration(1000);
        animBottom.setDuration(1000);
        animBottom.start();

        mCurrStatus = STATUE.INPUT;
    }


    @OnClick(R.id.pwdlook_ver)
    public void onBtnPwdLook() {
        if (isHiddenVer) {
            //设置EditText文本为可见的
            regiPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mPwdLookOkImg.setVisibility(View.VISIBLE);
            mPwdLookunImg.setVisibility(View.GONE);

        } else {
            //设置EditText文本为隐藏的
            regiPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mPwdLookOkImg.setVisibility(View.GONE);
            mPwdLookunImg.setVisibility(View.VISIBLE);
        }
        isHiddenVer = !isHiddenVer;
        regiPassword.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = regiPassword.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    @OnClick(R.id.pwdlook)
    public void onBtnPwdLookSet() {
        if (isSetPwdHiddenVer) {
            //设置EditText文本为可见的
            regiPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mPwdLookOkImgSet.setVisibility(View.VISIBLE);
            mPwdLookunImgSet.setVisibility(View.GONE);
        } else {
            //设置EditText文本为隐藏的
            regiPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mPwdLookOkImgSet.setVisibility(View.GONE);
            mPwdLookunImgSet.setVisibility(View.VISIBLE);
        }
        isSetPwdHiddenVer = !isSetPwdHiddenVer;
        regiPassword.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = regiPassword.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    @OnClick(R.id.getver)
    public void onBtnGetVer() {
        if (TextUtils.isEmpty(regiMobile.getText().toString().trim())) {
            Toast.makeText(this, "请填写手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isMobileNO(regiMobile.getText().toString().trim())) {
            Toast.makeText(this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.getVerCode(regiMobile.getText().toString().trim());
        LoginSendVerTask task = new LoginSendVerTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 在正式环境 也可能出现111222的手机账号进行测试 所以只判断位数
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        return mobiles.length() == 11;
    }

    @OnClick(R.id.btn_login)
    public void onBtnNext() {
        if (btnLogin.getText().equals("登录")) {
            if (TextUtils.isEmpty(loginEdPassword.getText().toString().trim()) || loginEdPassword.getText().toString().trim().length() < 6) {
                Toast.makeText(this, "密码必须大于或等于6位", Toast.LENGTH_SHORT).show();
            } else {
                //先判断是否是第三方的跳转
                Uri uri = getIntent().getData();
                if (uri != null) {
                    String appid = uri.getQueryParameter("appid");
                    String data = uri.getQueryParameter("data");
                    if (appid != null && appid.equals(CooperateAppId.AppId)) {
                        //如果id已经存在就说明第三方app的调用有效
                        //去解密
                        try {
                            assert data != null;
                            String sr = Decrypt(data, CooperateAppId.aesKey);
                            Log.d("LoginInputMobilActivity", "解密内容：" + sr);
                            WuhanDataBean dataBean = new Gson().fromJson(sr, WuhanDataBean.class);
                            get_student_status(true, dataBean.school_code, dataBean.account_no, dataBean.account_name);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "解析数据失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    presenter.login(loginEdMobile.getText().toString().trim(), loginEdPassword.getText().toString().trim(), App.mAxLoginSp.getUUID().replace("-", ""));
                }
            }

        } else {
            if (TextUtils.isEmpty(regiPassword.getText().toString().trim()) || regiPassword.getText().toString().trim().length() < 6) {
                Toast.makeText(this, "密码必须大于或等于6位", Toast.LENGTH_SHORT).show();
            } else {
                //先判断是否是第三方的跳转
                Uri uri = getIntent().getData();
                if (uri != null) {
                    String appid = uri.getQueryParameter("appid");
                    String data = uri.getQueryParameter("data");
                    if (appid != null && appid.equals(CooperateAppId.AppId)) {
                        //如果id已经存在就说明第三方app的调用有效
                        //去解密
                        try {
                            assert data != null;
                            String sr = Decrypt(data, CooperateAppId.aesKey);
                            WuhanDataBean dataBean = new Gson().fromJson(sr, WuhanDataBean.class);
                            get_student_status(false, dataBean.school_code, dataBean.account_no, dataBean.account_name);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "解析数据失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    presenter.setPwdOrRegistAndLogin(regiMobile.getText().toString().trim(), regiVerCode.getText().toString().trim(),
                            regiPassword.getText().toString().trim(), App.mAxLoginSp.getUUID().replace("-", ""));
                }
            }
        }
    }

    private void get_student_status(final boolean isLogin, final String school_code, final String student_no, final String student_name) {
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        Map<String, Object> map = new HashMap<>();
        map.put("school_code", school_code);
        map.put("student_no", student_no);
        CustomerService customerService = ApiFactory.getFactory().create(CustomerService.class);
        customerService.get_student_status(NetUtils.getRequestParams(this, map), NetUtils.getSign(NetUtils.getRequestParams(this, map)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(this, true, null) {
                    @Override
                    public void onNext(Object o) {
                        StudentResponse studentResponse = new Gson().fromJson(o.toString(), StudentResponse.class);
                        if (studentResponse.student_status.equals("Bound")) {
                            WudaReqData wudaReqData = new WudaReqData();
                            wudaReqData.school_code = school_code;
                            wudaReqData.student_no = student_no;
                            String hei = new Gson().toJson(wudaReqData);
                            String encodedString = Base64.encodeToString(hei.getBytes(), Base64.NO_WRAP);
                            trusted_platform_login(encodedString, App.mAxLoginSp.getUUID().replace("-", ""), md5(CooperateAppId.accesskey + encodedString + App.mAxLoginSp.getUUID().replace("-", "")));
                        } else {
                            //判断登录的账号是否绑定了学生
                            if (isLogin) {
                                login(loginEdMobile.getText().toString().trim(), loginEdPassword.getText().toString().trim(), App.mAxLoginSp.getUUID().replace("-", ""), school_code, student_name, student_no);
                            } else {
                                registAndLogin(regiMobile.getText().toString().trim(), regiVerCode.getText().toString().trim(),
                                        regiPassword.getText().toString().trim(), App.mAxLoginSp.getUUID().replace("-", ""), school_code, student_name, student_no);
                            }
                        }
                    }
                });
    }

    public static class StudentResponse {
        public BaseResponse resp;
        public String student_status;
    }

    public class WudaReqData implements Serializable {
        public String school_code;
        public String student_no;
    }

    /**
     * 第三方信任登录
     *
     * @param account
     * @param attribute_code
     * @param access_key
     */
    private void trusted_platform_login(String account, String attribute_code, String access_key) {
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        map.put("account_type", "StudentNO");
        map.put("attribute_code", attribute_code);
        map.put("access_key", access_key);
        CustomerService customerService = ApiFactory.getFactory().create(CustomerService.class);
        customerService.trusted_platform_login(NetUtils.getRequestParams(this, map), NetUtils.getSign(NetUtils.getRequestParams(this, map)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(this, true, null) {
                    @Override
                    public void onNext(Object o) {
                        final LoginVerPwdPresenter.LoginResponse response = new Gson().fromJson(o.toString(), LoginVerPwdPresenter.LoginResponse.class);
                        try {
                            //登录成功事项
                            LoginChangeDataForApp.updateDataForApp(response);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            AxToast.showHttpErr(LoginActivity.this, e.getMessage());
                        }
                    }
                });
    }

    /**
     * 通过第三方登录-成功后需要绑定学生信息
     *
     * @param mobile
     * @param pwd
     * @param uuid
     * @param school_code
     * @param student_name
     * @param student_no
     */
    public void login(String mobile, String pwd, String uuid, final String school_code, final String student_name, final String student_no) {
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("password", pwd);
        map.put("attribute_code", uuid);
        LoginService loginService = ApiFactory.getFactory().create(LoginService.class);
        loginService.login(NetUtils.getRequestParams(this, map), NetUtils.getSign(NetUtils.getRequestParams(this, map)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(this, true, null) {
                    @Override
                    public void onNext(Object o) {
                        try {
                            final LoginVerPwdPresenter.LoginResponse response = new Gson().fromJson(o.toString(), LoginVerPwdPresenter.LoginResponse.class);
                            LoginChangeDataForApp.updateDataForApp(response);
                            if (response.customer.fee_account != null) {
                                if (response.customer.fee_account.status.equals("OK") || (!TextUtils.isEmpty(response.customer.fee_account.name) && !TextUtils.isEmpty(response.customer.fee_account.student_no))) {
                                    Toast.makeText(mContext, "该账户已绑定", Toast.LENGTH_SHORT).show();
                                } else {
                                    //去bind
                                    bind_student(school_code, student_name, student_no);
                                }
                            } else {
                                //去bind
                                bind_student(school_code, student_name, student_no);
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            AxToast.showHttpErr(LoginActivity.this, e.getMessage());
                        }
                    }
                });
    }

    /**
     * 通过第三方注册完 需要绑定学生信息
     *
     * @param mobile
     * @param verCode
     * @param pwd
     * @param machineId
     * @param school_code
     * @param student_name
     * @param student_no
     */
    public void registAndLogin(String mobile, String verCode, String pwd, String machineId, final String school_code, final String student_name, final String student_no) {
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("secureity_code", verCode);
        map.put("attribute_code", machineId);
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        LoginService loginService = ApiFactory.getFactory().create(LoginService.class);

        Observable<String> objectObservable;
        map.put("password", pwd);
        objectObservable = loginService.regist(NetUtils.getRequestParams(this, map), NetUtils.getSign(NetUtils.getRequestParams(this, map)));
        objectObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(this, true, null) {
                    @Override
                    public void onNext(Object o) {
                        try {
                            final LoginVerPwdPresenter.LoginResponse response = new Gson().fromJson(o.toString(), LoginVerPwdPresenter.LoginResponse.class);
                            LoginChangeDataForApp.updateDataForApp(response);
                            if (response.customer.fee_account != null) {
                                if (response.customer.fee_account.status.equals("OK") || (!TextUtils.isEmpty(response.customer.fee_account.name) && !TextUtils.isEmpty(response.customer.fee_account.student_no))) {
                                    Toast.makeText(mContext, "该账户已绑定", Toast.LENGTH_SHORT).show();
                                } else {
                                    //去bind
                                    bind_student(school_code, student_name, student_no);
                                }
                            } else {
                                //去bind
                                bind_student(school_code, student_name, student_no);
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            AxToast.showHttpErr(LoginActivity.this, e.getMessage());
                        }
                    }
                });
    }

    private void bind_student(String school_code, String student_name, String student_no) {
        RetrofitFactory.setBaseUrl(AppConstant.URL);
        Map<String, Object> map = new HashMap<>();
        map.put("school_code", school_code);
        map.put("student_name", student_name);
        map.put("student_no", student_no);
        CustomerService customerService = ApiFactory.getFactory().create(CustomerService.class);
        customerService.bind_student(NetUtils.getRequestParams(this, map), NetUtils.getSign(NetUtils.getRequestParams(this, map)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>(this, true, null) {
                    @Override
                    public void onNext(Object o) {
                        try {
                            final CustomerResponse customerResponse = new Gson().fromJson(o.toString(), CustomerResponse.class);
                            if (customerResponse.resp.resp_code.equals(AppConstant.SUCCESS)) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            AxToast.showHttpErr(LoginActivity.this, e.getMessage());
                        }
                    }
                });
    }

    private void goLoginSelectCityActivity() {
        Intent intent = new Intent(this, LoginSelectCityActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(LoginSelectCityActivity.EXTRA_FROM_LOGIN, LoginSelectCityActivity.EXTRA_FROM_LOGIN);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}