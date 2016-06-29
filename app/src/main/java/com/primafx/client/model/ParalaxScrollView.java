package com.primafx.client.model;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by user on 6/24/2016.
 */
public class ParalaxScrollView  extends ScrollView {

    public interface OnScrollChangedListener {
        public void onScrollChanged(int deltaX, int deltaY);
    }

    private OnScrollChangedListener mOnScrollChangedListener;

    public ParalaxScrollView(Context context) {
        super(context);
    }

    public ParalaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParalaxScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(l - oldl, t - oldt);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }
}
