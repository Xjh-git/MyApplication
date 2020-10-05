package com.example.recipes.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.recipes.R;
import com.example.recipes.Recipes.Recipes;
import com.example.recipes.Recipes.RecipesContentActivity;
import com.example.recipes.Recipes.RecipesDAO;
import com.example.recipes.Shouye.RecipesAdapter;

import java.util.List;

public class SearchActivity extends Activity {
    private Button cancel_edit_bt;
    private EditText search_ed;
    private ListView recipes_lv;
    private RecipesDAO recipesDAO;
    private ImageView clear;
    List<Recipes> recipesList;
    String string;

    //    private ImageView back_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        string = bundle.getString("search");

        recipesDAO = new RecipesDAO(this);

        clear=(ImageView)findViewById(R.id.clear1_iv);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_ed.getText().clear();
            }
        });

        recipes_lv = (ListView) findViewById(R.id.recipes_lv);
        search_ed = (EditText) findViewById(R.id.search_et);
        search_ed.setText(string);
        search_ed.setSelection(string.length());
        recipesList = recipesDAO.getIdByName(string);
        RecipesAdapter adapter = new RecipesAdapter(SearchActivity.this, recipesList);
        recipes_lv.setAdapter(adapter);
        search_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                //在该改变事件里调用查询函数，可以即输即查
                //                if (!str.equals("")) {
                //                    clear.setVisibility(View.VISIBLE);

                if (!str.equals("")) {
                    clear.setVisibility(View.VISIBLE);

                } else {
                    clear.setVisibility(View.INVISIBLE);
                }
                recipesList = recipesDAO.getIdByName(str);
                RecipesAdapter adapter = new RecipesAdapter(SearchActivity.this, recipesList);
                recipes_lv.setAdapter(adapter);

                //                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        recipes_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipes recipes = recipesList.get(position);
                Intent intent = new Intent(SearchActivity.this, RecipesContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipes", recipes);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        cancelsearch();
        //        back_iv=(ImageView)findViewById(R.id.back_iv);
        //        back_iv.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                finish();
        //            }
        //        });

    }

    private void cancelsearch() {
        cancel_edit_bt = (Button) findViewById(R.id.cancel_bt);
        cancel_edit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideInputMethod(getApplicationContext(), v);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    //取消输入法并清空搜索编辑框
    private void hideInputMethod(Context context, View view) {
        //清空编辑框
        search_ed.getText().clear();
        //关闭输入法
        InputMethodManager inputMethodManager = (InputMethodManager)
                context.getSystemService(context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
