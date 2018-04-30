package com.nickwelna.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.nickwelna.bakingapp.models.Recipe;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class SelectRecipeActivity extends AppCompatActivity {

    @BindView(R.id.recipe_recycler_view)
    RecyclerView recipeRecyclerView;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipe);
        ButterKnife.bind(this);

        if (BuildConfig.DEBUG) {

            Timber.plant(new DebugTree());

        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recipeRecyclerView.setLayoutManager(layoutManager);

        final RecipeAdapter adapter = new RecipeAdapter();
        recipeRecyclerView.setAdapter(adapter);

        Request request = new Request.Builder().url("https://d17h27t6h515a5.cloudfront" +
                ".net/topher/2017/May/59121517_baking/baking.json").build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                ResponseBody responseBody = response.body();

                if (responseBody != null) {

                    try {

                        String responseString = responseBody.string();
                        Gson gson = new Gson();
                        final Recipe[] recipes = gson.fromJson(responseString, Recipe[].class);

                        Timber.d("%d", recipes.length);
                        for (Recipe recipe : recipes) {

                            Timber.d(recipe.getName());

                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                adapter.setRecipes(recipes);

                            }

                        });

                    }
                    catch (IOException e) {

                        Timber.e(e);

                    }

                }

            }

        });

    }

}
