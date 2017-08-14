package test.branimir.com.recipepuppytest.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bconjar on 14/08/17.
 */

public class RecipeList {

    @SerializedName("results")
    private List<Recipe> mRecipeList;

    public RecipeList() {
        mRecipeList = new ArrayList<>();
    }

    public List<Recipe> getRecipes() {
        return mRecipeList;
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipeList = recipes;
    }
}
