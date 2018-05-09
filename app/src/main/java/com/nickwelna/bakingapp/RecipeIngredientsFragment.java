package com.nickwelna.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.google.gson.Gson;
import com.nickwelna.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientsFragment extends Fragment implements OnClickListener {

    Recipe recipe;
    @BindView(R.id.ingredients_recycler_view)
    RecyclerView ingredientsRecyclerView;
    @BindView(R.id.recipe_steps_button)
    public Button recipeStepsButton;
    @BindView(R.id.display_in_widget)
    public Button displayInWidget;
    public static final String PREFERENCE_KEY = "ingredients_list";

    public RecipeIngredientsFragment() {

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

        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ingredientsRecyclerView.setLayoutManager(layoutManager);

        final IngredientAdapter adapter = new IngredientAdapter();
        ingredientsRecyclerView.setAdapter(adapter);

        adapter.setIngredients(recipe.getIngredients());

        recipeStepsButton.setOnClickListener(this);
        displayInWidget.setOnClickListener(this);

        return rootView;

    }

    @Override
    public void onClick(View v) {

        if (v.equals(recipeStepsButton)) {

            RecipeStepsFragment stepsFragment = new RecipeStepsFragment();

            Bundle arguments = new Bundle();
            arguments.putParcelable(getString(R.string.recipe_key), recipe);

            stepsFragment.setArguments(arguments);

            getFragmentManager().beginTransaction().replace(R.id.fragment_holder, stepsFragment)
                                .addToBackStack(getString(R.string.fragment_tag_key)).commit();

        }
        else if (v.equals(displayInWidget)) {

            Gson gson = new Gson();

            String ingredients = gson.toJson(recipe.getIngredients());
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(getContext());
            Editor editor = sharedPreferences.edit();
            editor.putString(PREFERENCE_KEY, ingredients);
            editor.apply();

            int[] ids = AppWidgetManager.getInstance(getContext()).getAppWidgetIds(
                    new ComponentName(getContext(), IngredientListWidget.class));
            AppWidgetManager.getInstance(getContext())
                            .notifyAppWidgetViewDataChanged(ids, R.id.widget_list_view);

        }

    }

}
