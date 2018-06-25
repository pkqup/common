package com.pkqup.commonlibrary.util;

import android.view.Gravity;
import android.widget.Toast;

/**
 * @CreatedbBy: liucun on 2018/6/24.
 * @Describe:
 */
public class ToastUtils {

    private static Toast toast;

    public static void showShort(String msg) {
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
