package com.udacity.bakingapp.steps;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.steps.StepsFragment.OnDetailsFragmentInteraction;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Recipe} and makes a call to the
 * specified {@link OnDetailsFragmentInteraction}.
 * TODO: Replace the implementation with code for your data type.
 */
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private List<Step> mValues;
    private final OnStepsAdapterInteraction mListener;

    StepsAdapter(List<Step> items, OnStepsAdapterInteraction listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mStep = mValues.get(position);
        holder.mShortDescription.setText(holder.mStep.getShortDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onStepClicked(holder.mStep);
                }
//                Toast.makeText(v.getContext(), holder.mStep.toString() + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mShortDescription;
        final TextView mContentView;
        Step mStep;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mShortDescription = view.findViewById(R.id.short_description);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    void replaceData(List<Step> steps) {
        mValues = steps;
        notifyDataSetChanged();
    }

    public interface OnStepsAdapterInteraction {
        void onStepClicked(Step step);
    }
}
