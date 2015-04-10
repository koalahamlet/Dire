package com.nightlynexus.playingatmyhouse.daft.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nightlynexus.playingatmyhouse.R;
import com.nightlynexus.playingatmyhouse.daft.activity.DaftActivity;

public class DaftFragment extends AbsMenuDaftFragment {

    public static final String ARG_NUM_IN_BACK_STACK = "ARG_NUM_IN_BACK_STACK";
    public static final String ARG_CHECK= "ARG_CHECK";

    private TextView mTextView;
    private CheckBox mCheckBox;
    private View mButtonBack;
    private View mButtonForward;
    private ListView mListView;
    private ListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_daft, container, false);
        mTextView = (TextView) rootView.findViewById(R.id.text);
        mCheckBox = (CheckBox) rootView.findViewById(R.id.check_box);
        mButtonBack = rootView.findViewById(R.id.button_back);
        mButtonForward = rootView.findViewById(R.id.button_forward);
        mListView = (ListView) rootView.findViewById(R.id.list);
        final Context context = inflater.getContext();
        final int numInBackStack = getArguments().getInt(ARG_NUM_IN_BACK_STACK, 1);
        mTextView.setText(String.valueOf(numInBackStack));
        final boolean checked = getArguments().getBoolean(ARG_CHECK, false);
        mCheckBox.setChecked(checked);
        final boolean isRoot = numInBackStack == 1;
        mButtonBack.setEnabled(!isRoot);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDaftActivity().popTopFragment();
            }
        });
        mButtonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDaftActivity().launchDaftFragment();
            }
        });
        final int len = 10000;
        final Integer[] ints = new Integer[len];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = i;
        }
        mAdapter = new ArrayAdapter<Integer>(context,
                android.R.layout.simple_list_item_1, ints);
        mListView.setAdapter(mAdapter);
        return rootView;
    }

    private DaftActivity getDaftActivity() {
        return (DaftActivity) getActivity();
    }
}
