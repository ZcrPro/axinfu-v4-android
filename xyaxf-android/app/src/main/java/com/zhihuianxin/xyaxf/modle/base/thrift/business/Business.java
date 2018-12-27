package com.zhihuianxin.xyaxf.modle.base.thrift.business;


import java.io.Serializable;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class Business  implements Serializable,Cloneable {

	public String no ;  // required
	public String title ;  // required
	public String name ;  // required
//	@BusinessStatus.business_status
	public String status ;  // required
//	@BusinessContainer.business_container
	public String container ;  // required
//	@BusinessType.business_type
	public String type ;  // required
	public String arg ;  // optional
	public String icon ;  // optional
	public String icon_gray ;  // optional

	@Override
	public Object clone() {
		Business obj = null;
		try{
			obj = (Business)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}