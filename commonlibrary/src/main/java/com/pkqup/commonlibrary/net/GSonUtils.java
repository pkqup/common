package com.pkqup.commonlibrary.net;

import com.google.gson.Gson;


public class GSonUtils {
    private static Gson mInstance;

    public static Gson getmInstance() {
        if (mInstance == null) {
            synchronized (GSonUtils.class) {
                if (mInstance == null) {
                    mInstance = new Gson();
                }
            }
        }

        return mInstance;
    }
}
