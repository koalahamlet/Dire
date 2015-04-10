package com.nightlynexus.playingatmyhouse.punk.util;

import com.nightlynexus.playingatmyhouse.R;
import com.nightlynexus.playingatmyhouse.punk.model.Picture;

public final class PictureFactory {

    public static Picture[] getPictures() {
        return new Picture[] {
                new Picture(R.drawable.profile),
                new Picture(R.drawable.dp),
                new Picture(R.drawable.digital),
                new Picture(R.drawable.ram)
        };
    }

    private PictureFactory() {
        // no instances
    }
}
