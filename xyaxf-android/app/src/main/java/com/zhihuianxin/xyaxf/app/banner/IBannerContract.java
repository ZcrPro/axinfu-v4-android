package com.zhihuianxin.xyaxf.app.banner;


import com.zhihuianxin.xyaxf.http.BasePresenter;
import com.zhihuianxin.xyaxf.http.BaseView;
import com.zhihuianxin.xyaxf.modle.base.thrift.message.Advertise;

import java.util.List;

/**
 * Created by Vincent on 2016/10/11.
 */

public interface IBannerContract {

    interface IBannerView extends BaseView<IBannerPresenter> {
        void bannerSuccess(List<Advertise> list);
        void bannerFailure();
    }

    interface  IBannerPresenter extends BasePresenter {
        void loadBannerData();
    }
}
