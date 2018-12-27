package com.zhihuianxin.xyaxf.modle.base.thrift.lock;

import java.io.Serializable;

/**
 * Created by zcrpro on 2018/2/6.
 */

public class Lock  implements Serializable {

    public String mobile;
    public String gesturePassword;
    public boolean gestureStatus = false;
    public boolean fingerStatus = false;
    public boolean laterStatus = false;
    public boolean remindStatus = false;

}
