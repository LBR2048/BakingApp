package com.udacity.bakingapp.widgets.widgetlistview;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by unity on 09/01/18.
 */

public class ListWidgetService extends RemoteViewsService {

    public static String EXTRA_RECIPE_ID = "extra-recipe-id";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, -1);
        return new ListRemoteViewsFactory(this.getApplicationContext(), recipeId);
    }
}
