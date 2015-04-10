package com.nightlynexus.dire.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.nightlynexus.dire.R;

public abstract class AbsDireStandardActivity extends AbsDireActivity {

    @Override
    @LayoutRes
    final int getContentViewResourceLayout() {
        return R.layout.activity_abs_title_normal;
    }

    @Override
    final void setUpViewsAndStates(Bundle savedInstanceState) {
        // do nothing
    }

    @Override
    final void setHomeIndicator(boolean isRoot) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(!isRoot);
    }
}
