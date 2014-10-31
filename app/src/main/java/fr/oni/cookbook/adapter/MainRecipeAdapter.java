package fr.oni.cookbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.oni.cookbook.R;
import fr.oni.cookbook.model.Recipe;

public class MainRecipeAdapter extends BaseAdapter {

    private List<Recipe> recipes;

    private LayoutInflater inflater;

    public MainRecipeAdapter(Context context, List<Recipe> recipes) {
        this.inflater = LayoutInflater.from(context);
        this.recipes = recipes;
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.recipe_list_linear_layout, parent, false);
            holder.tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.tvTitle.setText(recipes.get(position).getTitle());

        return v;
    }

    private static class ViewHolder {
        TextView tvTitle;
    }

}
