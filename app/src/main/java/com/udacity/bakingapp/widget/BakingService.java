package com.udacity.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.repository.RecipesRepository;
import com.udacity.bakingapp.repository.RecipesRepositoryImpl;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 02/01/2018.
 */

public class BakingService extends IntentService {

    private static final String ACTION_UPDATE_WIDGETS = "com.udacity.bakingapp.action.update_widgets";
    private RecipesRepositoryImpl mRecipesRepository;

    public BakingService() {
        super("Baking Service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRecipesRepository = new RecipesRepositoryImpl();
    }

    public static void startActionUpdateIngredientWidgets(Context context) {
        Intent intent = new Intent(context, BakingService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_UPDATE_WIDGETS.equals(action)) {
                handleActionUpdateWidgets();
            }
        }
    }

    private void handleActionUpdateWidgets() {
        final Context context = this;
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        final int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, BakingAppWidget.class));

        mRecipesRepository.loadRecipe(new RecipesRepository.LoadRecipeCallback() {
            @Override
            public void onRecipeLoaded(Recipe recipe) {
                List<Ingredient> ingredients = recipe.getIngredients();
                BakingAppWidget.updateIngredientWidgets(context, appWidgetManager, appWidgetIds, ingredients);
            }

            @Override
            public void onDataNotAvailable() {
                Toast.makeText(getApplication(), "Ingredients not available", Toast.LENGTH_SHORT).show();
            }
        }, 1);
    }
}
