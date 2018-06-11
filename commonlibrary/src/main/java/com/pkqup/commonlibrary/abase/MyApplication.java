package com.pkqup.commonlibrary.abase;

import android.support.multidex.MultiDexApplication;

import com.pkqup.commonlibrary.crash.CrashHandler;
import com.pkqup.commonlibrary.util.AppUtils;
import com.socks.library.KLog;

/**
 * Created by LiuCun on 2017/11/24.<br>
 * Describe 继承MultiDexApplication，解决方法数超过65535编译失败问题
 */
public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        CrashHandler.getInstance().init(this);
        KLog.init(AppUtils.checkInDebug(this));
    }

}
