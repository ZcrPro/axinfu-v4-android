package com.zhihuianxin.xyaxf.modle.base.thrift.message;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by Vincent on 2016/11/15.
 */

public class ImportantMessage  {

    public String id;
    public String title;
    public String content;
    public String time;
    public String timestamp;
    public String action;
}
