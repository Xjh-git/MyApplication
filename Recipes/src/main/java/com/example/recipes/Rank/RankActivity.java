package com.example.recipes.Rank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.recipes.R;
import com.example.recipes.Recipes.Recipes;
import com.example.recipes.Recipes.RecipesContentActivity;
import com.example.recipes.Recipes.RecipesDAO;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends Activity {
    private List<Recipes> productionList = new ArrayList<Recipes>();
    private String uri = "content://media/external/images/media/646";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rank);

        ImageView backButton=(ImageView) findViewById(R.id.back_image);
        //ImageView addButton=(ImageView) findViewById(R.id.add_button);

        init();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        RecyclerView recycle=(RecyclerView)findViewById(R.id.product_Recycle);
        recycle.setLayoutManager(linearLayoutManager);
        proAdapter adapter=new proAdapter(this,productionList);
        recycle.setAdapter(adapter);
        /**
         * RecyclerView条目点击事件
         */
        adapter.setOnItemClickListener(new proAdapter.OnItemClickListener() {
            @Override
            public void OnCLick(int postion, View v) {

                Recipes recipes = productionList.get(postion);
                Intent intent = new Intent(RankActivity.this, RecipesContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipes", recipes);
                intent.putExtras(bundle);
                startActivity(intent);
//                Toast.makeText(RankActivity.this, "xxx"+postion, Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Toast.makeText(getApplicationContext(),"PressBack" ,Toast.LENGTH_SHORT).show();
            }
        });
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                /**
//                 * 此处按钮用于测试
//                 * 点击后直接指向 最新发布界面
//                 * 需修改点击事件
//                 */
//                Intent intent= new Intent(RankActivity.this, RecentActivity.class);
//                startActivity(intent);
//
//            }
//        });
    }
    private void init() {
        RecipesDAO recipesDAO = new RecipesDAO(this);
        productionList = recipesDAO.get();
    }

//    private void init(){
//        for (int i=0;i<5;i++){
//            Recipes recentPro = new Recipes(i,uri,"recent"+i,"ingredient","progress","tip","sort","time","lisi");
//            productionList.add(recentPro);
//        }
//    }
}
