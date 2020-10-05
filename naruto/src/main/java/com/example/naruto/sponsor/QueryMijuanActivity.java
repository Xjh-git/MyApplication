package com.example.naruto.sponsor;

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

public class QueryMijuanActivity extends Activity {
    List<Mijuan> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_mijuan);

        init();


    }

    private void init() {
        MijuanDAO mijuanDAO = new MijuanDAO(QueryMijuanActivity.this);
        list = mijuanDAO.query();


        ListView listView = (ListView) findViewById(R.id.query_mijuan_list);
        MijuanAdapter adapter = new MijuanAdapter(QueryMijuanActivity.this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mijuan mijuan = list.get(position);
                Intent intent = new Intent(QueryMijuanActivity.this, SpMijuanDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mijuan", mijuan);
                intent.putExtras(bundle);
                startActivityForResult(intent, 100);
            }
        });


        TextView back = (TextView) findViewById(R.id.sponsor_query_mijuan_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            init();
        }
    }
}
