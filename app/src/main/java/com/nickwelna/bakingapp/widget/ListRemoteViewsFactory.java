package com.nickwelna.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.nickwelna.bakingapp.R;
import com.nickwelna.bakingapp.models.Ingredient;

import java.util.Arrays;
import java.util.List;

import static com.nickwelna.bakingapp.fragments.RecipeIngredientsFragment.PREFERENCE_KEY;

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private List<Ingredient> ingredients;

    public ListRemoteViewsFactory(Context context) {

        this.context = context;

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        String ingredientString = sharedPreferences.getString(PREFERENCE_KEY, "");

        Gson gson = new Gson();
        if (!TextUtils.isEmpty(ingredientString)) {

            ingredients = Arrays.asList(gson.fromJson(ingredientString, Ingredient[].class));

        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {

        if (ingredients == null) {

            return 0;

        }
        return ingredients.size();

    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);

        double quantity = ingredients.get(position).getQuantity();
        String measurement = ingredients.get(position).getMeasure().toLowerCase();

        String ingredientString =
                context.getString(R.string.ingredient_format, quantity, measurement,
                        ingredients.get(position).getIngredient());
        views.setTextViewText(R.id.ingredient_name_text_view, ingredientString);

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {

        return null;

    }

    @Override
    public int getViewTypeCount() {

        return 1;

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @Override
    public boolean hasStableIds() {

        return true;

    }

}
