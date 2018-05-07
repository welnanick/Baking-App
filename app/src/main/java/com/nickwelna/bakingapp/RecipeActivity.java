package com.nickwelna.bakingapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.nickwelna.bakingapp.models.Recipe;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if (getResources().getBoolean(R.bool.isTablet)) {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (savedInstanceState == null && extras != null) {

            final Recipe recipe = extras.getParcelable(getString(R.string.recipe_key));

            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeIngredientsFragment ingredientsFragment = new RecipeIngredientsFragment();

            Bundle arguments = new Bundle();
            arguments.putParcelable(getString(R.string.recipe_key), recipe);

            ingredientsFragment.setArguments(arguments);

            fragmentManager.beginTransaction().add(R.id.fragment_holder, ingredientsFragment)
                           .commit();

        }

    }

}
