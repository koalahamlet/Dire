package com.nightlynexus.playingatmyhouse.daft.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nightlynexus.dire.fragment.AbsDireFragment;
import com.nightlynexus.playingatmyhouse.R;
import com.nightlynexus.playingatmyhouse.punk.activity.PunkActivity;

public abstract class AbsMenuDaftFragment extends AbsDireFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.daft, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_launch_punk:
                final Intent intentPunk = new Intent(getActivity(), PunkActivity.class);
                intentPunk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentPunk);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
