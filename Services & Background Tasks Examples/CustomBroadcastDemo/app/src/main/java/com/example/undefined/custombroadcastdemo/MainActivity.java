package com.example.undefined.custombroadcastdemo;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  //  ExBroadcastReceiver exBroadcastReceiver=new ExBroadcastReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        IntentFilter filter=new IntentFilter(" ");
        registerReceiver(exBroadcastReceiver,filter);*/
    }

/*    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(exBroadcastReceiver);
    }*/
}
