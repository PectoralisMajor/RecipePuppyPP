package test.branimir.com.recipepuppytest.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import test.branimir.com.recipepuppytest.R;
import test.branimir.com.recipepuppytest.service.model.Recipe;

/**
 * Created by bconjar on 14/08/17.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeAdapterViewHolder> {

    public interface OnListClickListener {
        void onListItemClicked(Recipe recipeClicked);
    }

    private List<Recipe> mRecipes;
    OnListClickListener mOnListClickListener;

    public RecipesAdapter(OnListClickListener listener) {
        mOnListClickListener = listener;
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int resId = R.layout.recipe_list_item;
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(resId, parent, false);
        return new RecipeAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        Recipe currentRecipe = mRecipes.get(position);
        holder.mRecipeTitleTextView.setText(currentRecipe.getTitle());
    }

    @Override
    public int getItemCount() {
        return mRecipes != null ? mRecipes.size() : 0;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mRecipeTitleTextView;

        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);

            mRecipeTitleTextView = (TextView) itemView.findViewById(R.id.tv_recipe_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnListClickListener.onListItemClicked(mRecipes.get(position));
        }
    }
}
