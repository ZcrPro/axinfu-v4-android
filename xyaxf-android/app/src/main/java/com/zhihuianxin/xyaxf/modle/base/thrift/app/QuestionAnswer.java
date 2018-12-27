package com.zhihuianxin.xyaxf.modle.base.thrift.app;

import java.io.Serializable;

/**
 * Created by Vincent on 2016/11/11.
 */

public class QuestionAnswer  implements Serializable {

    public String id;
    public String type;
    public String question;
    public String content;
    public String create_time;
}
