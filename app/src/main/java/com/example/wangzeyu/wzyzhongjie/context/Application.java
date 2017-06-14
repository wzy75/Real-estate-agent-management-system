package com.example.wangzeyu.wzyzhongjie.context;

import com.example.wangzeyu.wzyzhongjie.http.OkHttpManager;

/**
 * Created by wangzeyu on 2017/6/10.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        OkHttpManager.getInstance().init();
    }
}
