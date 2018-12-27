package com.zhihuianxin.xyaxf.modle.base.thrift.ecard;


import com.zhihuianxin.xyaxf.modle.base.thrift.business.AccountVerifyItem;

import java.io.Serializable;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */
public class ECardAccount  implements Serializable,Cloneable {

    public String status;
    public String name;

    public String account_no;
    public String type;  // optional
    public AccountVerifyItem account_no_verify_config;
    public Boolean need_password;

    public class EcardStatus {
        public static final String OK = "OK";
        public static final String ReportLoss = "ReportLoss";
        public static final String Error = "Error";
    }

    @Override
    public Object clone() {
        ECardAccount obj = new ECardAccount();
        try{
            //obj = (ECardAccount)super.clone();
            obj.account_no_verify_config = (AccountVerifyItem) account_no_verify_config.clone();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}