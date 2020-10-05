package com.example.naruto.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.naruto.Adapter.NinjiaAdapter;
import com.example.naruto.R;
import com.example.naruto.Table.Ninjia;
import com.example.naruto.Table.NinjiaDAO;

import java.util.ArrayList;
import java.util.List;

public class UserNinjiaActivity extends Activity {

    List<Ninjia> ninjia_list = new ArrayList<>();
    TextView ninjia_name, back;
    ImageView ninjia_icon;
    LinearLayout detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ninjia);

        init();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void init() {
        ninjia_name = (TextView) findViewById(R.id.ninjia_name);
        ninjia_icon = (ImageView) findViewById(R.id.ninjia_icon);
        //        ninjia_fragment = (TextView) findViewById(R.id.user_ninjia_fragment);
        //        ninjia_fragment_progress = (ProgressBar) findViewById(R.id.user_ninjia_fragment_progressBar);
        //        ninjia_details = (TextView) findViewById(R.id.user_ninjia_details);
        detail = (LinearLayout) findViewById(R.id.user_ninjia_det);
        back = (TextView) findViewById(R.id.user_ninjia_back);

        NinjiaDAO ninjiaDAO = new NinjiaDAO(UserNinjiaActivity.this);
        ninjia_list = ninjiaDAO.query();

        ListView listView = (ListView) findViewById(R.id.user_ninjia);
        NinjiaAdapter adapter = new NinjiaAdapter(this, ninjia_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ninjia ninjia = ninjia_list.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ninjia", ninjia);
                Intent intent = new Intent(UserNinjiaActivity.this, NinjiaDetailsActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 123);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 123) {
            init();
        }
    }
}
