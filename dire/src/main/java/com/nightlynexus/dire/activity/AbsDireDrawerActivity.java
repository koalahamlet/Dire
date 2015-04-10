package com.nightlynexus.dire.activity;

import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.nightlynexus.dire.R;
import com.nightlynexus.dire.fragment.AbsDireFragment;
import com.nightlynexus.dire.widget.ScrimInsetsFrameLayout;

public abstract class AbsDireDrawerActivity extends AbsDireActivity {

    private DrawerLayout mDrawerLayout;
    private ScrimInsetsFrameLayout mDrawerNavigationLayout;
    private NavigationDrawerToggle mDrawerNavToggle;
    private boolean mIsRoot;

    protected abstract class NavigationDrawerToggle extends ActionBarDrawerToggle {

        protected abstract void onDrawerOpened();

        protected abstract void onDrawerClosed();

        private static final long DURATION_ANIMATION = 320l;

        private ValueAnimator mAnimator;

        public NavigationDrawerToggle() {
            super(AbsDireDrawerActivity.this, mDrawerLayout, getToolbar(),
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            mAnimator = null;
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            // disable hamburger-arrow animation
            setNavigationItem();
        }

        @Override
        public final void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            setNavigationItem();
            onDrawerOpened();
        }

        @Override
        public final void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            setNavigationItem();
            onDrawerClosed();
        }

        private void setNavigationItem() {
            final float slideOffset = mIsRoot ? 0f : 1f;
            super.onDrawerSlide(mDrawerLayout, slideOffset);
        }

        private void animateNavigationItem(boolean isRoot) {
            if (mIsRoot == isRoot) {
                // never animate on first run (true == true)
                // if no change, either continue animation or do nothing
                return;
            }
            mIsRoot = isRoot;
            final float start, end;
            if (isRoot) {
                // animate to menu
                start = 1f;
                end = 0f;
            } else {
                // animate to arrow
                start = 0f;
                end = 1f;
            }
            final ValueAnimator animator = ValueAnimator.ofFloat(start, end);
            if (mAnimator != null) {
                mAnimator.cancel();
            }
            mAnimator = animator;
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    final float slideOffset = (float) valueAnimator.getAnimatedValue();
                    NavigationDrawerToggle.super.onDrawerSlide(mDrawerLayout, slideOffset);
                }
            });
            mAnimator.setInterpolator(new DecelerateInterpolator());
            mAnimator.setDuration(DURATION_ANIMATION);
            mAnimator.start();
        }
    }

    @NonNull
    protected abstract NavigationDrawerToggle getNavigationDrawerToggle();

    /**
     * @return the color value for the intersection of the status bar and the navigation drawer that
     *         will give the appearance of the navigation drawer going underneath the status bar
     */
    protected abstract int getStatusBarNavigationDrawerColor();

    /**
     * @param parent the parent of the View to be returned.
     *               Use the parent to set the appropriate LayoutParams via correct inflation.
     *
     * @return the View to use as the navigation drawer
     */
    protected abstract View getNavigationDrawerView(ViewGroup parent);

    @Override
    @LayoutRes
    final int getContentViewResourceLayout() {
        return R.layout.activity_abs_title_drawer;
    }

    @Override
    final void setUpViewsAndStates(Bundle savedInstanceState) {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerNavigationLayout = (ScrimInsetsFrameLayout) findViewById(R.id.navigation_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
        mDrawerNavigationLayout.setInsetForegroundColor(getStatusBarNavigationDrawerColor());
        mDrawerNavigationLayout.addView(getNavigationDrawerView(mDrawerNavigationLayout));
        mDrawerNavToggle = getNavigationDrawerToggle();
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doOnHomeClicked();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerNavToggle);
        mIsRoot = true;
    }

    @Override
    final void setHomeIndicator(boolean isRoot) {
        mDrawerNavToggle.animateNavigationItem(isRoot);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerNavToggle.syncState();
        if (isDrawerNavigationOpen()) {
            mDrawerNavToggle.onDrawerOpened();
        } else {
            mDrawerNavToggle.onDrawerClosed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerNavToggle.onConfigurationChanged(newConfig);
    }

    protected final void changeContentFragment(AbsDireFragment fragment) {
        // pop entire back stack
        // immediately, so we get the correct Fragment to remove
        getSupportFragmentManager().popBackStackImmediate(
                null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        launchFragment(fragment);
    }

    protected final void popToContentFragment() {
        // pop back stack up to the first Fragment
        // immediately, so we get the correct back stack entry count
        final FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStackImmediate();
        }
    }

    protected final void openDrawerNavigation() {
        mDrawerLayout.openDrawer(mDrawerNavigationLayout);
    }

    protected final void closeDrawerNavigation() {
        mDrawerLayout.closeDrawer(mDrawerNavigationLayout);
    }

    protected final boolean isDrawerNavigationOpen() {
        return mDrawerLayout.isDrawerOpen(mDrawerNavigationLayout);
    }

    protected final boolean isDrawerNavigationVisible() {
        return mDrawerLayout.isDrawerVisible(mDrawerNavigationLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                doOnHomeClicked();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    final void doOnHomeClicked() {
        if (mIsRoot) {
            if (isDrawerNavigationVisible()) {
                closeDrawerNavigation();
            } else {
                openDrawerNavigation();
            }
        } else {
            doOnBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (isDrawerNavigationVisible()) {
            closeDrawerNavigation();
        } else {
            super.onBackPressed();
        }
    }

    protected final int getStatusBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final int resourceId = getResources()
                    .getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                return getResources().getDimensionPixelSize(resourceId); // should be 24dp
            }
        }
        return 0;
    }
}
