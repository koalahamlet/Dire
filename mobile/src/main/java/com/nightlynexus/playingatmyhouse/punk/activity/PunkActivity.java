package com.nightlynexus.playingatmyhouse.punk.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nightlynexus.dire.activity.AbsDireDrawerActivity;
import com.nightlynexus.dire.fragment.AbsDireFragment;
import com.nightlynexus.playingatmyhouse.R;
import com.nightlynexus.playingatmyhouse.punk.adapter.NavigationDrawerAdapter;
import com.nightlynexus.playingatmyhouse.punk.fragment.FasterFragment;
import com.nightlynexus.playingatmyhouse.punk.fragment.HarderFragment;
import com.nightlynexus.playingatmyhouse.punk.fragment.PunkColorFragment;
import com.nightlynexus.playingatmyhouse.punk.fragment.PunkFragment;
import com.nightlynexus.playingatmyhouse.punk.fragment.SettingsFragment;
import com.nightlynexus.playingatmyhouse.punk.model.Picture;
import com.nightlynexus.playingatmyhouse.punk.util.PictureFactory;

import java.util.Random;

public class PunkActivity extends AbsDireDrawerActivity {

    private static final String KEY_DRAWER_ADAPTER_POSITION_CHECKED
            = "KEY_DRAWER_ADAPTER_POSITION_CHECKED";

    private static final int ADAPTER_POSITION_INVALID = -1;

    private RecyclerView mNavigationDrawerView;
    private LinearLayoutManager mLayoutManager;
    private NavigationDrawerAdapter mAdapter;
    private Picture[] mPictures;
    private int mAdapterPositionChecked;
    private final NavigationDrawerAdapter.OnPictureClickListener mPictureClickListener
            = new NavigationDrawerAdapter.OnPictureClickListener() {
        @Override
        public void onPictureClick(int adapterPosition) {
            if (setDrawerPositionChecked(adapterPosition)) {
                final AbsDireFragment fragment;
                switch (adapterPosition) {
                    case 0:
                        fragment = getPunkFragment();
                        break;
                    case 1:
                        fragment = getPunkColorFragment();
                        break;
                    case 2:
                        fragment = getPunkColorFragment();
                        break;
                    case 3:
                        fragment = getSettingsFragment();
                        break;
                    default:
                        throw new IllegalStateException("Unimplemented adapter position: "
                                + adapterPosition);
                }
                changeContentFragment(fragment);
            } else {
                popToContentFragment();
            }
            closeDrawerNavigation();
        }
    };

    @Override
    @NonNull
    protected final NavigationDrawerToggle getNavigationDrawerToggle() {
        return new NavigationDrawerToggle() {
            @Override
            public void onDrawerOpened() {
                // do nothing
            }

            @Override
            public void onDrawerClosed() {
                // do nothing
            }
        };
    }

    @Override
    protected final int getStatusBarNavigationDrawerColor() {
        return getResources().getColor(R.color.status_bar_navigation_drawer_punk);
    }

    @Override
    protected final View getNavigationDrawerView(ViewGroup parent) {
        mNavigationDrawerView = (RecyclerView) LayoutInflater.from(this)
                .inflate(R.layout.navigation_drawer_punk, parent, false);
        mLayoutManager = new LinearLayoutManager(this);
        mNavigationDrawerView.setLayoutManager(mLayoutManager);
        mPictures = PictureFactory.getPictures();
        mAdapter = new NavigationDrawerAdapter(mPictures, mPictureClickListener);
        mNavigationDrawerView.setAdapter(mAdapter);
        mNavigationDrawerView.setHasFixedSize(false);
        return mNavigationDrawerView;
    }

    @Override
    protected final AbsDireFragment getFirstFragment() {
        return getPunkFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapterPositionChecked = ADAPTER_POSITION_INVALID;
        final int adapterPositionChecked;
        if (savedInstanceState == null) {
            adapterPositionChecked = 0; // corresponds to getFirstFragment()
        } else {
            adapterPositionChecked = savedInstanceState.getInt(
                    KEY_DRAWER_ADAPTER_POSITION_CHECKED, ADAPTER_POSITION_INVALID);
            if (adapterPositionChecked == ADAPTER_POSITION_INVALID) {
                throw new RuntimeException("Invalid adapter position value for drawer key");
            }
        }
        setDrawerPositionChecked(adapterPositionChecked);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_DRAWER_ADAPTER_POSITION_CHECKED, mAdapterPositionChecked);
        super.onSaveInstanceState(outState);
    }

    private boolean setDrawerPositionChecked(int adapterPosition) {
        if (mAdapterPositionChecked != adapterPosition) {
            if (mAdapterPositionChecked != ADAPTER_POSITION_INVALID) {
                // not first run
                mPictures[mAdapterPositionChecked].setChecked(false);
                mAdapter.notifyItemChanged(mAdapterPositionChecked);
            }
            mAdapterPositionChecked = adapterPosition;
            mPictures[mAdapterPositionChecked].setChecked(true);
            mAdapter.notifyItemChanged(mAdapterPositionChecked);
            return true;
        } else {
            return false;
        }
    }

    public final void launchHarderFragment() {
        launchFragment(getHarderFragment());
    }

    public final void launchFasterFragment() {
        launchFragment(getFasterFragment());
    }

    private PunkFragment getPunkFragment() {
        final PunkFragment fragmentPunk = new PunkFragment();
        final Bundle bundlePunk = new Bundle();
        fragmentPunk.setArguments(bundlePunk, getText(R.string.title_punk));
        return fragmentPunk;
    }

    private PunkColorFragment getPunkColorFragment() {
        final PunkColorFragment fragmentPunkColor = new PunkColorFragment();
        final Bundle bundlePunkColor = new Bundle();
        final CharSequence[] titles = getResources().getTextArray(R.array.title_punk_color);
        fragmentPunkColor.setArguments(bundlePunkColor,
                titles[new Random().nextInt(titles.length)]);
        return fragmentPunkColor;
    }

    private SettingsFragment getSettingsFragment() {
        final SettingsFragment fragmentSettings = new SettingsFragment();
        final Bundle bundleSettings = new Bundle();
        fragmentSettings.setArguments(bundleSettings, getText(R.string.title_settings));
        return fragmentSettings;
    }

    private HarderFragment getHarderFragment() {
        final HarderFragment fragmentHarder = new HarderFragment();
        final Bundle bundleHarder = new Bundle();
        fragmentHarder.setArguments(bundleHarder, getText(R.string.title_harder));
        return fragmentHarder;
    }

    private FasterFragment getFasterFragment() {
        final FasterFragment fragmentFaster = new FasterFragment();
        final Bundle bundleFaster = new Bundle();
        fragmentFaster.setArguments(bundleFaster, getText(R.string.title_faster));
        return fragmentFaster;
    }
}
