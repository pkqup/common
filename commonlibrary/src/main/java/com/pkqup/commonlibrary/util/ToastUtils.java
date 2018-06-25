package com.pkqup.commonlibrary.util;

import android.view.Gravity;
import android.widget.Toast;

/**
 * @CreatedbBy: liucun on 2018/6/24.
 * @Describe:
 */
public class ToastUtils {

    private static Toast toast;//实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长

    public static void showShortToast(String msg) {
        if (AppUtils.getContext() != null) {
            if (toast == null) {
                toast = Toast.makeText(AppUtils.getContext(), msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }
}
