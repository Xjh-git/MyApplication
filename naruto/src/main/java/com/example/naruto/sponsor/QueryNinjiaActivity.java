package com.example.naruto.sponsor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.naruto.Adapter.NinjiaAdapter;
import com.example.naruto.R;
import com.example.naruto.Table.Ninjia;
import com.example.naruto.Table.NinjiaDAO;

import java.util.ArrayList;
import java.util.List;

public class QueryNinjiaActivity extends Activity {
    List<Ninjia> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_ninjia);

        init();


    }

    private void init() {
        NinjiaDAO ninjiaDAO = new NinjiaDAO(QueryNinjiaActivity.this);
        list = ninjiaDAO.query();


        ListView listView = (ListView) findViewById(R.id.query_ninjia_list);
        NinjiaAdapter adapter = new NinjiaAdapter(QueryNinjiaActivity.this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ninjia ninjia = list.get(position);
                Intent intent = new Intent(QueryNinjiaActivity.this, SpNinjiaDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ninjia", ninjia);
                intent.putExtras(bundle);
                startActivityForResult(intent, 11);
            }
        });


        TextView back = (TextView) findViewById(R.id.sponsor_query_ninjia_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11 && resultCode == RESULT_OK) {
            init();
        }
    }
}
