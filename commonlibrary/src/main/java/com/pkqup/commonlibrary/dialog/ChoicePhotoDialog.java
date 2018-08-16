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

/**
 * @CreatedbBy: liucun on 2018/8/16
 * @Describe:
 */
public class ChoicePhotoDialog extends Dialog {

    private TextView tv_cancel;
    private TextView tv_take_photo;
    private TextView tv_photo_album;

    private OnCallBackListener onCallBackListener;

    public interface OnCallBackListener {
        void takePhoto();

        void toPhotoAlbum();
    }

    public void setCallBackListener(OnCallBackListener onCallBackListener) {
        this.onCallBackListener = onCallBackListener;
    }

    public ChoicePhotoDialog(@NonNull Context context) {
        this(context, R.style.BottomDialogTheme);
    }

    public ChoicePhotoDialog(@NonNull Context context, int themeResIds) {
        super(context, themeResIds);
        setContentView(R.layout.dialog_choice_photo);
        getWindow().getAttributes().gravity = Gravity.BOTTOM;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(p);

        tv_cancel = findViewById(R.id.tv_cancel);
        tv_take_photo = findViewById(R.id.tv_take_photo);
        tv_photo_album = findViewById(R.id.tv_photo_album);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onCallBackListener != null) {
                    onCallBackListener.takePhoto();
                }
            }
        });
        tv_photo_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onCallBackListener != null) {
                    onCallBackListener.toPhotoAlbum();
                }
            }
        });
    }
}
