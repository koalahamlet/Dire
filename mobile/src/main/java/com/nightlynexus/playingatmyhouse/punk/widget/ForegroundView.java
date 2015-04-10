package com.nightlynexus.playingatmyhouse.punk.widget;

import android.graphics.drawable.Drawable;

public interface ForegroundView {

    int getForegroundGravity();

    void setForegroundGravity(int foregroundGravity);

    void setForeground(Drawable drawable);

    Drawable getForeground();
}
