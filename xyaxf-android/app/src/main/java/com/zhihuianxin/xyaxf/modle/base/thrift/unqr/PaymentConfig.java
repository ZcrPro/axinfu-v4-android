package com.zhihuianxin.xyaxf.modle.base.thrift.unqr;

import java.io.Serializable;

/**
 * Created by Vincent on 2017/11/14.
 */

public class PaymentConfig  implements Serializable {
    public boolean has_pay_password;
    public boolean pin_free;
    public String pin_free_amount;
    public String trade_limit_per_day;
    public String trade_limit_per_times;		// 单笔限额-剩余额度
    public String limit_per_day;				// 单日限额-总额度
    public String limit_per_times;				// 单笔限额-总额度
    public String pin_free_limit_per_day;				// 单笔限额-总额度
    public boolean has_bank_card;				// 单笔限额-总额度
}
