package com.yinhao.googleplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 * Created by yinhao on 2017/7/21.
 * 自定义Application，进行全局初始化处理
 */

public class GooglePlayApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        //获取主线程的id
        mainThreadId = Process.myTid();
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}
