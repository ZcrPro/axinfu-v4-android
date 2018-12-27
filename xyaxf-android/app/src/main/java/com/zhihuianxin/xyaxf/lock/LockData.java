//package com.zhihuianxin.xyaxf.lock;
//
//import com.raizlabs.android.dbflow.annotation.Column;
//import com.raizlabs.android.dbflow.annotation.PrimaryKey;
//import com.raizlabs.android.dbflow.annotation.Table;
//import com.raizlabs.android.dbflow.structure.BaseModel;
//import com.zhihuianxin.xyaxf.constant.DBFlowDataBase;
//
//
//@Table(database = DBFlowDataBase.class) //上面自己创建的类（定义表的名称 版本）
//
//public class LockData extends BaseModel {
//
//     //主键
//    public String regist_serial; //用户唯一标识
//    @Column
//    public String gesture_password; //手势密码
//    @Column
//    public boolean gesture_status; //手势密码是否打开
//    @Column
//    public boolean finger_status;  //指纹密码是否打开
//    @Column
//    public boolean later_status;  //稍后设置
//    @Column
//    public boolean remind_status; //不再提醒
//    @Column
//    public int gesture_er_times; //手势输入错误的次数
//    @Column
//    public boolean lock_status; //是否已经解开了锁
//
//}
