package com.nightlynexus.playingatmyhouse.punk.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nightlynexus.playingatmyhouse.R;

public class FasterFragment extends AbsMenuPunkFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_faster, container, false);
    }
}
