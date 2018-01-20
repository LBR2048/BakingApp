package com.udacity.bakingapp;

import android.app.Application;

import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;

/**
 * Created by unity on 19/01/18.
 */

public class BakingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Rush is initialized asynchronously to recieve a callback after it initialized
        // set an InitializeListener on the config object

        // All classes that extent RushObject or implement Rush must be added to the config
        // this is new to v1.3.0 to fix issues introduced in android 6,
        // this is far more efficient that searching for classes like in pervious versions
        List<Class<? extends Rush>> classes = new ArrayList<>();
        classes.add(Ingredient.class);
        classes.add(Recipe.class);
        classes.add(Step.class);

        AndroidInitializeConfig config
                = new AndroidInitializeConfig(getApplicationContext(), classes);
        RushCore.initialize(config);
    }
}
