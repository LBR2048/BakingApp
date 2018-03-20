package com.udacity.bakingapp.widgets.widgettextview;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.repository.RecipesRepository;
import com.udacity.bakingapp.repository.RecipesRepositoryImpl;

/**
 * Created by leonardo.ardjomand on 02/01/2018.
 */

public class TextWidgetDataService extends IntentService {

    private static final String ACTION_UPDATE_ALL_WIDGETS = "com.udacity.bakingapp.action.update_textwidgets";
    private static final String ACTION_UPDATE_WIDGET = "com.udacity.bakingapp.action.update_single_textwidget";

    private static final String EXTRA_RECIPE_ID = "recipeId";

    private RecipesRepository mRecipesRepository;

    public TextWidgetDataService() {
        super("Text Widget Service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRecipesRepository = new RecipesRepositoryImpl();
    }

    public static void startActionUpdateAllWidgets(Context context) {
        Intent intent = new Intent(context, TextWidgetDataService.class);
        intent.setAction(ACTION_UPDATE_ALL_WIDGETS);
        context.startService(intent);
    }

    public static void startActionUpdateWidget(Context context, int widgetId, int recipeId) {
        Intent intent = new Intent(context, TextWidgetDataService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("Widget", "onHandleIntent");

        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_UPDATE_ALL_WIDGETS.equals(action)) {
                handleActionUpdateAllWidgets();
            } else if (ACTION_UPDATE_WIDGET.equals(action)) {
                int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                int recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, 1);
                if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    handleActionUpdateWidget(widgetId, recipeId);
                }
            }
        }
    }

    private void handleActionUpdateAllWidgets() {
        final Context context = this;
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        final int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, TextWidgetProvider.class));

        for (int appWidgetId : appWidgetIds) {
            int recipeId = PreferenceManager.getDefaultSharedPreferences(context).getInt(
                    String.valueOf(appWidgetId), -1);
            handleActionUpdateWidget(appWidgetId, recipeId);
        }
    }

    private void handleActionUpdateWidget(final int widgetId, final int recipeId) {
        Log.d("Widget", "handleActionUpdateWidget: id " + recipeId);

        final Context context = this;
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        Recipe recipe = mRecipesRepository.loadRecipe(recipeId);
        if (recipe != null) {
            TextWidgetProvider.updateAppWidget(context, appWidgetManager, widgetId, recipe);
        } else {
            Toast.makeText(getApplication(), "Ingredients not available",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
