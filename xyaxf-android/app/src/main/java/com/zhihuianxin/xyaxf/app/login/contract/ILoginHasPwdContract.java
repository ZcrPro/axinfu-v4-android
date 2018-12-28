package com.zhihuianxin.xyaxf.app.login.contract;



import com.zhihuianxin.xyaxf.http.BasePresenter;
import com.zhihuianxin.xyaxf.http.BaseView;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.MobileStatus;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.VerifyField;

import java.util.ArrayList;

/**
 * Created by Vincent on 2016/10/18.
 */

public interface ILoginHasPwdContract {
    interface ILoginHasPwdView extends BaseView<ILoginHasPwdPresenter> {
        void loginSuccess(Customer customer, String session);
        void setPwdOrRegistAndLoginSuccess(Customer customer, String session);
        void getVerCodeSuccess(String verCode);
        void getmodifyPwdInfoResult(ArrayList<VerifyField> verify_fields);
    }

    interface ILoginHasPwdPresenter extends BasePresenter {
        void login(String mobile, String pwd, String uuid);
        void getVerCode(String mobile);
        void setPwdOrRegistAndLogin(String mobile, String verCode, String pwd, String machineId);
        void getmodifyPwdInfo(String mobile);
    }
}
