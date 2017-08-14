package test.branimir.com.recipepuppytest.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bconjar on 14/08/17.
 */

public class Recipe {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("ingredients")
    private String mIngredients;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getIngredients() {
        return mIngredients;
    }

    public void setIngredients(String ingredients) {
        mIngredients = ingredients;
    }
}
