package com.udacity.bakingapp.stepdetails;

import com.udacity.bakingapp.model.Step;

/**
 * Created by leonardo.ardjomand on 28/12/2017.
 */

public interface StepDetailsContract {

    interface View {

        void showStep(Step step);
    }

    interface Presenter {

        void getFirstStep(int recipeId);

        void getPreviousStep();

        void getNextStep();
    }
}
