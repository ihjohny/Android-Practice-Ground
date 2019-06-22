package com.example.undefined.custombroadcastsender;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.text_view);
    }

    public void sendBroadcast(View view) {
        //Intent intent = new Intent(this,ExampleBroadcastReceiver.class);
        Intent intent=new Intent();
        //intent.setClass(this,ExampleBroadcastReceiver.class);
        ComponentName componentName= new ComponentName("com.example.undefined.custombroadcastdemo",
                "com.example.undefined.custombroadcastdemo.ExBroadcastReceiver");
        intent.setComponent(componentName);
        sendBroadcast(intent);
    }

/*    public void sendBroadcast(View view) {
        Intent intent = new Intent("com.example.undefined.EXAMPLE_ACTION");
        intent.putExtra("com.example.undefined.EXTRA_TEXT","Broadcast Received");
        sendBroadcast(intent);
    }*/
/*    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String receiverText = intent.getStringExtra("com.example.undefined.EXTRA_TEXT");
            textView.setText(receiverText);
        }
    };*/
/*    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("com.example.undefined.EXAMPLE_ACTION");
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }*/
}
