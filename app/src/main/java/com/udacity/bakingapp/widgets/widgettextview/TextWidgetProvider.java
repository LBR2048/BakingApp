package com.udacity.bakingapp.widgets.widgettextview;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.Utils;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipes.SinglePaneActivity;
import com.udacity.bakingapp.widgets.WidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class TextWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients);

        // Create an intent to launch SinglePaneActivity when clicked
        Intent intent = new Intent(context, SinglePaneActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Widget allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.appwidget_ingredients, pendingIntent);

        // Show recipe title on the widget
        views.setTextViewText(R.id.appwidget_recipe_name, recipe.getName());

        // Show ingredients on the widget
        String ingredientsString = Utils.formatIngredientsString(recipe.getIngredients());
        views.setTextViewText(R.id.appwidget_ingredients, ingredientsString);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Update the widget through an IntentService
        WidgetService.startActionUpdateAllWidgets(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

