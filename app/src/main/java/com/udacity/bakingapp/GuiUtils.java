package com.udacity.bakingapp;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by leonardo.ardjomand on 22/03/2018.
 */

public class GuiUtils {

    @SuppressLint("InlinedApi")
    public static void hideSystemUI(FragmentActivity activity) {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
