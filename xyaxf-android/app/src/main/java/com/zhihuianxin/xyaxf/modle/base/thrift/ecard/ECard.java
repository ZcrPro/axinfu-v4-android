package com.zhihuianxin.xyaxf.modle.base.thrift.ecard;

import com.zhihuianxin.xyaxf.modle.base.thrift.base.PayMethod;

import java.io.Serializable;
import java.util.List;


/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */

public class ECard  implements Serializable {
    public String student_no;  // optional
    public String student_name;  // optional

    public String card_no;  // optional
    public String id_card_no;  // optional
    public String card_balance;  // optional
    public String losscard_type;  // required
    public String consumption_query_type;  // required

    public List<PayMethod> pay_methods;  // required

    public List<String> recharge_amts;  // optional
    public String status;
    public String status_desc;

    public String charge_notice;

}