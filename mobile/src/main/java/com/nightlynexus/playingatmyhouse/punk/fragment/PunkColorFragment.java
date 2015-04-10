package com.nightlynexus.playingatmyhouse.punk.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

public class PunkColorFragment extends PunkFragment {

    private static final String KEY_COLOR_BACKGROUND = "KEY_COLOR_BACKGROUND";

    private int mColorBackground;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            final Random random = new Random();
            mColorBackground = Color.rgb(
                    random.nextInt(256), random.nextInt(256), random.nextInt(256));
        } else {
            mColorBackground = savedInstanceState.getInt(KEY_COLOR_BACKGROUND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = super.onCreateView(inflater, container, savedInstanceState);
        rootView.setBackgroundColor(mColorBackground);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_COLOR_BACKGROUND, mColorBackground);
        super.onSaveInstanceState(outState);
    }
}
