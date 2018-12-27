package com.axinfu.basetools.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.sdk.android.man.MANService;
import com.alibaba.sdk.android.man.MANServiceProvider;
import com.axinfu.basetools.IException;
import com.cocosw.favor.FavorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcrpro on 16/9/29.
 */
public abstract class BaseActivity extends BaseFragmentActivity {
    private static IException iException;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iException = new FavorAdapter.Builder(getApplicationContext()).build().create(IException.class);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MANService manService = MANServiceProvider.getService();
        manService.getMANPageHitHelper().pageAppear(this);
    }

    public static IException getExceptionFavor(){
        return iException;
    }

    public void reLogin(){
        iException.setReloginException(false);
        Toast.makeText(this,"您的设备已在其他位置登录，请重新登录",Toast.LENGTH_LONG).show();
        sendBroadcast(new Intent("relogin_broadcast"));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MANService manService = MANServiceProvider.getService();
        manService.getMANPageHitHelper().pageDisAppear(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 不受系统字体大小的影响
     * @return
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }
}
