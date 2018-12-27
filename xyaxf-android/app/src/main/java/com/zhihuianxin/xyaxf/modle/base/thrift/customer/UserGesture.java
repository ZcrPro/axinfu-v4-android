package com.zhihuianxin.xyaxf.modle.base.thrift.customer;

import java.io.Serializable;

/**
 * Created by Vincent on 2018/1/11.
 */

public class UserGesture  implements Serializable {

    public String mobile;
    public String gesturePwd;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGesturePwd() {
        return gesturePwd;
    }

    public void setGesturePwd(String gesturePwd) {
        this.gesturePwd = gesturePwd;
    }
}
