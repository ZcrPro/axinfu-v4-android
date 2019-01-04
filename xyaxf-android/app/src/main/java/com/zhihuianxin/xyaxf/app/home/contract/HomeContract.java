package com.zhihuianxin.xyaxf.app.home.contract;


import com.zhihuianxin.xyaxf.http.BasePresenter;
import com.zhihuianxin.xyaxf.http.BaseView;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;
import com.zhihuianxin.xyaxf.modle.base.thrift.message.Advertise;

import java.util.List;

/**
 * Created by zcrpro on 2016/11/4.
 */
public interface HomeContract {

    interface HomeView extends BaseView<HomePresenter> {
        void customerSuccess(Customer customer);

        void bannerSuccess(List<Advertise> list);

        void getEcardDataSuccess(com.zhihuianxin.xyaxf.app.home.presenter.HomePresenter.EcardResponse ecardResponse);
    }

    interface HomePresenter extends BasePresenter {
        void loadBannerData();
        void loadCustomerData();
        void getEcardData();
    }
}
