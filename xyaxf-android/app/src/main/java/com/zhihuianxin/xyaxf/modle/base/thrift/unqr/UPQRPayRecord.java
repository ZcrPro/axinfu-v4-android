package com.zhihuianxin.xyaxf.modle.base.thrift.unqr;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Vincent on 2017/12/7.
 */

public class UPQRPayRecord  implements Serializable {

    public String qr_code;
    public String status;
    public String amount;// 支付金额
    public String merchant_id;
    public String merchant_name;
    public List<UPCoupon> couponInfo;
//    public String couponInfo;
    public String orig_amt;// 原金额
    public String order_no;
    public String order_time;
    public String voucher_num;
    public String order_type;
    public UPBankCard card;
    public String mode;
}
