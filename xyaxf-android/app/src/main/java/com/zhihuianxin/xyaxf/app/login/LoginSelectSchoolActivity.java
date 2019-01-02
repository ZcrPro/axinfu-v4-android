package com.zhihuianxin.xyaxf.app.login;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.axinfu.basetools.base.BaseActionBarActivity;
import com.gjiazhe.wavesidebar.WaveSideBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zhihuianxin.xyaxf.App;
import com.zhihuianxin.xyaxf.R;
import com.zhihuianxin.xyaxf.app.login.adapter.LoginSearchSchoolListAdapter;
import com.zhihuianxin.xyaxf.app.login.adapter.LoginSelectSchoolAdapter;
import com.zhihuianxin.xyaxf.app.login.contract.ILoginSelectSchoolContract;
import com.zhihuianxin.xyaxf.app.login.presenter.LoginSelectSchoolPresenter;
import com.zhihuianxin.xyaxf.app.view.GifView;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;
import com.zhihuianxin.xyaxf.modle.base.thrift.resource.School;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Vincent on 2016/10/20.
 */

public class LoginSelectSchoolActivity extends BaseActionBarActivity implements ILoginSelectSchoolContract.ISelectSchoolView {
    public static final String EXTRA_CITY_CODE = "city_code";

    @InjectView(R.id.search_list)
    ListView mSearchList;
    @InjectView(R.id.graySearchBg)
    View mSearchGrayBg;
    @InjectView(R.id.search_edit)
    EditText mSearchEdit;
    @InjectView(R.id.bar)
    View mActionBarView;
    @InjectView(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.stickListView)
    StickyListHeadersListView mListView;
    @InjectView(R.id.side_bar)
    WaveSideBar sideBar;
    private LoginSelectSchoolAdapter mAdapter;
    @InjectView(R.id.bottomView)
    View mSelectView;
    @InjectView(R.id.school_logo)
    ImageView mSchoolLogo;
    @InjectView(R.id.gif_bg_view)
    View mGitbg;
    @InjectView(R.id.gif_view)
    GifView mGitView;

    private DisplayMetrics metrics;
    private String mCityCode;
    private School mSelectSchool;
    private ILoginSelectSchoolContract.ISelectSchoolPresenter mPresenter;
    private LoginSearchSchoolListAdapter searchListAdapter;

    private List<School> schools; //存储学校用于搜索

    @Override
    protected int getContentViewId() {
        return R.layout.login_select_school_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        metrics = new DisplayMetrics();

        schools = new ArrayList<>();

        findViewById(R.id.action_bar).setVisibility(View.GONE);
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        new LoginSelectSchoolPresenter(this, this);
        ButterKnife.inject(this);
        initView();
    }

    protected void initView() {
        App.mAxLoginSp.setSelectSchoolCode("");
        mCityCode = getIntent().getExtras().getString(EXTRA_CITY_CODE);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mAdapter = new LoginSelectSchoolAdapter(this);
        mListView.setAdapter(mAdapter);

        mSearchEdit.addTextChangedListener(textWatcher);
        mSelectView.setOnClickListener(onClickListener);
        mSwipeRefreshLayout.setOnRefreshListener(refreshListener);
        mListView.setOnScrollListener(scrollListener);
        mListView.setOnItemClickListener(itemClickListener);

        mPresenter.loadSchool(mCityCode);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() != 0) {
                showSearchList(s.toString().trim());
            } else {
                mSearchList.setVisibility(View.GONE);
                if (searchListAdapter!=null){
                    searchListAdapter.clear();
                    searchListAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submitSchool();
        }
    };

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPresenter.loadSchool(mCityCode);
        }
    };

    AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem == 0)
                mSwipeRefreshLayout.setEnabled(true);
            else
                mSwipeRefreshLayout.setEnabled(false);
        }
    };

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            App.mAxLoginSp.setSelectSchoolCode(((LoginSelectSchoolAdapter.SchoolExt) view.getTag()).school.code);
            mAdapter.notifyDataSetChanged();

            selectSchool(((LoginSelectSchoolAdapter.SchoolExt) view.getTag()).school);
        }
    };

    private void selectSchool(final School school) {
        mSelectSchool = school;

        DisplayImageOptions config = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage(school.logo, config, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                mSchoolLogo.setImageBitmap(loadedImage);
            }
        });

        mSelectView.setBackgroundResource(R.drawable.btn_axf_blue);
    }

    private void submitSchool() {
        if (mSelectSchool == null) {
            return;
        }
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mGitbg, "translationY", 0, -(int) metrics.density * 200);
        animator2.setDuration(300);
        animator2.start();
        mGitView.setMovieResource(R.raw.gif_loading);

        mPresenter.updateSchool(mSelectSchool.code);
    }

    private void hideGifView() {
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mGitbg, "translationY", (int) metrics.density * 200, 0);
        animator2.setDuration(300).start();
    }

    private void resetAnima() {
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mGitbg, "translationY", (int) metrics.density * 200, 0);
        animator2.setDuration(300);
        animator2.start();
        mGitView.setPaused(true);
    }

    @Override
    public void setSchoolData(List<School> schoolData) {
        mAdapter.clear();
        schools.clear();
        schools.addAll(schoolData);
        for (int i = 0; i < schoolData.size(); i++) {
            LoginSelectSchoolAdapter.SchoolExt ext = new LoginSelectSchoolAdapter.SchoolExt();
            ext.school = schoolData.get(i);

            ext.category_value = schoolData.get(i).quanpin.substring(0, 1).toUpperCase();
            ext.category = ext.category_value;
            mAdapter.add(ext);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateSchoolSuccess(Customer customer) {
        successLogin(customer);
    }

    private void successLogin(final Customer customer) {
            LoginChangeDataForApp.updateDataForApp(customer);
//        Intent intentBroadCast = new Intent(HomeFragment.EXTRA_SCHOOL_DATA);
//        sendBroadcast(intentBroadCast);
//        Log.d("selectcity", "存储customer数据成功!");
//        // 跳转到主页
//        resetAnima();
//
//        Intent intent = new Intent(HomeFragment.EXTRA_SCHOOL_DATA);
//        sendBroadcast(intent);
//
//        startActivity(new Intent(LoginSelectSchoolActivity.this, MainActivity.class));
//        finish();
    }

    @OnClick(R.id.search_cancel)
    public void onBtnSearchCancel() {
        hideSearchAnim();
    }

    @OnClick(R.id.back_icon)
    public void onBtnBack() {
        finish();
    }

    @OnClick(R.id.search_icon)
    public void onBtnSearch() {
        showSearchAnim();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchEdit.getWindowToken(), 0);
    }

    @OnClick(R.id.search_clear)
    public void onBtnSearchClear() {
        mSearchList.setVisibility(View.GONE);
        mSearchEdit.setText("");
        searchListAdapter.clear();
        searchListAdapter.notifyDataSetChanged();
    }

    private void showSearchList(String key) {//.equalTo("city_code",mCityCode)
        mSearchList.setItemsCanFocus(true);
        searchListAdapter = new LoginSearchSchoolListAdapter(this, key);
        mSearchList.setAdapter(searchListAdapter);

        for (int i = 0; i < schools.size(); i++) {
            if (schools.get(i).quanpin.equals(key)||schools.get(i).name.equals(key)){
                School s = new School();
                s.quanpin = schools.get(i).quanpin;
                s.name = schools.get(i).name;
                s.code = schools.get(i).code;
                searchListAdapter.add(s);
            }
        }

        searchListAdapter.notifyDataSetChanged();
        mSearchList.setVisibility(View.VISIBLE);
        mSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectSchool = (School) view.getTag();
                submitSchool();
            }
        });
    }

    private void showSearchAnim() {
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mActionBarView, "translationY", 0, -(int) metrics.density * 100);
        animator2.setDuration(600);
        animator2.start();
        mSearchEdit.setFocusable(true);
        mSearchEdit.setFocusableInTouchMode(true);
        mSearchEdit.requestFocus();
        mSearchEdit.requestFocusFromTouch();
        @SuppressLint("WrongConstant") InputMethodManager imm = (InputMethodManager) getSystemService("input_method");//Context.INPUT_METHOD_SERVICE
        imm.showSoftInput(mSearchEdit, InputMethodManager.SHOW_FORCED);
        mSearchGrayBg.setVisibility(View.VISIBLE);
    }

    private void hideSearchAnim() {
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mActionBarView, "translationY", -(int) metrics.density * 100, 0);
        animator2.setDuration(600);
        animator2.start();
        mSearchEdit.setFocusable(true);
        @SuppressLint("WrongConstant") InputMethodManager imm = (InputMethodManager) getSystemService("input_method");//Context.INPUT_METHOD_SERVICE
        imm.hideSoftInputFromWindow(mSearchEdit.getWindowToken(), 0);
        mSearchGrayBg.setVisibility(View.GONE);
        mSearchList.setVisibility(View.GONE);
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    public void setPresenter(ILoginSelectSchoolContract.ISelectSchoolPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadStart() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void loadError(String errorMsg) {
        mSwipeRefreshLayout.setRefreshing(false);
        hideGifView();
    }

    @Override
    public void loadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
