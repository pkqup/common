package com.pkqup.commonlibrary.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkqup.commonlibrary.R;


/**
 * Created by xdp on 2016/8/21.
 */
public class PermissionDialog extends DialogFragment {

    protected FragmentActivity mFragmentActivity;
    protected String mContentMessage;
    protected Dialog mDialog;
    protected DialogInterface.OnDismissListener mDismissListener;

    private TextView mLeftButton;
    private TextView mRightButton;
    private TextView mContentTv;
    private View.OnClickListener mClickListener;

    private String mLeftButtonText;
    private String mRightButtonText;
    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    public void setLeftClickListener(View.OnClickListener listener) {
        mLeftClickListener = listener;
    }

    public void setRightClickListener(View.OnClickListener listener) {
        mRightClickListener = listener;
    }

    /**
     * 设置标题
     * **/
    public void setContentMessage(String mContentStr) {
        this.mContentMessage = mContentStr;
    }

    public void setLeftButtonMessage(String mLeftButtonMessage) {
        this.mLeftButtonText = mLeftButtonMessage;
    }

    public void setRightButtonMessage(String mRightButtonMessage) {
        this.mRightButtonText = mRightButtonMessage;
    }

    public PermissionDialog() {

    }

    public void showStyleDialog(FragmentActivity fragmentActivity) {
        if(fragmentActivity == null) {
            return;
        }
        mFragmentActivity = fragmentActivity;
        FragmentManager dialogFm = fragmentActivity.getSupportFragmentManager();
        showCheckInDialog(dialogFm, "dialogFragment");
    }

    public void showCheckInDialog(FragmentManager manager, String tag) {
        //这里直接调用show方法会报java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        //因为show方法中是通过commit进行的提交(通过查看源码)
        //这里为了修复这个问题，使用commitAllowingStateLoss()方法
        //注意：DialogFragment是继承自android.app.Fragment，这里要注意同v4包中的Fragment区分，别调用串了
        //DialogFragment有自己的好处，可能也会带来别的问题
        //dialog.show(getFragmentManager(), "SignDialog");
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.permission_dialog, container, false);
        return dialogView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLeftButton = (TextView) view.findViewById(R.id.dialog_button_left);
        mRightButton = (TextView) view.findViewById(R.id.dialog_button_right);
        mContentTv = (TextView) view.findViewById(R.id.dialog_content);
        setCancelable(false);
        initViewText();

        mLeftButton.setOnClickListener(mClickListener);
        mRightButton.setOnClickListener(mClickListener);
    }

    private void initViewText() {
        if (!TextUtils.isEmpty(mContentMessage)) {
            mContentTv.setText(mContentMessage);
        }
        if (!TextUtils.isEmpty(mLeftButtonText)) {
            mLeftButton.setText(mLeftButtonText);
        }
        if (!TextUtils.isEmpty(mRightButtonText)) {
            mRightButton.setText(mRightButtonText);
        }
    }

    private void createListener() {
        mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vId = v.getId();
                if (vId == R.id.dialog_button_left) {

                    if (null != mLeftClickListener) {
                        mLeftClickListener.onClick(v);

                    } else {
                        dismiss();
                    }

                } else if (vId == R.id.dialog_button_right) {

                    if (null != mRightClickListener) {
                        mRightClickListener.onClick(v);

                    } else {
                        dismiss();
                    }
                }
            }
        };
    }


}
