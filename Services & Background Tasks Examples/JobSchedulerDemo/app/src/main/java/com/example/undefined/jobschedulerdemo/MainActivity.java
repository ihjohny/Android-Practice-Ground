package com.example.undefined.jobschedulerdemo;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scheduleJob(View v) {
        ComponentName componentName=new ComponentName(this,ExampleJobService.class);
        JobInfo jobInfo=new JobInfo.Builder(123,componentName)
                .setRequiresCharging(true)
              //  .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15*60*1000)
                .build();
        JobScheduler scheduler= (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode=scheduler.schedule(jobInfo);
        if(resultCode==JobScheduler.RESULT_SUCCESS){
            Log.d("MainActivity","Job Scheduled");
        }
        else{
            Log.d("MainActivity","Job Scheduling Failed");
        }

    }

    public void cancelJob(View v) {
        JobScheduler scheduler= (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d("MainActivity","Job Cancelled");
    }
}
