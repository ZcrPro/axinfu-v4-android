package com.zhihuianxin.xyaxf.app.login.contract;


import com.zhihuianxin.xyaxf.http.BasePresenter;
import com.zhihuianxin.xyaxf.http.BaseView;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;
import com.zhihuianxin.xyaxf.modle.base.thrift.resource.School;

import java.util.List;

/**
 * Created by Vincent on 2016/10/31.
 */

public interface ILoginSelectSchoolContract {
    interface ISelectSchoolView extends BaseView<ISelectSchoolPresenter> {
        void setSchoolData(List<School> schoolData);
        void updateSchoolSuccess(Customer customer);
    }

    interface ISelectSchoolPresenter extends BasePresenter {
        void loadSchool(String cityCode);
        void updateSchool(String school_code);
    }
}
