package com.udacity.bakingapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.udacity.bakingapp.stepdetails.StepDetailsFragment;

/**
 * Created by leonardo.ardjomand on 22/03/2018.
 */

public class GuiUtils {

    private static final int HANDSET_LANDSCAPE_WIDTH_LIMIT = 840;

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

    public static float getWidthDp(Context context) {
        // https://stackoverflow.com/questions/6465680/how-to-determine-the-screen-width-in-terms-of-dp-or-dip-at-runtime-in-android
        // https://stackoverflow.com/questions/29579811/changing-number-of-columns-with-gridlayoutmanager-and-recyclerview
        // Best solution: http://blog.sqisland.com/2014/12/recyclerview-autofit-grid.html
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / displayMetrics.density;
    }

    public static boolean isHandsetLandscape(Context context) {
        int orientation = context.getResources().getConfiguration().orientation;
        float widthDp = getWidthDp(context);
        return orientation == Configuration.ORIENTATION_LANDSCAPE
                && widthDp <= HANDSET_LANDSCAPE_WIDTH_LIMIT;
    }

    public static void replaceFragment(FragmentActivity fragmentActivity, int containerViewId, Fragment fragment, String fragmentTag) {
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .commit();
        fragmentActivity.getSupportFragmentManager().executePendingTransactions();
    }

    public static void replaceFragmentWithBackStack(FragmentActivity fragmentActivity, int containerViewId, StepDetailsFragment stepDetailsFragment, String tag) {
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, stepDetailsFragment, tag)
                .addToBackStack(null)
                .commit();
        fragmentActivity.getSupportFragmentManager().executePendingTransactions();
    }
}
