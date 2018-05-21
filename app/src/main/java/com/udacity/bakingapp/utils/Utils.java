package com.udacity.bakingapp.utils;

import android.support.annotation.NonNull;

import com.udacity.bakingapp.model.Ingredient;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 03/01/2018.
 */

public class Utils {
    @NonNull
    public static String formatIngredientsString(List<Ingredient> ingredients) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            builder.append(ingredients.get(i).toString());
            if (i != ingredients.size() - 1) {
                builder.append(", \n");
            }
        }
        return builder.toString();
    }
}
