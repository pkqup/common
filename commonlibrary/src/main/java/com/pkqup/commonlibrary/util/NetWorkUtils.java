package com.pkqup.commonlibrary.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtils {

    //未找到合适匹配网络类型
    public static final int TYPE_NO = 0;

    //中国移动CMNET网络(中国移动GPRS接入方式之一, 主要为PC、笔记本电脑、PDA设立)
    public static final int TYPE_MOBILE_CMNET = 1;

    //中国移动CMWAP网络(中国移动GPRS接入方式之一,主要为手机WAP上网而设立)
    public static final int TYPE_MOBILE_CMWAP = 2;

    //中国联通UNIWAP网络(中国联通划分GPRS接入方式之一, 主要为手机WAP上网而设立)
    public static final int TYPE_MOBILE_UNIWAP = 3;

    //中国联通3GWAP网络
    public static final int TYPE_MOBILE_3GWAP = 4;

    //中国联通3HNET网络
    public static final int TYPE_MOBLIE_3GNET = 5;

    //中国联通UNINET网络(中国联通划分GPRS接入方式之一, 主要为PC、笔记本电脑、PDA设立)
    public static final int TYPE_MOBILE_UNINET = 6;

    //中国电信CTWAP网络
    public static final int TYPE_MOBILE_CTWAP = 7;

    //中国电信CTNET网络
    public static final int TYPE_MOBILE_CTNET = 8;

    //WIFI网络
    public static final int TYPE_WIFI = 10;

    /**
     * 网络类型 - 无连接
     */
    public static final int NETWORK_TYPE_NO_CONNECTION = -1231545315;

    public static final String NETWORK_TYPE_WIFI = "wifi";
    public static final String NETWORK_TYPE_3G = "eg";
    public static final String NETWORK_TYPE_2G = "2g";
    public static final String NETWORK_TYPE_WAP = "wap";
    public static final String NETWORK_TYPE_UNKNOWN = "unknown";
    public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

    /**
     * 判断网络是否连接
     *
     * @param activity Activity
     * @return boolean 网络连接状态
     */
    public static boolean isNetworkConnected(Activity activity) {
        boolean falg = false;
        ConnectivityManager mConnectivityManager = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnectivityManager == null) {
            return falg;
        }
        NetworkInfo[] arrayOfNetworkInfo = mConnectivityManager.getAllNetworkInfo();
        if (arrayOfNetworkInfo != null) {
            for (int j = 0; j < arrayOfNetworkInfo.length; j++) {
                if (arrayOfNetworkInfo[j].getState() == NetworkInfo.State.CONNECTED) {
                    falg = true;
                    break;
                }
            }
        }
        return falg;
    }

    /**
     * 判断网络是否连接
     *
     * @return boolean 网络连接状态
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) AppUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        //获取连接对象
        if (mNetworkInfo != null) {
            //判断是TYPE_MOBILE网络
            if (ConnectivityManager.TYPE_MOBILE == mNetworkInfo.getType()) {
                //判断移动网络连接状态
                NetworkInfo.State STATE_MOBILE = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
                if (STATE_MOBILE == NetworkInfo.State.CONNECTED) {
                    return mNetworkInfo.isAvailable();
                }
            }
            //判断是TYPE_WIFI网络
            if (ConnectivityManager.TYPE_WIFI == mNetworkInfo.getType()) {
                //判断WIFI网络状态
                NetworkInfo.State STATE_WIFI = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                if (STATE_WIFI == NetworkInfo.State.CONNECTED) {
                    return mNetworkInfo.isAvailable();
                }
            }
        }
        return false;
    }


    /**
     * 获取当前手机连接的网络类型
     *
     * @param context 上下文
     * @return int 网络类型
     */
    public static int getNetworkState(Context context) {
        //获取ConnectivityManager对象
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获得当前网络信息
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            //获取网络类型
            int currentNetWork = networkInfo.getType();
            //手机网络类型
            if (currentNetWork == ConnectivityManager.TYPE_MOBILE) {
                if (networkInfo.getExtraInfo() != null) {
                    if (networkInfo.getExtraInfo().equals("cmnet")) {
                        return TYPE_MOBILE_CMNET;
                    }
                    if (networkInfo.getExtraInfo().equals("cmwap")) {
                        return TYPE_MOBILE_CMWAP;
                    }
                    if (networkInfo.getExtraInfo().equals("uniwap")) {
                        return TYPE_MOBILE_UNIWAP;
                    }
                    if (networkInfo.getExtraInfo().equals("3gwap")) {
                        return TYPE_MOBILE_3GWAP;
                    }
                    if (networkInfo.getExtraInfo().equals("3gnet")) {
                        return TYPE_MOBLIE_3GNET;
                    }
                    if (networkInfo.getExtraInfo().equals("uninet")) {
                        return TYPE_MOBILE_UNINET;
                    }
                    if (networkInfo.getExtraInfo().equals("ctwap")) {
                        return TYPE_MOBILE_UNINET;
                    }
                    if (networkInfo.getExtraInfo().equals("ctnet")) {
                        return TYPE_MOBILE_UNINET;
                    }
                }
                //WIFI网络类型
            } else if (currentNetWork == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }
        }
        return TYPE_NO;
    }


    /**
     * 打开网络设置界面
     *
     * @param activity Activity
     */
    public static void openNetSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
}
