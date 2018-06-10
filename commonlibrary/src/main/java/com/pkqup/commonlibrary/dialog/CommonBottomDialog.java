package com.pkqup.commonlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.pkqup.commonlibrary.R;

public class CommonBottomDialog extends Dialog {

    private TextView tv_cancel;

    private CommonBottomDialog.OnCallBackListener onCallBackListener;

    public interface OnCallBackListener {
        void onItemClick(int position);
    }

    public void setCallBackListener(CommonBottomDialog.OnCallBackListener onCallBackListener) {
        this.onCallBackListener = onCallBackListener;
    }

    public CommonBottomDialog(@NonNull Context context) {
        this(context, R.style.BottomDialogTheme);
    }

    public CommonBottomDialog(@NonNull Context context, int themeResIds) {
        super(context, themeResIds);
        setContentView(R.layout.common_bottom_dialog);
        getWindow().getAttributes().gravity = Gravity.BOTTOM;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(p);

        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
