package com.udacity.bakingapp.stepdetails;

/**
 * Created by leonardo.ardjomand on 28/12/2017.
 */

public interface StepDetailsContract {

    interface View {

        void showDescription(String description);

        void showVideo(String videoUrl);
    }

    interface Presenter {

        void getFirstStep(int recipeId);

        void getPreviousStep();

        void getNextStep();
    }
}
