package com.zhihuianxin.xyaxf.modle.base.thrift.ecard;


import com.raizlabs.android.dbflow.annotation.Table;
import com.zhihuianxin.xyaxf.constant.DBFlowDataBase;
import com.zhihuianxin.xyaxf.modle.base.thrift.business.AccountVerifyItem;

import java.io.Serializable;

/**
 * AUTO-GENERATE FILE, DO NOT MODIFY
 */

public class ECardAccount  implements Serializable {

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
}