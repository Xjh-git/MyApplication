package com.example.naruto.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.naruto.Adapter.MijuanAdapter;
import com.example.naruto.R;
import com.example.naruto.Table.Mijuan;
import com.example.naruto.Table.MijuanDAO;

import java.util.ArrayList;
import java.util.List;

public class UserMijuanActivity extends Activity {
    List<Mijuan> list = new ArrayList<>();
    TextView back;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mijuan);

        init();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void init() {
        back = (TextView) findViewById(R.id.user_mijuan_back);

        MijuanDAO mijuanDAO = new MijuanDAO(UserMijuanActivity.this);
        list = mijuanDAO.query();

        listView = (ListView) findViewById(R.id.user_mijuan);
        MijuanAdapter adapter = new MijuanAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mijuan mijuan = list.get(position);
                Intent intent = new Intent(UserMijuanActivity.this, MijuanDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mijuan", mijuan);
                intent.putExtras(bundle);
                startActivityForResult(intent, 111);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 111) {
            init();
        }
    }
}
