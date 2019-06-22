package com.example.undefined.servicesdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startService,stopService,bindService,unBindService,gerRandomNumber;
    private TextView randomNumberDisplay;
    private Intent intentService;

    private LocalBindingService Myservice;
    private boolean isServiceBound;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService=findViewById(R.id.startService);
        stopService=findViewById(R.id.stopService);
        bindService=findViewById(R.id.bindService);
        unBindService=findViewById(R.id.unbingService);
        gerRandomNumber=findViewById(R.id.getRandomNumber);
        randomNumberDisplay=findViewById(R.id.randomNumberDisplay);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unBindService.setOnClickListener(this);
        gerRandomNumber.setOnClickListener(this);

        Log.i("Service","In MainActivity Thread id: "+Thread.currentThread().getId());
        intentService=new Intent(getApplicationContext(),LocalBindingService.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.startService){

            startService(intentService);
        }
        else if(v.getId()==R.id.stopService){
            stopService(intentService);
        }
        else if(v.getId()==R.id.bindService){
            setBindService();
        }
        else if(v.getId()==R.id.unbingService){
            setUnBindService();
        }
        else if(v.getId()==R.id.getRandomNumber){
            setRandomNumber();
        }
    }

    private void setBindService() {
        if(serviceConnection==null){
            serviceConnection=new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    LocalBindingService.ServiceBinder serviceBinder= (LocalBindingService.ServiceBinder) service;
                    Myservice=serviceBinder.getService();
                    isServiceBound=true;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isServiceBound=false;
                }
            };
            bindService(intentService,serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }
    private void setUnBindService() {
        if(isServiceBound)
        {
            unbindService(serviceConnection);
            isServiceBound=false;
        }

    }
    private void setRandomNumber() {
        if(isServiceBound)
        {
            randomNumberDisplay.setText("Random Number : "+Myservice.getRandomNumber());
        }
        else
            randomNumberDisplay.setText("Service is not Bound");

    }
}
