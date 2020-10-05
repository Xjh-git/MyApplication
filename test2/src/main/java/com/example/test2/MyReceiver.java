package com.example.test2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MyReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //        throw new UnsupportedOperationException("Not yet implemented");
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {

            Intent intent1=new Intent(context,MyService.class);
            context.startService(intent1);

        }

    }

}
