package com.zhihuianxin.xyaxf.modle.base.thrift.ecard;

public enum ECardAccessModel {
	unsupported(),
	normal(),
	password(),

	;

	static{
		for(ECardAccessModel e: values()){
			if(!e.manual && e.ordinal() > 0){
				e.code = values()[e.ordinal() - 1].code + 1;
			}
		}
	}

	private int code = 0;
	private boolean manual = false;
	private ECardAccessModel(int code){
		this.code = code;
		this.manual = true;
	}

	private ECardAccessModel(){
	}

	public int getCode(){
		return code;
	}
}
