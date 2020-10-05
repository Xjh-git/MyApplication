package com.example.recipes.share;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recipes.R;
import com.example.recipes.Recipes.Recipes;
import com.example.recipes.Recipes.RecipesDAO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateRecipesActivity extends Activity {
    private String uri = "content://media/external/images/media/646";

    ImageView imageView;
    Uri recipes_pic_uri;
    TextView release_time, cancel, tv_sort;
    EditText ed_name, ed_ingredient, ed_progress, ed_tips;
    String name, ingredient, progress, tips, sort, releasetime, writer;
    int score;
    Button release_bt2;
    int yourchoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_recipes);
        init();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.cancel_tv:
                        finish();
                        break;
                    case R.id.recipespic_iv:
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 0x1);
                        break;
                    case R.id.release_bt2:

                        if (check()) {
                            SharedPreferences sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
                            String id = sp.getString("id", "123456");
                            writer = id;

                            Recipes recipes = new Recipes(recipes_pic_uri.toString(), name, ingredient, progress, tips, sort, releasetime, writer);
                            RecipesDAO recipesDAO = new RecipesDAO(CreateRecipesActivity.this);
                            recipesDAO.add(recipes);


                            Toast.makeText(CreateRecipesActivity.this, "发布成功", Toast.LENGTH_SHORT).show();

                            finish();

                        }
                        break;
                }
            }
        };

        cancel.setOnClickListener(listener);
        release_bt2.setOnClickListener(listener);
        imageView.setOnClickListener(listener);

    }


    private void init() {
        imageView = (ImageView) findViewById(R.id.recipespic_iv);
        release_time = (TextView) findViewById(R.id.releasetime_tv);
        release_time.setText("" + getCurrentTime());

        ed_name = (EditText) findViewById(R.id.recipesname_et);
        ed_ingredient = (EditText) findViewById(R.id.ingredient_et);
        ed_progress = (EditText) findViewById(R.id.progress_et);
        ed_tips = (EditText) findViewById(R.id.tips_et);
        tv_sort = (TextView) findViewById(R.id.sortname_tv);

        release_bt2 = (Button) findViewById(R.id.release_bt2);

        cancel = (TextView) findViewById(R.id.cancel_tv);

        View v = (View) findViewById(R.id.sort_view);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_sort();
            }
        });

        recipes_pic_uri=null;
    }

    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String str = format.format(date);
        return str;
    }

    private boolean check() {
        name = ed_name.getText().toString();
        ingredient = ed_ingredient.getText().toString();
        progress = ed_progress.getText().toString();
        tips = ed_tips.getText().toString();
        sort = tv_sort.getText().toString();
        releasetime = getCurrentTime();

        if (name.equals("")) {
            Toast.makeText(CreateRecipesActivity.this, "请输入菜名", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ingredient.equals("")) {
            Toast.makeText(CreateRecipesActivity.this, "请输入材料", Toast.LENGTH_SHORT).show();
            return false;
        } else if (progress.equals("")) {
            Toast.makeText(CreateRecipesActivity.this, "请输入步骤", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tips.equals("")) {
            Toast.makeText(CreateRecipesActivity.this, "请输入提示", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sort.equals("")) {
            Toast.makeText(CreateRecipesActivity.this, "请选择分类", Toast.LENGTH_SHORT).show();
            return false;
        }else if (recipes_pic_uri==null){
            Toast.makeText(CreateRecipesActivity.this, "请选择照片", Toast.LENGTH_SHORT).show();
            return false;
        }else  {
            return true;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                Glide.with(CreateRecipesActivity.this)
                        .load(data.getData())
                        .into(imageView);
                //                imageView.setImageURI(data.getData());
                recipes_pic_uri = data.getData();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void show_sort() {

        final String[] items = {"中式", "日式", "法式", "素食", "甜品", "蛋糕", "荤菜", "饮品"};
        yourchoice = -1;
        new AlertDialog.Builder(CreateRecipesActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setTitle("菜谱分类")
                //.setIcon(android.R.drawable.)
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        yourchoice = which;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourchoice != -1) {
                            tv_sort.setText(items[yourchoice]);
                            String str = tv_sort.getText().toString();
                            Toast.makeText(CreateRecipesActivity.this, "你选择了：" + str, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
    }


}
