package com.udacity.bakingapp.widgets.listwidget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.repository.RecipesRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unity on 09/01/18.
 */

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private int mRecipeId;
    private Context mContext;
    private RecipesRepositoryImpl mRecipesRepository;

    private List<Ingredient> mIngredients = new ArrayList<>();

    public ListRemoteViewsFactory(Context context, int recipeId) {
        mContext = context;
        mRecipeId = recipeId;
    }

    @Override
    public void onCreate() {
        mRecipesRepository = new RecipesRepositoryImpl();
    }

    @Override
    public void onDataSetChanged() {
        loadData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        view.setTextViewText(android.R.id.text1, mIngredients.get(i).getIngredient());
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

    private void loadData() {
        mIngredients = mRecipesRepository.loadRecipe(mRecipeId).getIngredients();
    }

}
