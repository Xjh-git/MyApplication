package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Jisuan2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jisuan2);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        String a=bundle.getString("a");
        String b=bundle.getString("b");
        String fu=bundle.getString("fu");
        Double result=0.0;

        switch (fu){
            case "+":
                result=Double.parseDouble(a)+Double.parseDouble(b);
                break;
            case "-":
                result=Double.parseDouble(a)-Double.parseDouble(b);
                break;
            case "*":
                result=Double.parseDouble(a)*Double.parseDouble(b);
                break;
            case "/":
                result=Double.parseDouble(a)/Double.parseDouble(b);
                break;
                default:break;
        }

        bundle.clear();
        bundle.putDouble("result",result);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }
}
