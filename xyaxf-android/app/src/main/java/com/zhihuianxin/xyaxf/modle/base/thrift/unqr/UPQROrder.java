package com.zhihuianxin.xyaxf.modle.base.thrift.unqr;

import com.zhihuianxin.xyaxf.modle.base.thrift.base.PayMethod;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vincent on 2017/11/14.
 */

public class UPQROrder  implements Serializable {
    public String amt;
    public int valid_time;

    public String tn;
    public String order_time;
    public String payee_comments;
    public String order_type;
    public UPQRPayeeInfo payee_info;

    public List<PayMethod> pay_methods;
}
