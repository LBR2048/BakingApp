package com.udacity.bakingapp.widgets.widgetlistview;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.udacity.bakingapp.widgets.widgetlistview.ListRemoteViewsFactory;

/**
 * Created by unity on 09/01/18.
 */

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}
