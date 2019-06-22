package com.example.undefined.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class LocalBindingService extends Service {
    private int randomNumber;
    private boolean isRandomNumberOn;
    private final int MIN=0;
    private final int MAX=100;

    class ServiceBinder extends Binder{
        public LocalBindingService getService(){
            return LocalBindingService.this;
        }
    }

    private IBinder mBinder=new ServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Service","In OnBind");
        return mBinder;
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
