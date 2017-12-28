package com.udacity.bakingapp.details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.details.DetailsFragment.OnDetailsFragmentInteraction;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Recipe} and makes a call to the
 * specified {@link OnDetailsFragmentInteraction}.
 * TODO: Replace the implementation with code for your data type.
 */
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private List<Step> mValues;
    private final OnDetailsFragmentInteraction mListener;

    public StepsAdapter(List<Step> items, OnDetailsFragmentInteraction listener) {
        mValues = items;
        mListener = listener;
    }

    public void replaceData(List<Step> steps) {
        mValues = steps;
        notifyDataSetChanged();
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
        public final View mView;
        public final TextView mShortDescription;
        public final TextView mContentView;
        public Step mStep;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mShortDescription = (TextView) view.findViewById(R.id.short_description);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
