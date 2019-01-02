package com.zhihuianxin.xyaxf.app.login;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axinfu.basetools.base.BaseActionBarActivity;
import com.gjiazhe.wavesidebar.WaveSideBar;
import com.zhihuianxin.xyaxf.R;
import com.zhihuianxin.xyaxf.app.login.adapter.LoginSearchCityListAdapter;
import com.zhihuianxin.xyaxf.app.login.adapter.LoginSelectCityAdapter;
import com.zhihuianxin.xyaxf.app.login.contract.ILoginSelectCityContract;
import com.zhihuianxin.xyaxf.modle.base.thrift.resource.City;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class LoginSelectCityActivity extends BaseActionBarActivity implements ILoginSelectCityContract.ISelectCityView {

    @InjectView(R.id.search_edit)
    EditText searchEdit;
    @InjectView(R.id.search_clear)
    ImageView searchClear;
    @InjectView(R.id.search_cancel)
    TextView searchCancel;
    @InjectView(R.id.search_view)
    RelativeLayout searchView;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.search_icon)
    ImageView searchIcon;
    @InjectView(R.id.back_icon)
    RelativeLayout backIcon;
    @InjectView(R.id.bar)
    RelativeLayout bar;
    @InjectView(R.id.side_bar)
    WaveSideBar sideBar;
    @InjectView(R.id.stickListView)
    StickyListHeadersListView stickListView;
    @InjectView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @InjectView(R.id.contentView)
    FrameLayout contentView;
    @InjectView(R.id.graySearchBg)
    View graySearchBg;
    @InjectView(R.id.search_list)
    ListView searchList;
    @InjectView(R.id.grayBg)
    View grayBg;
    @InjectView(R.id.click_errorbtn)
    Button clickErrorbtn;
    @InjectView(R.id.exit)
    TextView exit;
    @InjectView(R.id.exit_view)
    RelativeLayout exitView;
    @InjectView(R.id.backAnimView)
    RelativeLayout backAnimView;

    public static final String EXTRA_FROM_LOGIN = "from_login";

    private LoginSelectCityAdapter mAdapter;
    private LoginSearchCityListAdapter searchListAdapter;

    private ILoginSelectCityContract.ISelectCityPresenter presenter;

    private List<City> cities; //存储城市用于搜索

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_select_city_fragment);
        ButterKnife.inject(this);
        cities =new ArrayList<>();
        findViewById(R.id.action_bar).setVisibility(View.GONE); //关闭BaseActionBarActivity进行自定义
        initView();
    }

    private void initView() {
        mAdapter = new LoginSelectCityAdapter(this);
        stickListView.setAdapter(mAdapter);
        searchEdit.addTextChangedListener(textWatcher);
        swiperefreshlayout.setOnRefreshListener(refreshListener);
        stickListView.setOnScrollListener(scrollListener);
        stickListView.setOnItemClickListener(itemClickListener);
        presenter.loadCity();
    }

    @Override
    public void setCityData(final List<City> cityData) {
        mAdapter.clear();
        cities.clear();
        cities.addAll(cityData);
        for (int i = 0; i < cityData.size(); i++) {
            LoginSelectCityAdapter.CityExt ext = new LoginSelectCityAdapter.CityExt();
            ext.cityCode = cityData.get(i).code;
            ext.cityName = cityData.get(i).name;
            ext.category_value = cityData.get(i).quanpin.substring(0, 1).toUpperCase();
            ext.category = ext.category_value;
            mAdapter.add(ext);
        }
        mAdapter.notifyDataSetChanged();
        sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(final String index) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < cityData.size(); i++) {
                            if (cityData.get(i).quanpin.substring(0, 1).toUpperCase().equals(index)) {
                                stickListView.smoothScrollToPosition(i, i);
                                return;
                            }
                        }
                    }
                }, 100);

            }
        });
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
                clearSearchListAndHide();
            }
        }
    };

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.loadCity();
        }
    };

    AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem == 0)
                swiperefreshlayout.setEnabled(true);
            else
                swiperefreshlayout.setEnabled(false);
        }
    };

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(LoginSelectCityActivity.this, LoginSelectSchoolActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(LoginSelectSchoolActivity.EXTRA_CITY_CODE, ((LoginSelectCityAdapter.CityExt) view.getTag()).cityCode);
            i.putExtras(bundle);
            startActivity(i);
        }
    };

    @Override
    public void setPresenter(ILoginSelectCityContract.ISelectCityPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void loadStart() {

    }

    @Override
    public void loadError(String errorMsg) {

    }

    @Override
    public void loadComplete() {

    }

    private void showSearchList(String key) {
        searchListAdapter = new LoginSearchCityListAdapter(this, key);
        searchList.setAdapter(searchListAdapter);
        searchList.setItemsCanFocus(true);
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).quanpin.equals(key)||cities.get(i).name.equals(key)){
                City c = new City();
                c.quanpin = cities.get(i).quanpin;
                c.name = cities.get(i).name;
                c.code = cities.get(i).code;
                searchListAdapter.add(c);
            }
        }
        searchListAdapter.notifyDataSetChanged();
        searchList.setVisibility(View.VISIBLE);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(LoginSelectCityActivity.this, LoginSelectSchoolActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(LoginSelectSchoolActivity.EXTRA_CITY_CODE, ((City) view.getTag()).code);
                i.putExtras(bundle);
                clearSearchListAndHide();
                hideSearchAnim();
                searchEdit.setText("");
                startActivity(i);
            }
        });
    }

    private void clearSearchListAndHide() {
        if (searchListAdapter == null) {
            searchListAdapter = new LoginSearchCityListAdapter(this, "");
        }
        searchListAdapter.clear();
        searchListAdapter.notifyDataSetChanged();
        searchList.setVisibility(View.GONE);
    }

    private void hideSearchAnim() {
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(bar, "translationY", -(int) metrics.density * 100, 0);
        animator2.setDuration(600);
        animator2.start();
        searchEdit.setFocusable(true);
        @SuppressLint("WrongConstant") InputMethodManager imm = (InputMethodManager) getSystemService("input_method");//Context.INPUT_METHOD_SERVICE
        assert imm != null;
        imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
        graySearchBg.setVisibility(View.GONE);
        searchList.setVisibility(View.GONE);
    }
}
