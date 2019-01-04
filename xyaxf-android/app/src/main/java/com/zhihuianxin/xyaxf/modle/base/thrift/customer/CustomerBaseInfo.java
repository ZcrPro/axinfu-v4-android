package com.zhihuianxin.xyaxf.modle.base.thrift.customer;

import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.zhihuianxin.xyaxf.constant.DBFlowDataBase;

import java.io.Serializable;
import java.util.List;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class CustomerBaseInfo  implements Serializable {

	public String mobile ;  // required
	public String nickname ;  // optional
	public String gender;  // optional
	public String avatar ;  // optional
	public String name ;  // optional
	public String regist_serial ;  // optional
	public List<Protocol> protocol;
}