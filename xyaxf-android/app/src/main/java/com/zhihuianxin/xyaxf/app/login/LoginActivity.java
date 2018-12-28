package com.zhihuianxin.xyaxf.app.login;

import android.animation.ObjectAnimator;
import android.app.Activity;
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
import android.util.DisplayMetrics;
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

import com.zhihuianxin.xyaxf.R;
import com.zhihuianxin.xyaxf.http.AppConstant;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends Activity {


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
    @InjectView(R.id.editText_ver)
    EditText editTextVer;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.inject(this);
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

}