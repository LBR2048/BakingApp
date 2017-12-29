package com.udacity.bakingapp.stepdetails;

import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.repository.RecipesRepository;
import com.udacity.bakingapp.repository.RecipesRepositoryImpl;

import java.util.List;

/**
 * Created by leonardo.ardjomand on 28/12/2017.
 */

public class StepDetailsPresenter implements StepDetailsContract.Presenter {

    private List<Step> mSteps;
    private int mCurrentStepId;

    private StepDetailsContract.View mView;
    private RecipesRepository mRepository;

    public StepDetailsPresenter(StepDetailsContract.View view) {
        mView = view;
        mRepository = new RecipesRepositoryImpl();
    }

    @Override
    public void getStep(int recipeId, final int stepId) {
        mRepository.loadSteps(new RecipesRepository.LoadStepsCallback() {
            @Override
            public void onStepsLoaded(List<Step> steps) {
                mSteps = steps;

                Step step = findStepById(stepId, steps);

                if (step != null) {
                    mCurrentStepId = stepId;

                    mView.showDescription(step.getDescription());

                    String videoURL = step.getVideoURL();
                    if (!videoURL.equals("")) {
                        mView.showVideo(videoURL);
                    }
                }
            }
        }, recipeId);
    }

    @Override
    public void getPreviousStep() {
        Step step = mSteps.get(--mCurrentStepId);
        mView.showDescription(step.getDescription());
        mView.showVideo(step.getVideoURL());
    }

    @Override
    public void getNextStep() {
        Step step = mSteps.get(++mCurrentStepId);
        mView.showDescription(step.getDescription());
        mView.showVideo(step.getVideoURL());
    }

    private Step findStepById(int stepId, List<Step> steps) {
        for (Step step : steps) {
            if (step.getId() == stepId) {
                return step;
            }
        }
        return null;
    }

    private int findStepPositionById(int stepId, List<Step> steps) {
        for (int i = 0; i < steps.size(); i++) {
            if (steps.get(i).getId() == stepId) {
                return i;
            }
        }
        return -1;
    }
}
