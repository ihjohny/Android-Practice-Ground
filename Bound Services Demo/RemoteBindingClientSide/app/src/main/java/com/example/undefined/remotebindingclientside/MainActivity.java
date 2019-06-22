package com.example.undefined.remotebindingclientside;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bindService, unBindService, gerRandomNumber;
    private TextView randomNumberDisplay;
    private Intent intentService;
    public static final int GET_RANDOM_NUMBER_FLAG = 0;
    int randomNumberValue;
    private boolean isServiceBound;
    private ServiceConnection serviceConnection;

    Messenger randomNumberRequestMessenger, randomNumberRecieveMessenger;

    class RecieveRandomNumberHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            randomNumberValue = 0;
            switch (msg.what) {
                case GET_RANDOM_NUMBER_FLAG:
                    randomNumberValue = msg.arg1;
                    randomNumberDisplay.setText("Random Number : " + randomNumberValue);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    ServiceConnection randomNumberServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            randomNumberRequestMessenger = new Messenger(service);
            randomNumberRecieveMessenger=new Messenger(new RecieveRandomNumberHandler());
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            randomNumberRecieveMessenger=null;
            randomNumberRequestMessenger=null;
            isServiceBound=false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService = findViewById(R.id.bindService);
        unBindService = findViewById(R.id.unbingService);
        gerRandomNumber = findViewById(R.id.getRandomNumber);
        randomNumberDisplay = findViewById(R.id.randomNumberDisplay);

        bindService.setOnClickListener(this);
        unBindService.setOnClickListener(this);
        gerRandomNumber.setOnClickListener(this);

        Log.i("Service", "In MainActivity Thread id: " + Thread.currentThread().getId());
        intentService = new Intent();
        intentService.setComponent(new ComponentName("com.example.undefined.remotebindingserviceside","com.example.undefined.remotebindingserviceside.Mservice"));
        intentService.setPackage(getPackageName());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bindService) {
            setBindService();
        } else if (v.getId() == R.id.unbingService) {
            setUnBindService();
        } else if (v.getId() == R.id.getRandomNumber) {
            setRandomNumber();
        }
    }

    private void setBindService() {
        bindService(intentService,randomNumberServiceConnection,BIND_AUTO_CREATE);
        Toast.makeText(getApplicationContext(),"Service Bound",Toast.LENGTH_SHORT).show();
    }

    private void setUnBindService() {
        unbindService(randomNumberServiceConnection);
        isServiceBound=false;
        Toast.makeText(getApplicationContext(),"Service UnBound",Toast.LENGTH_SHORT).show();
    }

    private void setRandomNumber() {
        if (isServiceBound) {
            Message requestMessage=Message.obtain(null,GET_RANDOM_NUMBER_FLAG);
            requestMessage.replyTo=randomNumberRecieveMessenger;
            try {
                randomNumberRequestMessenger.send(requestMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(getApplicationContext(),"Service UnBound Can not Get Random Number ",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        randomNumberServiceConnection=null;
    }
}

