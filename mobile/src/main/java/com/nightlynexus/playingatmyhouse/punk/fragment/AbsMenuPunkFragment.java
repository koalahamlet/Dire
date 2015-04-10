package com.nightlynexus.playingatmyhouse.punk.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nightlynexus.dire.fragment.AbsDireFragment;
import com.nightlynexus.playingatmyhouse.R;
import com.nightlynexus.playingatmyhouse.daft.activity.DaftActivity;

public abstract class AbsMenuPunkFragment extends AbsDireFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.punk, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_launch_daft:
                final Intent intentDaft = new Intent(getActivity(), DaftActivity.class);
                intentDaft.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentDaft);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
