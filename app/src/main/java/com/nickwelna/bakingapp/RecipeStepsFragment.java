package com.nickwelna.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.nickwelna.bakingapp.StepAdapter.StepSelectedListener;
import com.nickwelna.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsFragment extends Fragment implements OnClickListener, StepSelectedListener {

    Recipe recipe;
    @BindView(R.id.steps_recycler_view)
    RecyclerView stepsRecyclerView;
    @BindView(R.id.recipe_ingredients_button)
    Button recipeIngredientsButton;

    public RecipeStepsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            recipe = getArguments().getParcelable("recipe");

        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        stepsRecyclerView.setLayoutManager(layoutManager);

        final StepAdapter adapter = new StepAdapter(this);
        stepsRecyclerView.setAdapter(adapter);

        adapter.setSteps(recipe.getSteps());

        recipeIngredientsButton.setOnClickListener(this);

        return rootView;

    }

    @Override
    public void onClick(View v) {

        getActivity().onBackPressed();

    }

    @Override
    public void onStepSelected(int step) {

        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();

        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList("steps", recipe.getSteps());
        arguments.putInt("current_step", step);
        arguments.putInt("number_of_steps", recipe.getSteps().size());

        stepDetailsFragment.setArguments(arguments);

        getFragmentManager().beginTransaction().replace(R.id.fragment_holder, stepDetailsFragment).addToBackStack( "tag" ).commit();

    }

}