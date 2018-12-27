package com.zhihuianxin.xyaxf.modle.base.thrift.resource;

import java.io.Serializable;

/**
 * Created by Vincent on 2016/11/12.
 */

public class UploadFileAccess  implements Serializable {
    public String uptoken;
    public String key;
    public String access_url;
}
