package com.udacity.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipes.MainActivity;

public class WidgetConfigActivity extends MainActivity {

    private int mAppWidgetId;

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Toast.makeText(this, "Recipe clicked: " + recipe.getId().toString(), Toast.LENGTH_SHORT).show();

        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putInt(String.valueOf(mAppWidgetId), recipe.getId())
                .apply();

        // Perform your App Widget configuration.
        BakingService.startActionUpdateWidget(this, mAppWidgetId, recipe.getId());

        // When the configuration is complete,
        // get an instance of the AppWidgetManager by calling getInstance(Context):
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        // Update the App Widget with a RemoteViews layout
//        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.baking_app_widget);
//        appWidgetManager.updateAppWidget(mAppWidgetId, views);

        // Finally, create the return Intent, set it with the Activity result, and finish the Activity:
        Intent returnIntent = new Intent();
        returnIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // When your configuration Activity first opens, set the Activity result to RESULT_CANCELED,
        // along with EXTRA_APPWIDGET_ID, as shown in step 5 above.
        // This way, if the user backs-out of the Activity before reaching the end, the App Widget
        // host is notified that the configuration was cancelled and the App Widget will not be added.
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_CANCELED, resultValue);

        // First, get the App Widget ID from the Intent that launched the Activity:
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If you receive an intent without the appropriate ID, then the system should kill this Activity
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }
}
