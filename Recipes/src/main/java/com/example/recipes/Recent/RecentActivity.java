package com.example.recipes.Recent;

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

public class RecentActivity extends Activity {
    private List<Recipes> recentProList = new ArrayList<Recipes>();
    private String uri = "content://media/external/images/media/646";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_recent);

        ImageView backButton = (ImageView) findViewById(R.id.back_image);
        //4ImageView addButton = (ImageView) findViewById(R.id.add_button);

        init();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Recent_Recycle);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecentAdapter adapter = new RecentAdapter(this, recentProList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void OnClick(int position, View v) {
                /**
                 * 测试数据
                 *
                 */
                Recipes recipes = recentProList.get(position);
                Intent intent = new Intent(RecentActivity.this, RecipesContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipes", recipes);
                intent.putExtras(bundle);
                startActivity(intent);
//                Toast.makeText(RecentActivity.this, "nnnnn" + position, Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 测试按钮用
                 *
                 */

                finish();

                //                Toast.makeText(getApplicationContext(), "PressBack", Toast.LENGTH_SHORT).show();
            }
        });

//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /**
//                 * 测试按钮用
//                 *
//                 */
//                Toast.makeText(getApplicationContext(), "PressAdd", Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    private void init() {
        RecipesDAO recipesDAO = new RecipesDAO(this);
        recentProList = recipesDAO.get();
    }

    //    private void init() {
    //        for (int i = 0; i < 5; i++) {
    //            Recipes recentPro = new Recipes(i,uri,"recent"+i,"ingredient","progress","tip","sort","time","lisi");
    //            recentProList.add(recentPro);
    //        }
    //    }
}
