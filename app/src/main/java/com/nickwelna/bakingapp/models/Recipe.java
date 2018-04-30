package com.nickwelna.bakingapp.models;

public class Recipe {

    private int id;
    private String name;
    private Ingredient[] ingredients;
    private Step[] steps;
    private int servings;
    private String image;

    public String getName() {

        return name;

    }

}
