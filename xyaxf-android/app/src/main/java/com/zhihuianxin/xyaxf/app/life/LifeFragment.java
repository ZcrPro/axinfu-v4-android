package com.zhihuianxin.xyaxf.app.life;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.axinfu.basetools.base.BaseFragment;
import com.zhihuianxin.xyaxf.R;
import com.zhihuianxin.xyaxf.app.home.HomeFragment;

public class LifeFragment extends BaseFragment {

    public static final String EXTRA_TEXT = "extra_text";

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.life_fragment;
    }

    public static Fragment newInstance(String text) {
        LifeFragment fragment = new LifeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TEXT, text);
        fragment.setArguments(bundle);
        return fragment;
    }

}
