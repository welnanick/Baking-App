package com.nickwelna.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private double quantity;
    private String measure;
    private String ingredient;

    public String getIngredient() {

        return ingredient;
    }

    public double getQuantity() {

        return quantity;

    }

    public String getMeasure() {

        return measure;

    }

    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeDouble(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);

    }

    public Ingredient() {

    }

    protected Ingredient(Parcel in) {

        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.ingredient = in.readString();

    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel source) {

            return new Ingredient(source);

        }

        @Override
        public Ingredient[] newArray(int size) {

            return new Ingredient[size];

        }

    };

}
