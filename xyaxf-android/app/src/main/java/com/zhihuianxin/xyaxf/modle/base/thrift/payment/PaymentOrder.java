package com.zhihuianxin.xyaxf.modle.base.thrift.payment;

import com.zhihuianxin.xyaxf.modle.base.thrift.base.BaseMessageObject;
import com.zhihuianxin.xyaxf.modle.base.thrift.base.PayMethod;

import java.io.Serializable;
import java.util.List;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class PaymentOrder extends BaseMessageObject implements Serializable {
    public PayMethod pay_method;  // required
    public String pay_data;  // required
    public String order_no;  // optional
    public String payment_amt;  // optional
    public String trade_summary;  // required
    public String success_notice;  // required
    public String order_time;  // required
    public String pay_for;

    public String error_msg;
    public String orig_amt;

    public List<AxfCouponInfo> coupon_info;
}