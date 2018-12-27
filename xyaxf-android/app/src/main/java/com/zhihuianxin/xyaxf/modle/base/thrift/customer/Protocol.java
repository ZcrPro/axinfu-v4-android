package com.zhihuianxin.xyaxf.modle.base.thrift.customer;

import java.io.Serializable;

/**
 * Created by zcrpro on 2018/1/17.
 */

public class Protocol  implements Serializable {

    public String serial_no;            // 协议唯一标识
    public String protocol_no;            // 协议编号
    public String name;                    // 协议名称
    public String content;                // 协议内容
}
