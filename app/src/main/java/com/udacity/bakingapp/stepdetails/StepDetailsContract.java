package com.udacity.bakingapp.stepdetails;

/**
 * Created by leonardo.ardjomand on 28/12/2017.
 */

public interface StepDetailsContract {

    interface View {

        void showDescription(String description);

        void showVideo(String videoUrl);

        void setPreviousButtonVisibility(boolean visibility);

        void setNextButtonVisibility(boolean visibility);
    }

    interface Presenter {

        void getStep(int recipeId, int stepId);

        void getPreviousStep();

        void getNextStep();
    }
}
