package com.nickwelna.bakingapp.fragments;

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

import com.nickwelna.bakingapp.R;
import com.nickwelna.bakingapp.adapters.StepAdapter;
import com.nickwelna.bakingapp.adapters.StepAdapter.StepSelectedListener;
import com.nickwelna.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsFragment extends Fragment implements OnClickListener, StepSelectedListener {

    private Recipe recipe;
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

            recipe = getArguments().getParcelable(getString(R.string.recipe_key));

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

        if (getActivity() != null) {

            getActivity().onBackPressed();

        }

    }

    @Override
    public void onStepSelected(int step) {

        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();

        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(getString(R.string.recipe_steps_key), recipe.getSteps());
        arguments.putInt(getString(R.string.current_step_key), step);
        arguments.putInt(getString(R.string.number_of_steps_key), recipe.getSteps().size());

        stepDetailsFragment.setArguments(arguments);
        if (getResources().getBoolean(R.bool.isTablet)) {

            if (getFragmentManager() != null) {

                getFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_holder_2, stepDetailsFragment).commit();

            }

        }
        else {

            if (getFragmentManager() != null) {

                getFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_holder, stepDetailsFragment)
                                    .addToBackStack(getString(R.string.fragment_tag_key)).commit();

            }

        }

    }

}