package com.zhihuianxin.xyaxf.modle.base.thrift.message;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.zhihuianxin.xyaxf.constant.DBFlowDataBase;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vincent on 2016/12/29.
 */

public class ImportantMessageWithUser implements Serializable {

    public String mobile;
    public List<ImportantMessage> importantMessages;
}
