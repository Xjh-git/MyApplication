package com.example.recipes.classify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.recipes.R;

import java.util.ArrayList;
import java.util.List;

public class ClassifyActivity extends Activity {
    private List<imageCL> clsImage1 = new ArrayList<>();
    private List<imageCL> clsImage2 = new ArrayList<>();
    private List<imageCL> clsImage3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_classify);
        //        ActionBar actionBar=getSupportActionBar();
        //        actionBar.hide();

        init1();
        init2();
        init3();

        /**
         * 所有案件响应事件均为测试数据，测试数据传输是否正确
         *
         */
        GridView gridView1 = (GridView) findViewById(R.id.classifyGV);
        imageAdapter imageAd = new imageAdapter(this, clsImage1);
        gridView1.setAdapter(imageAd);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClassifyActivity.this, SortListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("sort", clsImage1.get(position).getImage());
                intent.putExtras(bundle);
                startActivity(intent);
                //                Toast.makeText(ClassifyActivity.this, "Click 热门菜单" + position, Toast.LENGTH_SHORT).show();

            }
        });

        GridView gridView2 = (GridView) findViewById(R.id.classifyGV2);
        imageAdapter imageAd2 = new imageAdapter(this, clsImage2);
        gridView2.setAdapter(imageAd2);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClassifyActivity.this, SortListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("sort", clsImage2.get(position).getImage());
                intent.putExtras(bundle);
                startActivity(intent);
                //                Toast.makeText(ClassifyActivity.this, "Click 菜式" + position, Toast.LENGTH_SHORT).show();
            }
        });

        GridView gridView3 = (GridView) findViewById(R.id.classifyGV3);
        imageAdapter imageAd3 = new imageAdapter(this, clsImage3);
        gridView3.setAdapter(imageAd3);
        gridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClassifyActivity.this, SortListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("sort", clsImage3.get(position).getImage());
                intent.putExtras(bundle);
                startActivity(intent);
                //                Toast.makeText(ClassifyActivity.this, "Click 场景" + position, Toast.LENGTH_SHORT).show();
            }
        });


        ImageView classify_back = (ImageView) findViewById(R.id.classify_back);
        classify_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * init中更新数据均为测试数据，最后需修改
     * init1，init2，init3分别对应三个分类栏，分别进行数据导入
     */
    private void init1() {

        String string[] = new String[]{
                "中式",
                "日式",
                "法式",
                "素食",
                "甜品",
                "蛋糕",
                "荤菜",
                "饮品"
        };


        imageCL imageCL;
        for (int i = 0; i < string.length; i++) {
            imageCL = new imageCL(string[i]);
            clsImage1.add(imageCL);
        }

    }

    private void init2() {
        String string[] = new String[]{
                "中式",
                "日式",
                "法式",
                "素食",
                "甜品",
                "蛋糕",
                "荤菜",
                "饮品"
        };


        imageCL imageCL;
        for (int i = 0; i < string.length; i++) {
            imageCL = new imageCL(string[i]);
            clsImage2.add(imageCL);
        }
        //        imageCL image1 = new imageCL(R.drawable.fruit);
        //        imageCL image2 = new imageCL(R.drawable.fruit);
        //        imageCL image3 = new imageCL(R.drawable.fruit);
        //        imageCL image4 = new imageCL(R.drawable.fruit);
        //        imageCL image5 = new imageCL(R.drawable.fruit);
        //        imageCL image6 = new imageCL(R.drawable.fruit);
        //        clsImage2.add(image1);
        //        clsImage2.add(image2);
        //        clsImage2.add(image3);
        //        clsImage2.add(image4);
        //        clsImage2.add(image5);
        //        clsImage2.add(image6);
    }

    private void init3() {
        String string[] = new String[]{
                "中式",
                "日式",
                "法式",
                "素食",
                "甜品",
                "蛋糕",
                "荤菜",
                "饮品"
        };


        imageCL imageCL;
        for (int i = 0; i < string.length; i++) {
            imageCL = new imageCL(string[i]);
            clsImage3.add(imageCL);
        }
        //        imageCL image1 = new imageCL(R.drawable.icecream);
        //        imageCL image2 = new imageCL(R.drawable.icecream);
        //        imageCL image3 = new imageCL(R.drawable.icecream);
        //        imageCL image4 = new imageCL(R.drawable.icecream);
        //        imageCL image5 = new imageCL(R.drawable.icecream);
        //        clsImage3.add(image1);
        //        clsImage3.add(image2);
        //        clsImage3.add(image3);
        //        clsImage3.add(image4);
        //        clsImage3.add(image5);
    }
}
