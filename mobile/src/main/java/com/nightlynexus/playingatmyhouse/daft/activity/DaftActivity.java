package com.nightlynexus.playingatmyhouse.daft.activity;

import android.os.Bundle;

import com.nightlynexus.dire.activity.AbsDireStandardActivity;
import com.nightlynexus.dire.fragment.AbsDireFragment;
import com.nightlynexus.playingatmyhouse.R;
import com.nightlynexus.playingatmyhouse.daft.fragment.DaftFragment;

import java.util.Random;

public class DaftActivity extends AbsDireStandardActivity {

    @Override
    protected final AbsDireFragment getFirstFragment() {
        return getDaftFragment();
    }

    public final void launchDaftFragment() {
        launchFragment(getDaftFragment());
    }

    private DaftFragment getDaftFragment() {
        final DaftFragment fragmentDaft = new DaftFragment();
        final Bundle bundleDaft = new Bundle();
        bundleDaft.putInt(DaftFragment.ARG_NUM_IN_BACK_STACK,
                getSupportFragmentManager().getBackStackEntryCount() + 1);
        bundleDaft.putBoolean(DaftFragment.ARG_CHECK, new Random().nextBoolean());
        fragmentDaft.setArguments(bundleDaft, getText(R.string.title_daft));
        return fragmentDaft;
    }
}
