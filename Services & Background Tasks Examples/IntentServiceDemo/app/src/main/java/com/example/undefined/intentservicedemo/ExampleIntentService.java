package com.example.undefined.intentservicedemo;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static com.example.undefined.intentservicedemo.App.CHANNEL_ID;

public class ExampleIntentService extends IntentService {

    private PowerManager.WakeLock wakeLock;
    private static final String TAG=" ExampleIntentService";
    public ExampleIntentService() {
        super("ExampleIntentService");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");

        PowerManager powerManager=(PowerManager)getSystemService(POWER_SERVICE);
        wakeLock=powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"ExapleAPP:wakelock");
        wakeLock.acquire();
        Log.d(TAG,"Wakelock Acquired");

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Example Service")
                    .setContentText("this is demo")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .build();

            startForeground(1,notification);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        wakeLock.release();
        Log.d(TAG,"Wakelock released");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        Log.d(TAG,"onHandleIntent");
        String input=intent.getStringExtra("inputExtra");
        for (int i = 0; i <10 ; i++) {
            Log.d(TAG,input+" - "+i);
            SystemClock.sleep(1000);
        }
    }
}
