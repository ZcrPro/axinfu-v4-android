package com.zhihuianxin.xyaxf.app.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axinfu.basetools.base.BaseActionBarActivity;
import com.zhihuianxin.xyaxf.R;
import com.zhihuianxin.xyaxf.app.home.HomeFragment;
import com.zhihuianxin.xyaxf.app.life.LifeFragment;
import com.zhihuianxin.xyaxf.app.mine.MineFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;


//todo 主页检查解锁

public class MainActivity extends BaseActionBarActivity implements View.OnClickListener {

    @InjectView(R.id.img_mine)
    ImageView imgMine;
    @InjectView(R.id.tv_mine)
    TextView tvMine;
    @InjectView(R.id.layout_mine)
    RelativeLayout layoutMine;
    @InjectView(R.id.container)
    FrameLayout container;
    @InjectView(R.id.img_home)
    ImageView imgHome;
    @InjectView(R.id.tv_home)
    TextView tvHome;
    @InjectView(R.id.layout_home)
    RelativeLayout layoutHome;
    @InjectView(R.id.img_life)
    ImageView imgLife;
    @InjectView(R.id.tv_life)
    TextView tvLife;
    @InjectView(R.id.layout_life)
    RelativeLayout layoutLife;
    @InjectView(R.id.activity_main)
    LinearLayout activityMain;
    @InjectView(R.id.msg_point)
    ImageView mMsgPointImg;
    @InjectView(R.id.swipeView)
    SwipeRefreshLayout mSwipeRefreshLayout;

    public static final String RELOGIN_BROADCAST = "relogin_broadcast";

    private int selectColor;
    private int unSelectColor;
    private Fragment[] fragments;
    private int currentIndex;
    private int index;

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            setTabs(0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        findViewById(R.id.action_bar).setVisibility(View.GONE);
        initDatas();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //回到主页应该要去检测的内容
    }

    //todo  cid获取通过service
    private void initDatas() {
        mSwipeRefreshLayout.setOnRefreshListener(refreshListener);
        layoutHome.setOnClickListener(this);
        layoutLife.setOnClickListener(this);
        layoutMine.setOnClickListener(this);
        selectColor = getResources().getColor(R.color.axf_text_content_gray);
        unSelectColor = getResources().getColor(R.color.axf_light_gray);
        fragments = new Fragment[3];
        fragments[0] = HomeFragment.newInstance("home");
        fragments[1] = LifeFragment.newInstance("life");
        fragments[2] = MineFragment.newInstance("mine");
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragments[0]).commitAllowingStateLoss();
    }

    public void setTabs(int pos) {
        resetColor();
        switch (pos) {
            case 0:
                imgHome.setImageResource(R.mipmap.axinfu_icon_nor);
                tvHome.setTextColor(selectColor);
                break;
            case 1:
                imgLife.setImageResource(R.mipmap.life_icon_nor);
                tvLife.setTextColor(selectColor);
                break;
            case 2:
                imgMine.setImageResource(R.mipmap.icon_mine_selected);
                tvMine.setTextColor(selectColor);
                break;
        }

        if (currentIndex != index || mSwipeRefreshLayout.isRefreshing()) {
            FragmentManager fm = getSupportFragmentManager();
            final FragmentTransaction ft = fm.beginTransaction();
            if (mSwipeRefreshLayout.isRefreshing()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ft.detach(fragments[index]).attach(fragments[index]).commit();
                    }
                }, 150);
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                ft.hide(fragments[currentIndex]);
                if (!fragments[index].isAdded()) {
                    ft.add(R.id.container, fragments[index]);
                }
                ft.show(fragments[index]).commitAllowingStateLoss();
            }
        }
        currentIndex = index;
        if (currentIndex == 0) {
            mSwipeRefreshLayout.setEnabled(true);
        } else {
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    public void resetColor() {
        imgHome.setImageResource(R.mipmap.axinfu_icon_dis);
        imgLife.setImageResource(R.mipmap.life_icon_dis);
        imgMine.setImageResource(R.mipmap.icon_mine_normal);
        tvHome.setTextColor(unSelectColor);
        tvLife.setTextColor(unSelectColor);
        tvMine.setTextColor(unSelectColor);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_home:
                index = 0;
                setTabs(index);
                break;

            case R.id.layout_life:
                index = 1;
                setTabs(index);
                break;

            case R.id.layout_mine:
                index = 2;
                setTabs(index);
                break;
        }
    }
}
