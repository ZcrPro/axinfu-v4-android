package com.zhihuianxin.xyaxf.app.login;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class LoginSelectCityActivity extends BaseActionBarActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_select_city_fragment);
        ButterKnife.inject(this);
    }
}
