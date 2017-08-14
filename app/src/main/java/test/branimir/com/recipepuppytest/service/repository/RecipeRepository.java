package test.branimir.com.recipepuppytest.service.repository;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.branimir.com.recipepuppytest.service.model.RecipeList;

/**
 * Created by bconjar on 14/08/17.
 */


public class RecipeRepository {

    private static final int DEFAULT_MAX_RESULTS = 20;
    private static final int PAGE_SIZE = 10;

    private static RecipeRepository sInstance;

    private final MutableLiveData<RecipeList> result = new MutableLiveData<>();
    private RecipePuppyService mService;

    private List<Call<RecipeList>> mCurrentRecipeListRequest;
    private int mRequestsFinished = 0;
    private RecipeList mFinalList;

    private int mMaxResults = DEFAULT_MAX_RESULTS;

    public static RecipeRepository getInstance() {
        if (sInstance == null) {
            sInstance = new RecipeRepository();
        }
        return sInstance;
    }

    private RecipeRepository() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RecipePuppyService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        mService = retrofit.create(RecipePuppyService.class);
        mCurrentRecipeListRequest = new ArrayList<>();
        mFinalList = new RecipeList();
    }

    public MutableLiveData<RecipeList> search(String query) {
        cancelOutgoingCalls();
        mRequestsFinished = 0;
        mFinalList = new RecipeList();
        mFinalList.setRecipes(new ArrayList<>());
        for (int i = 0; (i * PAGE_SIZE) < mMaxResults; i++) {
            int pageNumber = i+1;
            Call<RecipeList> recipeListCall = mService.listRecipes(query, String.valueOf(pageNumber));
            mCurrentRecipeListRequest.add(recipeListCall);
            recipeListCall.enqueue(new Callback<RecipeList>() {
                @Override
                public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {
                    mRequestsFinished++;
                    if (response.isSuccessful()) {
                        mFinalList.getRecipes().addAll(response.body().getRecipes());
                    }
                    if (mRequestsFinished == (mMaxResults / PAGE_SIZE)) {
                            result.setValue(mFinalList);
                    }
                }

                @Override
                public void onFailure(Call<RecipeList> call, Throwable t) {
                    mRequestsFinished++;
                    if (mRequestsFinished == (mMaxResults / PAGE_SIZE)) {
                        result.setValue(mFinalList);
                    }
                }
            });
        }

        return result;
    }

    public void setMaxResults(int maxResults) {
        mMaxResults = maxResults;
    }

    private void cancelOutgoingCalls() {
        if (mCurrentRecipeListRequest != null) {
            for (Call<RecipeList> call : mCurrentRecipeListRequest) {
                call.cancel();
            }
        }
        mCurrentRecipeListRequest.clear();
    }
}
