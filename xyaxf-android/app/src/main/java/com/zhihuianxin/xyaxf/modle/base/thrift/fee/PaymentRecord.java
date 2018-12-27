package com.zhihuianxin.xyaxf.modle.base.thrift.fee;

import com.zhihuianxin.xyaxf.modle.base.thrift.base.PayMethod;
import com.zhihuianxin.xyaxf.modle.base.thrift.payment.AxfCouponInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vincent on 2016/11/11.
 */

public class PaymentRecord  implements Serializable {

    public String order_no;
    public String order_time;
    public String payfor;
    public String order_status;
    public String order_status_desc;
    public String pay_amt;
    public String pay_channel;
    public String trade_summary;
    public String pay_time;
    public String pay_remark;
    public String orig_amt;
    public PayMethod pay_method;


    public String refund_time;
    // 退款时间
    public String cancel_time;                // 订单取消（冲正或者撤销）时间


    public String voucher_num;

    public String ecard_account;

    public RecordFee fee;

    public List<PricingStrategy> strategy = null;

    public List<AxfCouponInfo> coupon_info = null;

    public String merchant_name;

    public String shop_name;

    public String trade_type;

    public String payment_record_url;

    public String mode;
}
