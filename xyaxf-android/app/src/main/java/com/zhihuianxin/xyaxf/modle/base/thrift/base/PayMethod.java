package com.zhihuianxin.xyaxf.modle.base.thrift.base;

import com.zhihuianxin.xyaxf.modle.base.thrift.unqr.PaymentConfig;
import com.zhihuianxin.xyaxf.modle.base.thrift.unqr.UPBankCard;

import java.io.Serializable;
import java.util.List;

public class PayMethod  implements Serializable {
    public String channel;
    public String merchant_id;
    public String merchant_code;
    public String promotion_hint;
    public String assistance_hint;
    public String ax_merchant_no;
    public List<String> supported_banks;
    public boolean is_recommended;            // 是否推广
    public boolean is_default;                    // 是否默认
    public UPBankCard card;
    public PaymentConfig payment_config;
    public String purpose;
    public boolean isSelected =false;
    public boolean enabled =false;  //是否可用\
    public String remark;  //是否可用
    public String balance;  //是否可用
}
