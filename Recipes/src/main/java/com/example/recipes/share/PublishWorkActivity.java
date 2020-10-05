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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipes.R;
import com.example.recipes.Recipes.Recipes;
import com.example.recipes.Recipes.RecipesDAO;
import com.example.recipes.works.LinkRecipesActivity;
import com.example.recipes.works.work;
import com.example.recipes.works.workDAO;

import java.util.ArrayList;
import java.util.List;

public class PublishWorkActivity extends Activity {

    private ImageView works_pic;
    private ImageView back_iv;
    Uri works_pic_uri = null;
    private TextView publish_works_tv;

    private TextView linkrecipes;

    private TextView addlabel;
    List<String> tagchoices = new ArrayList<>();
    int size;
    String tags = "", writer;
    int recipesId = -1;
    Recipes recipes;
    RecipesDAO recipesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_publish_works);

        recipesDAO = new RecipesDAO(this);

        back_iv = (ImageView) findViewById(R.id.back_iv);
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        publish_works_tv = (TextView) findViewById(R.id.publish_works_tv);
        publish_works_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publish_works();
            }
        });

        addlabel = (TextView) findViewById(R.id.addlabel_tv);
        addlabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMultiChoiceDialog();

            }
        });

        linkrecipes = (TextView) findViewById(R.id.linkrecipes_tv);

        works_pic = (ImageView) findViewById(R.id.works_pic);
        works_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 0x2);
            }
        });

        //linearlayout点击事件——关联菜谱
        View link_recipes = (View) findViewById(R.id.link_recipes);
        link_recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishWorkActivity.this, LinkRecipesActivity.class);
                startActivityForResult(intent, 0x0);
            }
        });


    }


    //发布即插入数据库
    private void publish_works() {
        SharedPreferences sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        String id = sp.getString("id", "123456");
        writer = id;
        if (works_pic_uri != null) {
            if (!tags.equals("")) {
                if (recipesId != -1) {
                    work work = new work(works_pic_uri.toString(), recipesId, tags, writer);
                    workDAO workDAO = new workDAO(PublishWorkActivity.this);
                    workDAO.add(work);
                    Toast.makeText(PublishWorkActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PublishWorkActivity.this, "请选择关联的菜谱", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(PublishWorkActivity.this, "请选择标签", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PublishWorkActivity.this, "请拍下作品的照片", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x2 && resultCode == RESULT_OK) {
            if (data != null) {
                works_pic.setImageURI(data.getData());
                works_pic_uri = data.getData();
            }
        }
        if (requestCode == 0x0)
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                //                String s = bundle.getString("str1");
                recipesId = bundle.getInt("Rid");
                recipes = recipesDAO.find(recipesId);
                linkrecipes.setText("@" + recipes.getName());
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showMultiChoiceDialog() {
        final String[] items = {"早餐", "午餐", "晚餐", "甜品", "颜即正义", "素食主义"};
        //设置默认选中的选项，全为false默认均未选中
        final boolean initChoiceSets[] = {false, false, false, false, false, false};
        tagchoices.clear();
        final AlertDialog.Builder multiChoiceDialog = new AlertDialog.Builder(PublishWorkActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        multiChoiceDialog.setIcon(R.drawable.leaf);
        multiChoiceDialog.setTitle("标签");
        multiChoiceDialog.setCancelable(false);
        multiChoiceDialog.setMultiChoiceItems(items, initChoiceSets, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    if (tagchoices.size() < 3) {
                        tagchoices.add(items[which]);//加对象
                    } else {
                        Toast.makeText(PublishWorkActivity.this, "标签选择不多于3个，超过按3个处理", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    tagchoices.remove(items[which]);
                }
            }
        });
        multiChoiceDialog.setNegativeButton("取消", null);
        multiChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                size = tagchoices.size();
                String str = "";
                for (int i = 0; i < size; i++) {
                    str += "#" + tagchoices.get(i) + "#";//输出集合中的元素
                    tags=str;

                }
                if (str.equals("")) {
                    Toast.makeText(PublishWorkActivity.this, "未选择任何标签", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PublishWorkActivity.this, "你选中了:" + str, Toast.LENGTH_SHORT).show();
                    addlabel.setText(str);
                }
            }
        }).create();
        multiChoiceDialog.show();

    }
}
