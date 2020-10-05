package com.example.recipes.Recipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recipes.R;
import com.example.recipes.User.My.Collect;
import com.example.recipes.User.My.CollectDAO;
import com.example.recipes.User.User;
import com.example.recipes.User.UserDAO;
import com.example.recipes.share.PublishWorkActivity;

public class RecipesContentActivity extends Activity {

    private ImageView back;
    private ImageView like;
    private ImageView camera;
    private TextView collect_tv;
    private TextView publish_tv;
    Recipes recipes;
    CollectDAO collectDAO;
    int recipes_id;
    String user_id;
    Collect collect;
    View collect_view;
    View.OnClickListener listener1, listener2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_recipes_content);

        back = (ImageView) findViewById(R.id.back_iv);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        init();

        publish();

        collect();


    }

    private void init() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        recipes = (Recipes) bundle.getSerializable("recipes");

        ImageView recipes_pic = (ImageView) findViewById(R.id.recipes_pic);
        TextView recipes_name = (TextView) findViewById(R.id.recipes_name);
        //        ImageView writer_icon = (ImageView) findViewById(R.id.writer_icon);
        TextView writer_name = (TextView) findViewById(R.id.writer_name);
        TextView recipes_ingredient = (TextView) findViewById(R.id.recipes_ingredient);
        TextView progress = (TextView) findViewById(R.id.progress);
        TextView tips = (TextView) findViewById(R.id.tips);
        TextView recipes_release_info = (TextView) findViewById(R.id.recipes_release_info);

        Glide.with(this)
                .load(Uri.parse(recipes.getIcon_addr()))
                .into(recipes_pic);
        recipes_name.setText(recipes.getName());
        UserDAO userDAO = new UserDAO(this);
        User user = userDAO.find(recipes.getWriter());
        //        Glide.with(this)
        //                .load(Uri.parse(user.getPhoto_uri()))
        //                .into(writer_icon);
        writer_name.setText(user.getName());
        recipes_ingredient.setText(recipes.getIngredient());
        progress.setText(recipes.getProgress());
        tips.setText(recipes.getTips());
        recipes_release_info.setText(recipes.getReleasetime());

        collectDAO = new CollectDAO(RecipesContentActivity.this);
        recipes_id = recipes.getRid();
        SharedPreferences sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        user_id = sp.getString("id", "123456");
        collect = collectDAO.find(user_id, recipes_id);
        collect_view = (View) findViewById(R.id.collect);

        collect_tv = (TextView) findViewById(R.id.collect_tv);
    }

    public void collect() {

        listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collect = collectDAO.find(user_id, recipes_id);
                collectDAO.delete(collect);
                collect_tv.setText("收藏");
                collect_view.setOnClickListener(listener2);
            }
        };

        listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collect = new Collect(recipes_id, user_id);
                if (collectDAO.find(user_id, recipes_id) == null) {
                    collectDAO.add(collect);
                    collect_tv.setText("取消收藏");
                } else {
                    Toast.makeText(getApplicationContext(), "已经收藏过了", Toast.LENGTH_SHORT).show();
                }
                collect_view.setOnClickListener(listener1);
            }
        };

        if (collect != null) {
            //已经收藏，将收藏换成取消收藏
            collect_tv.setText("取消收藏");
            collect_view.setOnClickListener(listener1);
        } else {
            //还未收藏
            collect_tv.setText("收藏");
            collect_view.setOnClickListener(listener2);
        }
    }

    public void publish() {
        View publish = (View) findViewById(R.id.publish);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesContentActivity.this, PublishWorkActivity.class);
                startActivity(intent);
            }
        });
    }
}
