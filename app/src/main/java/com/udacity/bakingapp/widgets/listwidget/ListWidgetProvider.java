package com.udacity.bakingapp.widgets.listwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipes.SinglePaneActivity;

/**
 * Implementation of App Widget functionality.
 */
public class ListWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, Recipe recipe) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_listview);

        // Set the ListWidgetService intent to act as the adapter for the ListView
        // Pass recipeId to RemoteViewsService using Intent's data
        // https://stackoverflow.com/questions/11350287/ongetviewfactory-only-called-once-for-multiple-widgets
        Intent listWidgetIntent = new Intent(context, ListWidgetService.class);
        listWidgetIntent.setData(Uri.fromParts("content", String.valueOf(recipe.getId()), null));
        Log.d("Factory", "Putting extra into intent");
        views.setRemoteAdapter(R.id.appwidget_ingredients_listview, listWidgetIntent);

        // Create an intent to launch SinglePaneActivity when clicked
        Intent intent = new Intent(context, SinglePaneActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Widget allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.appwidget, pendingIntent);

        // Show recipe title on the widget
        views.setTextViewText(R.id.appwidget_recipe_name, recipe.getName());

        // Show ingredients on the widget
//        String ingredientsString = Utils.formatIngredientsString(recipe.getIngredients());
//        views.setTextViewText(R.id.appwidget_ingredients, ingredientsString);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Update the widget through an IntentService
        ListWidgetDataService.startActionUpdateAllWidgets(context);
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

