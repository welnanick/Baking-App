package com.nickwelna.bakingapp;

import android.content.Context;
import android.net.Uri;
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

import com.nickwelna.bakingapp.models.Ingredient;
import com.nickwelna.bakingapp.models.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientsFragment extends Fragment implements OnClickListener {

    Recipe recipe;

    @BindView(R.id.ingredients_recycler_view)
    RecyclerView ingredientsRecyclerView;
    @BindView(R.id.recipe_steps_button)
    public Button recipeStepsButton;

    public RecipeIngredientsFragment() {
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

        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ingredientsRecyclerView.setLayoutManager(layoutManager);

        final IngredientAdapter adapter = new IngredientAdapter();
        ingredientsRecyclerView.setAdapter(adapter);

        adapter.setIngredients(recipe.getIngredients());

        recipeStepsButton.setOnClickListener(this);

        return rootView;

    }

    @Override
    public void onClick(View v) {

        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();

        Bundle arguments = new Bundle();
        arguments.putParcelable("recipe", recipe);

        stepsFragment.setArguments(arguments);

        getFragmentManager().beginTransaction().replace(R.id.fragment_holder, stepsFragment).addToBackStack( "tag" ).commit();

    }

}
