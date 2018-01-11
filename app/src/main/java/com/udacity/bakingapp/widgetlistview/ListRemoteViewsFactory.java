package com.udacity.bakingapp.widgetlistview;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.udacity.bakingapp.repository.RecipesRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unity on 09/01/18.
 */

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;
    private RecipesRepositoryImpl mRecipesRepository;

    List<String> mCollection = new ArrayList<>();


    public ListRemoteViewsFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        mRecipesRepository = new RecipesRepositoryImpl();
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCollection.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        view.setTextViewText(android.R.id.text1, mCollection.get(i));
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initData() {
        mCollection.clear();
        for (int i = 1; i <= 10; i++) {
            mCollection.add("Ingredient " + i);
        }
    }

}
