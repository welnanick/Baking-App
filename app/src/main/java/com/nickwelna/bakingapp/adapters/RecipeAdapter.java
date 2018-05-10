package com.nickwelna.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nickwelna.bakingapp.R;
import com.nickwelna.bakingapp.adapters.RecipeAdapter.RecipeViewHolder;
import com.nickwelna.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private Recipe[] recipes;
    private final RecipeOnClickHandler onClickHandler;

    public interface RecipeOnClickHandler {

        void onClick(Recipe recipe);

    }

    public RecipeAdapter(RecipeOnClickHandler onClickHandler) {

        this.onClickHandler = onClickHandler;

    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeViewHolder(view, parent.getContext());

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

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        @BindView(R.id.recipe_name)
        TextView recipeName;
        @BindView(R.id.recipe_image)
        ImageView recipeImage;
        Context context;

        RecipeViewHolder(View itemView, Context context) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            this.context = context;

        }

        void bind(Recipe recipe) {

            recipeName.setText(recipe.getName());
            if (recipe.getImage() != null && !TextUtils.isEmpty(recipe.getImage())) {

                Glide.with(context).load(recipe.getImage()).into(recipeImage);

            }

        }

        @Override
        public void onClick(View v) {

            onClickHandler.onClick(recipes[getAdapterPosition()]);

        }

    }

}
