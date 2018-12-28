package com.zhihuianxin.xyaxf.data;

import com.cocosw.favor.AllFavor;
import com.cocosw.favor.Default;

/**
 * Created by Vincent on 2016/10/9.
 */
@AllFavor
public interface IAXLogin {

    // 登录成功标识
    @Default("false")
    boolean getLoginSign();
    void setLoginSign(boolean sign);

    // 登录用户手机号
    @Default("")
    String getUserMobil();
    void setUserMobil(String mobil);

    // 登录用户的唯一标识
    @Default("")
    String getRegistSerial();
    void setRegistSerial(String regist_serial);

    // 是否显示过首页加载动画
    @Default("false")
    boolean getHadShowSplash();
    void setHadShowSplash(boolean flag);
}
