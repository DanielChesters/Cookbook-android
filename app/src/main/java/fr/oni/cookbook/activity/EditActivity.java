package fr.oni.cookbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import fr.oni.cookbook.R;
import fr.oni.cookbook.adapter.RecipeEditPagerAdapter;

public class EditActivity extends AbstractActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager.setAdapter(new RecipeEditPagerAdapter(getSupportFragmentManager(), getApplicationContext()));
    }

    private void onSave() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        setResult(RESULT_OK);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                onSave();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
