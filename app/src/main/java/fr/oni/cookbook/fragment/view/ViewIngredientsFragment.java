package fr.oni.cookbook.fragment.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.oni.cookbook.R;
import fr.oni.cookbook.StringConstant;
import fr.oni.cookbook.model.Ingredient;
import fr.oni.cookbook.model.Recipe;

public class ViewIngredientsFragment extends Fragment {

  private Recipe recipe;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle data = getArguments();
    recipe = (Recipe) data.getSerializable(StringConstant.KEY_RECIPE);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ListView v = (ListView) inflater.inflate(R.layout.view_recipe_ingredients, container, false);
    v.setAdapter(new ArrayAdapter<>(getActivity(),
        R.layout.ingredients_list_linear_layout, R.id.ingredient_text, recipe.getIngredients()));
    return v;
  }

}
