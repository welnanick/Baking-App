package com.nickwelna.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nickwelna.bakingapp.StepAdapter.StepViewHolder;
import com.nickwelna.bakingapp.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {

    private List<Step> steps;
    private final StepSelectedListener stepSelectedListener;

    public StepAdapter(StepSelectedListener stepSelectedListener) {

        this.stepSelectedListener = stepSelectedListener;

    }

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

    public class StepViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        @BindView(R.id.step_text_view)
        TextView stepTextView;

        StepViewHolder(View itemView, Context context) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        void bind(Step step) {

            stepTextView.setText(step.getShortDescription());

        }

        @Override
        public void onClick(View v) {

            stepSelectedListener.onStepSelected(getAdapterPosition());

        }

    }

    public interface StepSelectedListener {

        void onStepSelected(int step);

    }

}
