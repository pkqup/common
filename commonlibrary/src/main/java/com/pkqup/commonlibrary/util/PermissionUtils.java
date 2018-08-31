package com.pkqup.commonlibrary.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.pkqup.commonlibrary.dialog.CommonConfirmDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;


/**
 * Created by macmini on 2017/11/7.
 * <p>
 * //STORAGE
 * Manifest.permission.READ_EXTERNAL_STORAGE,
 * Manifest.permission.WRITE_EXTERNAL_STORAGE,
 * <p>
 * //CAMERA
 * Manifest.permission.CAMERA,
 * <p>
 * //MICROPHONE
 * Manifest.permission.RECORD_AUDIO,
 * <p>
 * //LOCATION
 * Manifest.permission.ACCESS_FINE_LOCATION,
 * Manifest.permission.ACCESS_COARSE_LOCATION,
 * <p>
 * //PHONE
 * Manifest.permission.READ_PHONE_STATE
 */

public class PermissionUtils {

    public static boolean isRequestedPhone = false; //是否申请过系统设置权限

    public static boolean hasPermissionForLocation(final Context context) {
        // 申请多个权限。
        String[] permission = {
                //LOCATION
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        };

        return AndPermission.hasPermission(context, permission);
    }


    public static void log(final Context context, PermissionListener listener) {
        // 申请多个权限。
        String[] permission = {
                Manifest.permission.READ_LOGS,
        };

        requestPermission(context, listener, permission, "信任是美好的开始，请授权相关权限，让我们更好的为你服务", true);
    }

    public static void PermissionForStart(final Context context, PermissionListener listener) {
        // 申请多个权限。
        String[] permission = {
//                Manifest.permission.READ_LOGS,
                //PHONE
                Manifest.permission.READ_PHONE_STATE,

                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,

                //LOCATION
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        };

        requestPermission(context, listener, permission, "信任是美好的开始，请授权相关权限，让我们更好的为你服务", true);
    }

    public static boolean hasPermissionForAD(final Context context) {
        // 申请多个权限。
        String[] permission = {
                Manifest.permission.READ_PHONE_STATE,

                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        return AndPermission.hasPermission(context, permission);
    }

    public static void smsPermiss(final Context context, PermissionListener listener) {
        // 申请多个权限。
        String[] permission = {
                Manifest.permission.READ_SMS,
        };

        requestPermission(context, listener, permission, "信任是美好的开始，请授权相关权限，让我们更好的为你服务", true);
    }

    public static void Storage(final Context context, PermissionListener listener) {
        // 申请多个权限。
        String[] permission = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        requestPermission(context, listener, permission, "信任是美好的开始，请授权相关权限，让我们更好的为你服务", true);
    }

    public static void RecordAudio(final Context context, PermissionListener listener) {
        // 申请多个权限。
        String[] permission = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        requestPermission(context, listener, permission, "信任是美好的开始，请授权相关权限，让我们更好的为你服务", true);
    }

    public static void Camera(final Context context, PermissionListener listener) {
        // 申请多个权限。
        String[] permission = {
                Manifest.permission.CAMERA,
        };

        requestPermission(context, listener, permission, "信任是美好的开始，请授权相关权限，让我们更好的为你服务", true);
    }

    public static void CameraAndStorge(final Context context, PermissionListener listener) {
        // 申请多个权限。
        String[] permission = {
                Manifest.permission.CAMERA,

                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        requestPermission(context, listener, permission, "信任是美好的开始，请授权相关权限，让我们更好的为你服务", true);
    }


    private static void requestPermission(final Context context, PermissionListener listener, String[] permission, final String tips, final boolean needDialog) {
        if (null == context) return;
        AndPermission.with(context)
                .requestCode(1100)
                .permission(permission)
                .callback(listener)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
               /* .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
                        final CommonConfirmDialog commonConfirmDialog = new CommonConfirmDialog(context, "信任是美好的开始，请授权相关权限，让我们更好的为你服务");
                        commonConfirmDialog.setDialogStr("取消","去授权");
                        commonConfirmDialog.setCallBack(new CommonConfirmDialog.CallBack() {
                            @Override
                            public void onConfirm() {
                                commonConfirmDialog.dismiss();
                                rationale.resume();
                            }

                            @Override
                            public void onCancel() {
                                commonConfirmDialog.dismiss();
                            }
                        });
                        commonConfirmDialog.show();
                    }
                })*/
                .start();
    }
}
