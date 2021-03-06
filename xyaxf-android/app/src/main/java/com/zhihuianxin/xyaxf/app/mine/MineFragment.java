package com.zhihuianxin.xyaxf.app.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.axinfu.basetools.base.BaseFragment;
import com.zhihuianxin.xyaxf.R;
import com.zhihuianxin.xyaxf.app.home.HomeFragment;

public class MineFragment extends BaseFragment {

    public static final String EXTRA_TEXT = "extra_text";

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.mine_fragment;
    }

    public static Fragment newInstance(String text) {
        MineFragment fragment = new MineFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TEXT, text);
        fragment.setArguments(bundle);
        return fragment;
    }
}
