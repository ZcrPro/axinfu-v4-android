package com.zhihuianxin.xyaxf.modle.base.thrift.customer;

import java.io.Serializable;
import java.util.List;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class CustomerBaseInfo  implements Serializable,Cloneable {

	public String mobile ;  // required
	public String nickname ;  // optional
	public String gender;  // optional
	public String avatar ;  // optional
	public String name ;  // optional
	public String regist_serial ;  // optional
	public List<Protocol> protocol;

	@Override
	public Object clone() {
		CustomerBaseInfo obj = null;
		try{
			obj = (CustomerBaseInfo)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}