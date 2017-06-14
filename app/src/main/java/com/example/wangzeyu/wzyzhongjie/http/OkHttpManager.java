package com.example.wangzeyu.wzyzhongjie.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangzeyu on 2017/6/10.
 */

public class OkHttpManager {

    private static final class OKHttpManagerHolder {
        private static final OkHttpManager INSTANCE = new OkHttpManager();
    }

    private static final String TAG = OkHttpManager.class.getSimpleName();
    private static final long TIME_CONNECTION = 5 * 1000L;
    private static final long READ_CONNECTION = 10 * 1000L;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private OkHttpClient mOkHttpClient;
    private OkHttpClient.Builder mOkHttpBuilder;

    private OkHttpManager() {
        mOkHttpBuilder = new OkHttpClient.Builder();
    }

    public static OkHttpManager getInstance() {
        return OKHttpManagerHolder.INSTANCE;
    }

    public void init() {
        mOkHttpBuilder.connectTimeout(TIME_CONNECTION, TimeUnit.MILLISECONDS);
        mOkHttpBuilder.readTimeout(READ_CONNECTION, TimeUnit.MILLISECONDS);
        mOkHttpClient = mOkHttpBuilder.build();
    }

    public void post(final String api, final Map<String, String> params, final OnHttpCallback callback) {
        ExecutorsUtils.execute(new Runnable() {
            @Override
            public void run() {
                postOnThread(api, params, callback);
            }
        });
    }

    private void postOnThread(String api, Map<String, String> params, final OnHttpCallback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        if (null != params) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        final Request request = new Request.Builder().url(api).post(builder.build()).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    handleResultSuccess(response, callback);
                } else {
                    handleResultFail(response, callback);
                }
            }
        });
    }

    private void handleResultSuccess(Response response, final OnHttpCallback callback) throws IOException {
        final String responseStr = response.body().string();
        Log.i(TAG, "請求成功，數據：" + responseStr);
        if (null != callback) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onSuccess(responseStr);
                }
            });
        }
    }

    private void handleResultFail(final Response response, final OnHttpCallback callback) {
        final String failMessage = "請求失敗，錯誤碼：" + response.code() + "\t錯誤信息:" + response.message();
        Log.w(TAG, failMessage);
        if (null != callback) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onError(new Throwable(failMessage));
                }
            });
        }
    }


}
