package com.udacity.bakingapp.widgets.widgetlistview;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by unity on 09/01/18.
 */

public class ListWidgetService extends RemoteViewsService {

    public static String EXTRA_RECIPE_ID = "extra-recipe-id";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        if (intent.getData() != null) {
            Integer recipeId = Integer.valueOf(intent.getData().getSchemeSpecificPart());
            Log.d("Factory", "onGetViewFactory");
            return new ListRemoteViewsFactory(this.getApplicationContext(), recipeId);
        } else {
            return null;
        }
    }
}
