package com.zhihuianxin.xyaxf.modle.base.thrift.base;

import com.zhihuianxin.xyaxf.modle.base.thrift.app.Appendix;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vincent on 2016/11/10.
 */

public class Feedback  implements Serializable {
    public String id;
    public String question;
    public String date;
    public String answer;
    public List<Appendix> appendices;
}
