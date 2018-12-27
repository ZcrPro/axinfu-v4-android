package com.zhihuianxin.xyaxf.modle.base.thrift.fee;


import java.util.List;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class FeeRecord {
    public String fee_id;  // required
    public String title;  // required
    public String amount;  // required
    public List<PayFeeRecord> pay_records;  // required
    public Boolean settled = false;
}