package com.nickwelna.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nickwelna.bakingapp.R;
import com.nickwelna.bakingapp.adapters.IngredientAdapter.IngredientViewHolder;
import com.nickwelna.bakingapp.models.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    private List<Ingredient> ingredients;

    public void setIngredients(List<Ingredient> ingredients) {

        this.ingredients = ingredients;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_list_item, parent, false);

        return new IngredientViewHolder(view, parent.getContext());

    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {

        holder.bind(ingredients.get(position));

    }

    @Override
    public int getItemCount() {

        if (ingredients == null) {

            return 0;

        }
        else {

            return ingredients.size();

        }

    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_name_text_view)
        TextView ingredientNameTextView;
        final Context context;

        IngredientViewHolder(View itemView, Context context) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = context;

        }

        void bind(Ingredient ingredient) {

            double quantity = ingredient.getQuantity();
            String measurement = ingredient.getMeasure().toLowerCase();

            String ingredientString =
                    context.getString(R.string.ingredient_format, quantity, measurement,
                            ingredient.getIngredient());
            ingredientNameTextView.setText(ingredientString);

        }

    }

}
