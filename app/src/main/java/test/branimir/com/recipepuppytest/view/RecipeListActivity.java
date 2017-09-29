package test.branimir.com.recipepuppytest.view;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.Toast;

import test.branimir.com.recipepuppytest.R;
import test.branimir.com.recipepuppytest.service.model.Recipe;
import test.branimir.com.recipepuppytest.service.model.RecipeList;
import test.branimir.com.recipepuppytest.viewmodel.ListRecipeViewModel;

public class RecipeListActivity extends LifecycleActivity implements Observer<RecipeList>,RecipesAdapter.OnListClickListener {

    /** Time that has to pass before we issue a new query*/
    private static final long TIME_THRESHOLD = 750;

    /** Handler used to execute runnable if enough time has passed after last text input from user*/
    private Handler mHandler = new Handler();

    /** Current query*/
    private String mQuery;

    /** Recycler view dfsaf*/
    private RecyclerView mRecyclerView;

    /** Recipes adapter*/
    private RecipesAdapter mRecipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);


        ListRecipeViewModel listModelView = ViewModelProviders.of(this).get(ListRecipeViewModel.class);
        listModelView.getRecipeListObservable().observe(this, this);

        Runnable task = () -> listModelView.setQuery(mQuery);

        SearchView sw = findViewById(R.id.search_view);
        sw.setQueryHint(getString(R.string.hint));
        sw.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Since we are issuing network request, we want to send them when we are
                //sure user stopped typing, instead of sending it for each change
                mQuery = s;
                mHandler.removeCallbacks(task);
                mHandler.postDelayed(task, TIME_THRESHOLD);
                return true;
            }
        });

        mRecyclerView = findViewById(R.id.recyclerview_recipes);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecipesAdapter = new RecipesAdapter(RecipeListActivity.this);
        mRecyclerView.setAdapter(mRecipesAdapter);
    }

    @Override
    public void onChanged(@Nullable RecipeList recipeList) {
        mRecipesAdapter.setRecipes(recipeList.getRecipes());
    }

    /** In the future, can be used to start a new Activity/Add fragment that shows Details of the Recipe*/
    @Override
    public void onListItemClicked(Recipe recipeClicked) {
        Toast.makeText(this, "Recipe " + recipeClicked.getTitle() + " has ingredients: " + recipeClicked.getIngredients(), Toast.LENGTH_LONG).show();
    }
}
