package com.example.recipes.works;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipes.R;
import com.example.recipes.Recipes.Recipes;
import com.example.recipes.Recipes.RecipesDAO;
import com.example.recipes.Shouye.RecipesAdapter;

import java.util.ArrayList;
import java.util.List;

public class LinkRecipesActivity extends Activity {
    TextView cancelx_tv;
    TextView finishx_tv;
    EditText references_et;
    ListView recipes_link_lv;
    TextView linkrecipes_name;
    ImageView clear;
    String str, str1;//要进行模糊搜索用到的字符串
    RecipesDAO recipesDAO;
    List<Recipes> recipesList;
    RecipesAdapter adapter;
    Recipes recipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_link_recipes);

        linkrecipes_name = (TextView) findViewById(R.id.linkrecipes_name_tv);

        cancelx_tv = (TextView) findViewById(R.id.cancelx_tv);
        cancelx_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clear = (ImageView) findViewById(R.id.clear_iv);//清空EditText文本
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                references_et.getText().clear();
            }
        });

        recipesDAO = new RecipesDAO(LinkRecipesActivity.this);
        recipesList = new ArrayList<>();

        /*从列表item里获取菜谱名，
        然后显示到edittext,edittext本身具有查找功能，只要内容不为空就可以查找到相关内容，
        完成后把菜谱名回传到关联菜谱的参考菜谱textview中*/

        //从列表item里获取菜谱名，显示到edittext
        recipes_link_lv = (ListView) findViewById(R.id.recipes_link_lv);
        adapter = new RecipesAdapter(LinkRecipesActivity.this, recipesList);
        recipes_link_lv.setAdapter(adapter);
        recipes_link_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (recipesList.size() > 0) {
                    recipes = recipesList.get(position);
                    linkrecipes_name.setText(recipes.getName());
                }
            }
        });


        references_et = (EditText) findViewById(R.id.references_et);
        references_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //实时获取EditText输入的文本，模糊搜索用
                str = s.toString();
                //                linkrecipes_name.setText(str);
                //在该改变事件里调用查询函数，可以即输即查
                if (!str.equals("")) {
                    clear.setVisibility(View.VISIBLE);

                } else {
                    clear.setVisibility(View.INVISIBLE);
                }

                recipesList = recipesDAO.getIdByName(str);

                adapter = new RecipesAdapter(LinkRecipesActivity.this, recipesList);
                recipes_link_lv.setAdapter(adapter);
                if (recipesList.size() > 0) {
                    recipes = recipesList.get(0);
                    linkrecipes_name.setText(recipes.getName() + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //菜谱id回传到关联菜谱的参考菜谱textview中
        finishx_tv = (TextView) findViewById(R.id.finishx_tv);
        finishx_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str1 = linkrecipes_name.getText().toString();
                if (!str1.equals("")) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("Rid", recipes.getRid());
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(LinkRecipesActivity.this, "关联菜谱不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


}
