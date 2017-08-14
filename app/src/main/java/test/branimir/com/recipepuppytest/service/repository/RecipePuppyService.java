package test.branimir.com.recipepuppytest.service.repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import test.branimir.com.recipepuppytest.service.model.RecipeList;

/**
 * Created by bconjar on 14/08/17.
 */

public interface RecipePuppyService {

    String BASE_URL = "http://www.recipepuppy.com/";

    @GET("/api")
    Call<RecipeList> listRecipes(@Query("q") String query, @Query("p") String pageNumber);
}
