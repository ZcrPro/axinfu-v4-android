package com.zhihuianxin.xyaxf.app.login.contract;


import com.zhihuianxin.xyaxf.http.BasePresenter;
import com.zhihuianxin.xyaxf.http.BaseView;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.VerifyField;

import java.util.ArrayList;

/**
 * Created by Vincent on 2016/10/11.
 */

public interface ILoginVerPwdContract {
    interface ILoginVerPwdView extends BaseView<ILoginVerPwdPresenter> {
        void loginSuccess(Customer customer, String session);
        void getmodifyPwdInfoResult(ArrayList<VerifyField> verify_fields);
    }

    interface ILoginVerPwdPresenter extends BasePresenter {
        void login(String mobile, String pwd, String uuid);
        void getmodifyPwdInfo(String mobile);
    }
}
