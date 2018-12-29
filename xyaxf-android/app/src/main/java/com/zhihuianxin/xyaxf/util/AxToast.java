package com.zhihuianxin.xyaxf.util;

import android.content.Context;
import android.widget.Toast;

public class AxToast {

    /**
     * 打印解析数据失败的提示
     * @param context
     * @param content
     */
    public static void showHttpErr(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }
}
