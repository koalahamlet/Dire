package com.nightlynexus.dire.activity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.nightlynexus.dire.R;
import com.nightlynexus.dire.fragment.AbsDireFragment;

abstract class AbsDireActivity extends ActionBarActivity {

    private static final String TAG = AbsDireActivity.class.getSimpleName();
    private static final String EXTRA_IS_TOOLBAR_ELEVATED = "EXTRA_IS_TOOLBAR_ELEVATED";
    private static final boolean USING_TOOLBAR_ELEVATION
            = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

    @IdRes
    private static final int CONTAINER_RES_ID = R.id.container;

    protected abstract AbsDireFragment getFirstFragment();

    @LayoutRes
    abstract int getContentViewResourceLayout();

    abstract void setUpViewsAndStates(Bundle savedInstanceState);

    abstract void setHomeIndicator(boolean isRoot);

    private Toolbar mToolbar;
    private FrameLayout mContainer;
    private float mToolbarElevation;
    private Drawable mContainerForeground;
    private boolean mIsToolbarElevated;

    protected final Toolbar getToolbar() {
        return mToolbar;
    }

    @SuppressLint("NewApi")
    public final void setToolbarElevated(boolean elevated) {
        if (mIsToolbarElevated == elevated) {
            return;
        }
        mIsToolbarElevated = elevated;
        if (elevated) {
            if (USING_TOOLBAR_ELEVATION) {
                mToolbar.setElevation(mToolbarElevation);
            } else {
                mContainer.setForeground(mContainerForeground);
            }
        } else {
            if (USING_TOOLBAR_ELEVATION) {
                mToolbar.setElevation(0);
            } else {
                mContainer.setForeground(null);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResourceLayout());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mContainer = (FrameLayout) findViewById(CONTAINER_RES_ID);
        if (savedInstanceState == null) {
            mIsToolbarElevated = true;
            if (USING_TOOLBAR_ELEVATION) {
                mToolbarElevation = mToolbar.getElevation();
                mContainerForeground = null;
            } else {
                mToolbarElevation = 0f;
                mContainerForeground = mContainer.getForeground();
            }
        } else {
            setToolbarElevated(savedInstanceState.getBoolean(EXTRA_IS_TOOLBAR_ELEVATED, true));
        }
        setSupportActionBar(mToolbar);
        setUpViewsAndStates(savedInstanceState);
        if (savedInstanceState == null) {
            final AbsDireFragment fragmentFirst = getFirstFragment();
            if (fragmentFirst != null) {
                launchFragment(fragmentFirst);
            }
        } else {
            setTitle();
        }
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        setTitle();
                    }
                }
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EXTRA_IS_TOOLBAR_ELEVATED, mIsToolbarElevated);
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("CommitTransaction")
    protected final void launchFragment(AbsDireFragment fragment) {
        final FragmentTransaction fragmentTransaction
                = getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        final AbsDireFragment fragmentOld = findCurrentFragment();
        if (fragmentOld != null) {
            fragmentTransaction
                    .remove(fragmentOld);
        }
        fragmentTransaction
                .add(CONTAINER_RES_ID, fragment)
                .commit();
    }

    public final boolean popTopFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return false;
    }

    private void setTitle() {
        final int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        final CharSequence title = backStackEntryCount == 0
                ? getActivityLabel()
                : findCurrentFragment().getTitle();
        setTitle(title);
        setHomeIndicator(backStackEntryCount <= 1);
    }

    private AbsDireFragment findCurrentFragment() {
        return (AbsDireFragment) getSupportFragmentManager()
                .findFragmentById(CONTAINER_RES_ID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                doOnBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        doOnBackPressed();
    }

    final void doOnBackPressed() {
        if (!popTopFragment()) {
            finish();
        }
    }

    private CharSequence getActivityLabel() {
        try {
            return getText(getPackageManager().getActivityInfo(getComponentName(), 0).labelRes);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "Set a string resource label for "
                    + getClass().getSimpleName() + " in AndroidManifest.xml");
            return "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
