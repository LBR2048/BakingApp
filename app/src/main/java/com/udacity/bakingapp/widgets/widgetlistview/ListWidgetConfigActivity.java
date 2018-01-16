package com.udacity.bakingapp.widgets.widgetlistview;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipes.SinglePaneActivity;

public class ListWidgetConfigActivity extends SinglePaneActivity {

    private int mAppWidgetId;

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Toast.makeText(this, "Recipe clicked: " + recipe.getId().toString(), Toast.LENGTH_SHORT).show();

        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putInt(String.valueOf(mAppWidgetId), recipe.getId())
                .apply();

        // Perform your App Widget configuration.
        ListWidgetDataService.startActionUpdateWidget(this, mAppWidgetId, recipe.getId());

        // When the configuration is complete,
        // get an instance of the AppWidgetManager by calling getInstance(Context):
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        // Update the App Widget with a RemoteViews layout
//        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.widget_ingredients);
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

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.widget_config_acivity_title);
        }

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
