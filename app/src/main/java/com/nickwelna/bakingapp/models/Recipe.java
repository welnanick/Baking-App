package com.nickwelna.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int servings;
    private String image;

    public String getName() {

        return name;

    }

    public ArrayList<Ingredient> getIngredients() {

        return ingredients;

    }

    public ArrayList<Step> getSteps() {

        return steps;

    }

    public String getImage() {

        return image;

    }

    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
        dest.writeInt(this.servings);
        dest.writeString(this.image);

    }

    public Recipe() {

    }

    protected Recipe(Parcel in) {

        this.id = in.readInt();
        this.name = in.readString();
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.steps = in.createTypedArrayList(Step.CREATOR);
        this.servings = in.readInt();
        this.image = in.readString();

    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {

        @Override
        public Recipe createFromParcel(Parcel source) {

            return new Recipe(source);

        }

        @Override
        public Recipe[] newArray(int size) {

            return new Recipe[size];

        }

    };

}
