package com.zhihuianxin.xyaxf.app.main;

import android.app.Activity;
import android.os.Bundle;

import com.zhihuianxin.xyaxf.R;


public class MainActivity extends Activity {

    public static final String RELOGIN_BROADCAST = "relogin_broadcast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
