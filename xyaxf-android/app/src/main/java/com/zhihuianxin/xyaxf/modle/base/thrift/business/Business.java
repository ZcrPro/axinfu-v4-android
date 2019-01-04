package com.zhihuianxin.xyaxf.modle.base.thrift.business;


import com.raizlabs.android.dbflow.annotation.Table;
import com.zhihuianxin.xyaxf.constant.DBFlowDataBase;

import java.io.Serializable;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class Business implements Serializable {
	public String no ;  // required
	public String title ;  // required
	public String name ;  // required
	public String status ;  // required
	public String container ;  // required
	public String type ;  // required
	public String arg ;  // optional
	public String icon ;  // optional
	public String icon_gray ;  // optional
}