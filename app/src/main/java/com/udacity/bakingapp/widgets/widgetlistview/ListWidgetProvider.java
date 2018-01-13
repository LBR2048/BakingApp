package com.udacity.bakingapp.widgets.widgetlistview;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.widgets.WidgetService;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.recipes.SinglePaneActivity;

/**
 * Implementation of App Widget functionality.
 */
public class ListWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_listview);

        // Set the ListWidgetService intent to act as the adapter for the ListView
        Intent listWidgetIntent = new Intent(context, ListWidgetService.class);
        views.setRemoteAdapter(R.id.appwidget_ingredients_listview, listWidgetIntent);

        // Create an intent to launch SinglePaneActivity when clicked
        Intent intent = new Intent(context, SinglePaneActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Widget allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.appwidget, pendingIntent);

        // Show recipe title on the widget
//        views.setTextViewText(R.id.appwidget_recipe_name, recipe.getName());
        views.setTextViewText(R.id.appwidget_recipe_name, "Nutella Pie");

        // Show ingredients on the widget
//        String ingredientsString = Utils.formatIngredientsString(recipe.getIngredients());
//        views.setTextViewText(R.id.appwidget_ingredients, ingredientsString);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
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

