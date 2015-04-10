package com.nightlynexus.playingatmyhouse.punk.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.nightlynexus.playingatmyhouse.R;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class SettingsFragment extends AbsMenuPunkFragment {

    private CompoundButton mSwitch;
    private GifImageView mGifImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        mSwitch = (CompoundButton) rootView.findViewById(R.id.toggle);
        mGifImageView = (GifImageView) rootView.findViewById(R.id.gif);
        ((GifDrawable) mGifImageView.getDrawable()).stop();
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final GifDrawable gifDrawable = ((GifDrawable) mGifImageView.getDrawable());
                if (isChecked) {
                    gifDrawable.start();
                } else {
                    gifDrawable.stop();
                }
            }
        });
        return rootView;
    }
}
