package com.example.undefined.remotebindingserviceside;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.os.Handler;

import java.util.Random;


public class Mservice extends Service {

    private int randomNumber;
    private boolean isRandomNumberOn;
    private final int MIN=0;
    private final int MAX=100;

    public static final int GET_COUNT=0;

    private class RandomNumberRequestHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_COUNT:
                    Message sendRandomNumber=Message.obtain(null,GET_COUNT);
                    sendRandomNumber.arg1=getRandomNumber();
                    try {
                        msg.replyTo.send(sendRandomNumber);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
            }
            super.handleMessage(msg);
        }
    }

    private Messenger randomNumberMessenger = new Messenger(new RandomNumberRequestHandler());

    class ServiceBinder extends Binder {
        public Mservice getService(){
            return Mservice.this;
        }
    }

    private IBinder mBinder=new ServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Service","In OnBind");
        if(intent.getPackage().equals("com.example.undefined.remotebindingclientside"))
            return randomNumberMessenger.getBinder();
        else
            return null;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i("Service","Service Destroy");
        stopRandomNumberGenerator();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Service","In onStartCommand Thread id: "+Thread.currentThread().getId());
        isRandomNumberOn=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                startRandomNumberGenerator();
            }
        }).start();
        return START_STICKY;
    }

    private void startRandomNumberGenerator(){
        while (isRandomNumberOn){
            try {
                Thread.sleep(1000);
                if(isRandomNumberOn) {
                    randomNumber = new Random().nextInt(MAX) + MIN;
                    Log.i("Service", "In LocalBinding Thread id: " + Thread.currentThread().getId() + " Random Number is: " + randomNumber);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopRandomNumberGenerator(){ isRandomNumberOn=false ;}


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public int getRandomNumber() {
        return randomNumber;
    }
}
