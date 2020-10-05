package com.example.recipes.classify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.recipes.R;
import com.example.recipes.Recipes.Recipes;
import com.example.recipes.Recipes.RecipesContentActivity;
import com.example.recipes.Recipes.RecipesDAO;
import com.example.recipes.Shouye.RecipesAdapter;

import java.util.List;

public class SortListActivity extends Activity {
    List<Recipes> recipesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sort_list);

        ImageView img=(ImageView)findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String sort = bundle.getString("sort");
        RecipesDAO recipesDAO = new RecipesDAO(this);
        recipesList = recipesDAO.getBySort(sort);
        RecipesAdapter adapter = new RecipesAdapter(this, recipesList);
        ListView listView = (ListView) findViewById(R.id.sort_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipes recipes = recipesList.get(position);
                Intent intent = new Intent(SortListActivity.this, RecipesContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipes", recipes);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
