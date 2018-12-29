package com.zhihuianxin.xyaxf.app.login.contract;


import com.zhihuianxin.xyaxf.http.BasePresenter;
import com.zhihuianxin.xyaxf.http.BaseView;
import com.zhihuianxin.xyaxf.modle.base.thrift.resource.City;

import java.util.List;

/**
 * Created by Vincent on 2016/10/12.
 */

public interface ILoginSelectCityContract {
    interface ISelectCityView extends BaseView<ISelectCityPresenter> {
        void setCityData(List<City> cityData);
    }

    interface ISelectCityPresenter extends BasePresenter {
        void loadCity();
    }
}
