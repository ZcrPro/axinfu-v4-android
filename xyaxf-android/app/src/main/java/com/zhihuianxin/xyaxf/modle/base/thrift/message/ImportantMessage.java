package com.zhihuianxin.xyaxf.modle.base.thrift.message;

import java.io.Serializable;

/**
 * Created by Vincent on 2016/11/15.
 */

public class ImportantMessage  implements Serializable {

    public String id;
    public String title;
    public String content;
    public String time;
    public String timestamp;
    public String action;
}
