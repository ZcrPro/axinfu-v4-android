package com.zhihuianxin.xyaxf;

import android.app.Application;

import com.cocosw.favor.FavorAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.zhihuianxin.xyaxf.data.IAXLogin;
import com.zhihuianxin.xyaxf.data.ISession;
import com.zhihuianxin.xyaxf.util.ESUUID;

public class App extends Application {

    public static IAXLogin mAxLoginSp;
    public static ISession mSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initDataBase();

        mAxLoginSp = new FavorAdapter.Builder(getApplicationContext()).build().create(IAXLogin.class);
        mSession = new FavorAdapter.Builder(getApplicationContext()).build().create(ISession.class);

        //生成uuid
        ESUUID esuuid = new ESUUID();
        esuuid.load();
        //iamgeloader
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageLoader.getInstance().init(configuration);
    }

    /**
     * 初始化数据库
     */
    private void initDataBase() {
        FlowManager.init(this);
    }
}
