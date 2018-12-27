package com.zhihuianxin.xyaxf.modle.base.thrift.fee;

import java.io.Serializable;

public class PricingStrategy  implements Serializable {
    public String id;                          // ID
    public String name;                        // 计价策略名称
    public String float_amt;                   // 该项计价策略浮动金额
}
