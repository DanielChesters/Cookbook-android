package fr.oni.cookbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.oni.cookbook.R;
import fr.oni.cookbook.activity.RecipeViewActivity;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Recipe;

public class MainRecipeAdapter extends RecyclerView.Adapter<MainRecipeAdapter.ViewHolder> {

    private List<Recipe> recipes;

    public MainRecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        TextView tvTitle = (TextView) LayoutInflater.from(context).inflate(R.layout.recipe_list_linear_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(context, tvTitle);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.tvTitle.setText(recipes.get(position).getTitle());
        viewHolder.position = position;
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Context context;
        int position;
        TextView tvTitle;

        public ViewHolder(Context context, TextView itemView) {
            super(itemView);
            this.context = context;
            this.tvTitle = itemView;
            tvTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Data data = (Data) context.getApplicationContext();
            Intent intent = new Intent(context.getApplicationContext(), RecipeViewActivity.class);
            data.setPosition(position);
            context.startActivity(intent);
        }
    }

}
