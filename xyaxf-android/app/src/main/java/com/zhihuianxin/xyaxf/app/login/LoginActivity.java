package com.zhihuianxin.xyaxf.app.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhihuianxin.xyaxf.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends Activity {

    @InjectView(R.id.input_back)
    ImageView inputBack;
    @InjectView(R.id.back_icon)
    RelativeLayout backIcon;
    @InjectView(R.id.top_title)
    TextView topTitle;
    @InjectView(R.id.iv_view)
    ImageView ivView;
    @InjectView(R.id.editmobile2)
    EditText editmobile2;
    @InjectView(R.id.editText)
    EditText editText;
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
    @InjectView(R.id.edit_pwd)
    EditText editPwd;
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
    ImageView pwdlookokVer;
    @InjectView(R.id.pwdlookun_ver)
    ImageView pwdlookunVer;
    @InjectView(R.id.pwdlook_ver)
    RelativeLayout pwdlookVer;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.inject(this);
        initUI();
    }

    private void initUI() {
        //登录状态

    }
}
