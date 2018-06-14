package com.pkqup.commonlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pkqup.commonlibrary.R;

public class CommonLoadingDialog extends Dialog {

    private Context context;

    public CommonLoadingDialog(Context context) {
        super(context, R.style.dialog_transparent);
        this.context = context;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common_loading, null);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);
        setContentView(view);// 设置布局
    }
}
