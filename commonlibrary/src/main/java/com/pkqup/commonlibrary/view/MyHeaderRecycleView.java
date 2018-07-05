package com.pkqup.commonlibrary.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.lzy.widget.HeaderScrollHelper;

public class MyHeaderRecycleView extends RecyclerView implements  HeaderScrollHelper.ScrollableContainer {


    public MyHeaderRecycleView(Context context) {
        super(context);
    }

    public MyHeaderRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHeaderRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public View getScrollableView() {
        return this;
    }
}