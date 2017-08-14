package test.branimir.com.recipepuppytest.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import test.branimir.com.recipepuppytest.service.model.RecipeList;
import test.branimir.com.recipepuppytest.service.repository.RecipeRepository;

/**
 * Created by bconjar on 14/08/17.
 */

public class ListRecipeViewModel extends ViewModel {

    LiveData<RecipeList> mRecipeListObservable;
    MutableLiveData<String> mQuery = new MutableLiveData<>();

    public ListRecipeViewModel() {
        mRecipeListObservable = Transformations.switchMap(mQuery, search -> RecipeRepository.getInstance().search(search));
    }

    public LiveData<RecipeList> getRecipeListObservable() {
        return mRecipeListObservable;
    }

    public void setQuery(String query) {
        mQuery.setValue(query);
    }
}
