package com.zhihuianxin.xyaxf.app.login.contract;


import com.zhihuianxin.xyaxf.http.BasePresenter;
import com.zhihuianxin.xyaxf.http.BaseView;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;

/**
 * Created by Vincent on 2016/10/17.
 */

public interface ILoginSetPwdOrRegistContract {
    interface ILoginSetPwdOrRegistView extends BaseView<ILoginSetPwdOrRegistPresenter> {
        void setPwdOrRegistAndLoginSuccess(Customer customer, String session);
        void getVerCodeSuccess(String verCode);
    }

    interface ILoginSetPwdOrRegistPresenter extends BasePresenter {
        void getVerCode(String mobile);
        void setPwdOrRegistAndLogin(String mobile, String verCode, String pwd, String machineId);
    }
}
