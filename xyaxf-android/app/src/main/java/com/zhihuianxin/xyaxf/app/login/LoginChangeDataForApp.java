package com.zhihuianxin.xyaxf.app.login;

import com.zhihuianxin.xyaxf.App;
import com.zhihuianxin.xyaxf.app.login.presenter.LoginVerPwdPresenter;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;

/**
 * 登录之后刷新数据
 */
public class LoginChangeDataForApp {

    /**
     * 通过返回值全数据更新
     *
     * @param response
     */
    public static void updateDataForApp(LoginVerPwdPresenter.LoginResponse response) {
        App.mSession.setSession(response.session);
        App.mAxLoginSp.setUserMobil(response.customer.base_info.mobile);
        App.mAxLoginSp.setRegistSerial(response.customer.base_info.regist_serial);
        App.mAxLoginSp.setLoginSign(true);
    }


    /**
     * 根据具体数据更新
     * @param customer
     * @param session
     */
    public static void updateDataForApp(Customer customer, String session) {
        App.mSession.setSession(session);
        App.mAxLoginSp.setUserMobil(customer.base_info.mobile);
        App.mAxLoginSp.setRegistSerial(customer.base_info.regist_serial);
        App.mAxLoginSp.setLoginSign(true);
    }
}
