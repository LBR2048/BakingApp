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
    private int mCurrentStepPosition;

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
                Step step = findStepById(stepId, steps);

                if (step != null) {
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
//        mView.showStep(mSteps.get(--mCurrentStepPosition));
    }

    @Override
    public void getNextStep() {
//        mView.showStep(mSteps.get(++mCurrentStepPosition));
    }

    private Step findStepById(int stepId, List<Step> steps) {
        for (Step step : steps) {
            if (step.getId() == stepId) {
                return step;
            }
        }
        return null;
    }
}
