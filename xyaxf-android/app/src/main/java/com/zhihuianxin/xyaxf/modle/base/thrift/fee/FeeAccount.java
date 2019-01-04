package com.zhihuianxin.xyaxf.modle.base.thrift.fee;

import com.raizlabs.android.dbflow.annotation.Table;
import com.zhihuianxin.xyaxf.constant.DBFlowDataBase;
import com.zhihuianxin.xyaxf.modle.base.thrift.business.AccountVerifyItem;

import java.io.Serializable;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class FeeAccount  implements Serializable {
    public String status;  // required
    public String name;  // optional
    public String student_no;  // optional
    public String new_student_no;  // optional
    public String id_card_no;  // optional
    public String other_no;  // optional
    public AccountVerifyItem new_stu_verify_config; //新生缴费记录
    public boolean support_required_fee;
    public boolean collect_bankcard;
    public boolean id_card_verified;
    public String open_fee_account_hint; 	// 开通缴费功能提示信息, 仅当status不为OK时有效
    public String new_student_fee_hint;	// 新生缴费提示信息, 仅当status不为OK时有效

}