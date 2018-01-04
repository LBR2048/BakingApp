package com.udacity.bakingapp.stepdetails;

/**
 * Created by leonardo.ardjomand on 28/12/2017.
 */

public interface StepDetailsContract {

    interface View {

        void setStepId(int stepId);

        void showStepTitle(String stepName);

        void showDescription(String description);

        void showVideo(String videoUrl);

        void hideVideo();

        void setPreviousButtonVisibility(boolean visibility);

        void setNextButtonVisibility(boolean visibility);
    }

    interface Presenter {

        void getStep(int recipeId, int stepId);

        void getPreviousStep();

        void getNextStep();
    }
}
