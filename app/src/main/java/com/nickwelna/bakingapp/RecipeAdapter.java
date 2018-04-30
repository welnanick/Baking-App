package com.nickwelna.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nickwelna.bakingapp.RecipeAdapter.RecipeViewHolder;
import com.nickwelna.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder>{

    Recipe[] recipes;

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        holder.bind(recipes[position]);

    }

    public void setRecipes(Recipe[] recipes) {

        this.recipes = recipes;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

        if (recipes == null) {

            return 0;

        }

        return recipes.length;

    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name)
        TextView recipeName;

        public RecipeViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void bind(Recipe recipe) {

            recipeName.setText(recipe.getName());

        }

    }


}
