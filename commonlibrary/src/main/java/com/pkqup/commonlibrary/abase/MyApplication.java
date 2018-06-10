package com.pkqup.commonlibrary.abase;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.multidex.MultiDexApplication;

import com.pkqup.commonlibrary.crash.CrashHandler;
import com.socks.library.KLog;

/**
 * Created by LiuCun on 2017/11/24.<br>
 * Describe 继承MultiDexApplication，解决方法数超过65535编译失败问题
 */
public class MyApplication extends MultiDexApplication {

    public static Context appContext;
    public static boolean isDebug;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        isDebug = checkInDebug(appContext);

        //初始化KLog,如果是debug模式，不打印
        KLog.init(isDebug);
        CrashHandler.getInstance().init(this);
    }

    //判断是否是debug模式
    public static boolean checkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static Context getContext() {
        return appContext;
    }
}
