package com.pkqup.commonlibrary.abase;

import android.support.multidex.MultiDexApplication;

import com.pkqup.commonlibrary.crash.CrashHandler;
import com.pkqup.commonlibrary.util.AppUtils;
import com.socks.library.KLog;

public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        CrashHandler.getInstance().init(this);
        KLog.init(AppUtils.checkInDebug(this));
    }

}
