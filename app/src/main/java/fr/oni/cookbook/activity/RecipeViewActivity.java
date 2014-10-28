package fr.oni.cookbook.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import fr.oni.cookbook.R;
import fr.oni.cookbook.StringConstant;
import fr.oni.cookbook.adapter.RecipeViewPagerAdapter;
import fr.oni.cookbook.dialog.view.DeleteRecipeConfirmDialogFragment;
import fr.oni.cookbook.model.Recipe;

public class RecipeViewActivity extends AbstractActivity {

  static final int EDIT_RECIPE_REQUEST = 1;

  private Recipe recipe;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.view_menu, menu);

    MenuItem shareItem = menu.findItem(R.id.action_share);

    final ShareActionProvider shareActionProvider =
        (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
    shareActionProvider.setShareIntent(getShareIntent());

    return true;
  }

  private Intent getShareIntent() {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          setFlagNewDocument(intent);
      } else {
          setFlagClearWhenTaskReset(intent);
      }

    intent.putExtra(Intent.EXTRA_SUBJECT, recipe.getTitle());
    intent.putExtra(Intent.EXTRA_TEXT, recipe.toString());

    return intent;
  }

    @SuppressWarnings("deprecation")
    private void setFlagClearWhenTaskReset(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    }

    @TargetApi(21)
    private void setFlagNewDocument(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    }

    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    recipe = data.getRecipes().get(data.getPosition());
    viewPager.setAdapter(new RecipeViewPagerAdapter(getSupportFragmentManager(), recipe,
        StringConstant.KEY_RECIPE, getApplicationContext()));
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_edit:
        editRecipe();
        return true;
      case R.id.action_delete:
        deleteRecipe();
        return true;

      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void deleteRecipe() {
    DeleteRecipeConfirmDialogFragment dialog = new DeleteRecipeConfirmDialogFragment();
    dialog.show(getSupportFragmentManager(), StringConstant.TAG_DELETE);
  }

  private void editRecipe() {
    Intent intent = new Intent(getApplicationContext(), EditActivity.class);
    startActivityForResult(intent, EDIT_RECIPE_REQUEST);
  }



}
