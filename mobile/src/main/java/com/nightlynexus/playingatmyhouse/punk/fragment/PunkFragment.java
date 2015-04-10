package com.nightlynexus.playingatmyhouse.punk.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nightlynexus.playingatmyhouse.R;
import com.nightlynexus.playingatmyhouse.punk.activity.PunkActivity;

public class PunkFragment extends AbsMenuPunkFragment {

    private View mButtonHarder;
    private View mButtonFaster;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_punk, container, false);
        mButtonHarder = rootView.findViewById(R.id.button_harder);
        mButtonFaster = rootView.findViewById(R.id.button_faster);
        mButtonHarder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPunkActivity().launchHarderFragment();
            }
        });
        mButtonFaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPunkActivity().launchFasterFragment();
            }
        });
        return rootView;
    }

    private PunkActivity getPunkActivity() {
        return (PunkActivity) getActivity();
    }
}
