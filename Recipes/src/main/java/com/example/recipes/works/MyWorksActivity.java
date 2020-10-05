package com.example.recipes.works;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.recipes.R;

import java.util.List;

public class MyWorksActivity extends Activity {

    ImageView back_tv;
    ListView works_lv;
    List<work> workList;
    workDAO workDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_works);

        back_tv = (ImageView) findViewById(R.id.back_works_iv);
        back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        workDAO = new workDAO(this);
        works_lv = (ListView) findViewById(R.id.works_lv);
        workList = workDAO.get();
        WorkAdapter adapter = new WorkAdapter(this, workList);
        works_lv.setAdapter(adapter);
        /*

         */
        works_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Recipes recipes = workList.get(position);
//                Intent intent = new Intent(MyWorksActivity.this, RecipesContentActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("recipes", recipes);
//                intent.putExtras(bundle);
//                startActivity(intent);
            }
        });
    }
}
