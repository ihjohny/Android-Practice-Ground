package com.example.undefined.custombroadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ExBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
/*        if("com.example.undefined.EXAMPLE_ACTION".equals(intent.getAction())){
            String receivedText = intent.getStringExtra("com.example.undefined.EXTRA_TEXT");
            Toast.makeText(context,receivedText,Toast.LENGTH_SHORT).show();
        }*/
        Toast.makeText(context,"ExBroadcastReceiver Triggred",Toast.LENGTH_SHORT).show();
    }
}
