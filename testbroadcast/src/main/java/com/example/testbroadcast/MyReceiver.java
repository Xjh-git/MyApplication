package com.example.testbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        if (intent.getAction().equals("wust.zz.myboradcast")){
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
            Toast.makeText(context,a+fu+b+"="+result,Toast.LENGTH_SHORT).show();
        }
    }
}
