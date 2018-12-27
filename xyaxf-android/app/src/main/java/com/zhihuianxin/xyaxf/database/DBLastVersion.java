package com.zhihuianxin.xyaxf.database;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.zhihuianxin.xyaxf.constant.DBFlowDataBase;

/**
 * 目前的app版本
 */

@Table(database = DBFlowDataBase.class)
public class DBLastVersion extends BaseModel {
    @PrimaryKey
    public long version_code;
    @Column
    public String version_name;
}
