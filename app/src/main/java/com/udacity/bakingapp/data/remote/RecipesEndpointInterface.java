package com.udacity.bakingapp.data.remote;

import com.udacity.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by leonardo.ardjomand on 04/01/2018.
 */

public interface RecipesEndpointInterface {

    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("{recipes}")
    Call<List<Recipe>> getRecipes(@Path("recipes") String recipes);
}
