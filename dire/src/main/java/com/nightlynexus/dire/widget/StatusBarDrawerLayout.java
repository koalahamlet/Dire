package com.nightlynexus.dire.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;

import com.nightlynexus.dire.R;

public class StatusBarDrawerLayout extends DrawerLayout {

    public StatusBarDrawerLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public StatusBarDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public StatusBarDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.StatusBarView, defStyle, 0);
        if (a == null) {
            return;
        }
        setStatusBarBackground(a.getDrawable(R.styleable.StatusBarView_statusBarColor));
        a.recycle();
    }

    @Override
    public final void setStatusBarBackground(Drawable bg) {
        super.setStatusBarBackground(bg);
    }
}
