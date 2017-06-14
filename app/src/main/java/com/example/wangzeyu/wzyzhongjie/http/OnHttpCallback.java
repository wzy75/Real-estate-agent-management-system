package com.example.wangzeyu.wzyzhongjie.http;

import java.sql.ResultSet;

/**
 * Created by wangzeyu on 2017/6/10.
 */

public interface OnHttpCallback {

    void onSuccess(String result);

    void onError(Throwable e);
}
