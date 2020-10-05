package com.example.recipes.User.My;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.recipes.R;
import com.example.recipes.Recipes.Recipes;
import com.example.recipes.Recipes.RecipesContentActivity;
import com.example.recipes.Recipes.RecipesDAO;
import com.example.recipes.Shouye.RecipesAdapter;
import com.example.recipes.User.User;
import com.example.recipes.User.UserDAO;

import java.util.List;

public class MyrecipesActivity extends Activity {
    private List<Recipes> recipesList;
    private ListView lv;
    private ImageView img_back;
    String id;

    private String uri = "content://media/external/images/media/646";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_myrecipes);

        SharedPreferences sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("IsLogin", false);
        id = sp.getString("id", "123456");

        initview();
        initdata();
        RecipesAdapter adapter = new RecipesAdapter(this, recipesList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipes recipes = recipesList.get(position);
                Intent intent = new Intent(MyrecipesActivity.this, RecipesContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipes", recipes);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView img_touxiang = (ImageView) findViewById(R.id.myrecipes_touxiang);
        TextView user_name = (TextView) findViewById(R.id.myrecipes_name);

        if (isLogin) {
            UserDAO userDAO = new UserDAO(this);
            User user = userDAO.find(id);
            img_touxiang.setImageURI(Uri.parse(user.getPhoto_uri()));
            user_name.setText(user.getName());
        }

    }

    private void initview() {
        lv = (ListView) findViewById(R.id.myrecipes_list);
        img_back = (ImageView) findViewById(R.id.recipes_back);
    }

    private void initdata() {
        RecipesDAO recipesDAO = new RecipesDAO(MyrecipesActivity.this);
        recipesList = recipesDAO.getByWriterId(id);
    }
}
