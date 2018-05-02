package com.nickwelna.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nickwelna.bakingapp.StepAdapter.StepViewHolder;
import com.nickwelna.bakingapp.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {

    private List<Step> steps;

    public void setSteps(List<Step> steps) {

        this.steps = steps;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.step_list_item, parent, false);

        return new StepViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {

        holder.bind(steps.get(position));

    }

    @Override
    public int getItemCount() {

        if (steps == null) {

            return 0;

        }
        else {

            return steps.size();

        }
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_text_view)
        TextView stepTextView;
        Context context;

        public StepViewHolder(View itemView, Context context) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = context;

        }

        void bind(Step step) {

            stepTextView.setText(step.getShortDescription());

        }

    }

}
