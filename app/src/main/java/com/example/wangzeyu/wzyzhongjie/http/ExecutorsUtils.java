package com.example.wangzeyu.wzyzhongjie.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangzeyu on 2017/6/10.
 */

public class ExecutorsUtils {

    private static final int SIZE_THREAD = 4;
    private static final ExecutorService mExecutorService = Executors.newFixedThreadPool(SIZE_THREAD);

    public static void execute(Runnable runnable){
        mExecutorService.execute(runnable);
    }
}
