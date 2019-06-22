package com.example.undefined.remotebindingserviceside;

import android.content.Intent;
import android.content.ServiceConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startService,stopService;
    private Intent intentService;

    private Mservice Myservice;
    private boolean isServiceBound;
    private ServiceConnection serviceConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService=findViewById(R.id.startService);
        stopService=findViewById(R.id.stopService);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);

        Log.i("Service","In MainActivity Thread id: "+Thread.currentThread().getId());
        intentService=new Intent(getApplicationContext(),Mservice.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.startService){

            startService(intentService);
        }
        else if(v.getId()==R.id.stopService){
            stopService(intentService);
        }
    }
}
