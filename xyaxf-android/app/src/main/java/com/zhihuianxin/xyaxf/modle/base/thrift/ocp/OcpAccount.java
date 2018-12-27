package com.zhihuianxin.xyaxf.modle.base.thrift.ocp;

import com.zhihuianxin.xyaxf.modle.base.thrift.base.BaseResponse;

import java.io.Serializable;

/**
 * Created by zcrpro on 2017/11/16.
 */

public class OcpAccount implements Serializable {
    public BaseResponse resp;
    public AxfQRPayAccount account;

    @Override
    public String toString() {
        return "OcpAccount{" +
                "resp=" + resp +
                ", axfQRPayAccount=" + account +
                '}';
    }
}