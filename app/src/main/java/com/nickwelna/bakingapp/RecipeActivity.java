package com.nickwelna.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.nickwelna.bakingapp.models.Recipe;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        if (savedInstanceState == null && extras != null) {

            final Recipe recipe = extras.getParcelable("Recipe");

            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeIngredientsFragment ingredientsFragment = new RecipeIngredientsFragment();

            Bundle arguments = new Bundle();
            arguments.putParcelable("recipe", recipe);

            ingredientsFragment.setArguments(arguments);

            fragmentManager.beginTransaction().add(R.id.fragment_holder, ingredientsFragment)
                           .commit();

        }

    }

}
