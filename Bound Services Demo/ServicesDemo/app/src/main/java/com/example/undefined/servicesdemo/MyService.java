package com.example.undefined.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Service","In onStartCommand Thread id: "+Thread.currentThread().getId());
      //  stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
}
