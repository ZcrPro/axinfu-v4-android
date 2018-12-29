package com.zhihuianxin.xyaxf.modle.base.thrift.customer;

import com.zhihuianxin.xyaxf.modle.base.thrift.business.Business;
import com.zhihuianxin.xyaxf.modle.base.thrift.ecard.ECardAccount;
import com.zhihuianxin.xyaxf.modle.base.thrift.fee.FeeAccount;
import com.zhihuianxin.xyaxf.modle.base.thrift.resource.School;

import java.io.Serializable;
import java.util.List;


/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */

public class Customer implements Serializable {

	public String mobile;
	public CustomerBaseInfo base_info ;  // required
	public School school ;  // optional
	public ECardAccount ecard_account ;  // optional
	public FeeAccount fee_account ;  // optional
	public List<Business> businesses;
	public boolean is_could_cancel;
	public boolean is_show_bind_card_guide =false;// 是否显示绑卡引导
	public String bind_card_short_hint;		// 绑卡引导提示 短
	public String bind_card_long_hint;		// 绑卡引导提示 长
	public StudentInfo student;
	public boolean is_show_scan_pay_in_center;

}