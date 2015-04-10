package com.nightlynexus.dire.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class AbsDireFragment extends Fragment {

    private static final String ARG_TITLE = AbsDireFragment.class.getName() + "." + "ARG_TITLE";

    private CharSequence mTitleAlt = null;

    public final void setArguments(@Nullable Bundle bundle, @NonNull CharSequence title) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putCharSequence(ARG_TITLE, title);
        super.setArguments(bundle);
    }

    @Deprecated
    @Override
    public final void setArguments(Bundle args) {
        throw new RuntimeException("Use setArguments(bundle, title).");
    }

    public final CharSequence getTitle() {
        if (mTitleAlt != null) {
            return mTitleAlt;
        } else {
            final CharSequence title = getArguments().getCharSequence(ARG_TITLE);
            if (title == null) {
                throw new IllegalStateException(
                        "setArguments(bundle, title) must be called on the Fragment.");
            }
            return title;
        }
    }

    /**
     * Sets a custom title that will be returned in getTitle(),
     * rather than the default title from ARG_TITLE.
     * Pass in {@code null} to reset to the default title.
     * Note that setTitle(title) will not be called here.
     *
     * @param title the alternate title
     * @return the result of getTitle(),
     *         for convenience in case of desired one-liners: activity.setTitle(setTitleAlt(title))
     */
    protected final CharSequence setTitleAlt(CharSequence title) {
        mTitleAlt = title;
        return getTitle();
    }
}
