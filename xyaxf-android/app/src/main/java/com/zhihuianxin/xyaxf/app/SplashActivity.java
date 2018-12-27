package com.zhihuianxin.xyaxf.app;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.axinfu.basetools.base.BaseActionBarActivity;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhihuianxin.axutil.Util;
import com.zhihuianxin.xyaxf.App;
import com.zhihuianxin.xyaxf.R;
import com.zhihuianxin.xyaxf.app.login.LoginActivity;
import com.zhihuianxin.xyaxf.database.DBLastVersion;
import com.zhihuianxin.xyaxf.database.DBLastVersion_Table;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Vincent on 2016/10/12.
 */

public class SplashActivity extends BaseActionBarActivity {
    @InjectView(R.id.updateMsgList)
    ListView mListView;
    @InjectView(R.id.gif_view_upper)
    GifImageView gifViewUpper;
    @InjectView(R.id.gif_view_lower)
    GifImageView gifViewLower;
    @InjectView(R.id.cover_view)
    View mCoverView;

    private String[] nupdateContent = new String[]{"校园安心付新年放大招！V3.0全新上线 ！",
            "全新的超Q界面   蜜汁萌的动态效果  ",
            "层次感更加的明朗  操作更加的流畅",
            "一目了然的服务类别    方便快捷的支付方式",
            "新增登录密码功能  账户更安全 ",
            "新增客服留言功能  响应更迅速",
            "…",
            "更多新功能等你体验！"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        findViewById(R.id.action_bar).setVisibility(View.GONE);
        judgeWay();
    }

    private void judgeWay(){
        final DBLastVersion db_lastVersion = SQLite.select()
                .from(DBLastVersion.class)
                .where(DBLastVersion_Table.version_name.is(Util.getPackageInfo(this).versionName))
                .querySingle();

        if (db_lastVersion==null){
            DBLastVersion ver = new DBLastVersion();
            ver.version_name = Util.getPackageInfo(this).versionName;
            ver.version_code = Util.getPackageInfo(this).versionCode;
            ver.save();
        }else {
            if(!Util.getPackageInfo(this).versionName.equals(db_lastVersion.version_name) || !App.mAxLoginSp.getHadShowSplash()){
                initViews();
                upperHalfAnim();
                showUpdateTextAnim();
            } else {
                goNextView();
            }
        }
    }

    private void initViews(){
        Adapter adapter = new Adapter(this,0);
        for(int i = 0;i < nupdateContent.length;i++){
            adapter.add(nupdateContent[i]);
        }
        mListView.setAdapter(adapter);
    }

    private void upperHalfAnim(){
        try {
            GifDrawable gifFromResource = new GifDrawable( getResources(),R.raw.comp_1);
            gifViewUpper.setImageDrawable(gifFromResource);
            gifFromResource.setLoopCount(1);
            gifFromResource.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showUpdateTextAnim(){
        ObjectAnimator anim = ObjectAnimator.ofFloat(mCoverView, "alpha", 1f, 0f);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                showLowerHalfAnim();
            }
        });
        anim.setDuration(3000);
        anim.setStartDelay(2000);
        anim.start();
    }

    private void showLowerHalfAnim(){
        gifViewUpper.setVisibility(View.GONE);
        try {
            GifDrawable gifFromResource = new GifDrawable( getResources(),R.raw.comp_2);
            gifViewLower.setImageDrawable(gifFromResource);
            gifFromResource.addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted(int loopNumber) {
                    goNextView();
                }
            });
            gifFromResource.setLoopCount(1);
            gifFromResource.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.jump)
    public void onBtnJump(){
        goNextView();
    }

    private void goNextView(){
        DBLastVersion ver = new DBLastVersion();
        ver.version_name = Util.getPackageInfo(this).versionName;
        ver.version_code = Util.getPackageInfo(this).versionCode;
        ver.save();
        App.mAxLoginSp.setHadShowSplash(true);
        if(App.mAxLoginSp.getLoginSign()){
            alreadyLogin();
        } else{
            inputLocalMobil();
        }
        finish();
    }

    public void inputLocalMobil() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    public void alreadyLogin() {
//        startActivity(new Intent(this,MainActivity.class));
//        finish();
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

    }

    class Adapter extends ArrayAdapter<String> {
        private Context mContext;

        public Adapter(Context context, int resource) {
            super(context, 0);
            mContext = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.splash_update_item,null);
            ((TextView)view.findViewById(R.id.content)).setText(getItem(position));
            return view;
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.splash_activity;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }
}