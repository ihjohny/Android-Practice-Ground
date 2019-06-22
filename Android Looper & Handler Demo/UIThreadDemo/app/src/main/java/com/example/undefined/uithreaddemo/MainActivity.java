package com.example.undefined.uithreaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button onButton, offButton;
    private TextView textView;
    private boolean mStopLoop;
    private int count=0;
/*    private Handler handler;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Thread Id: " + Thread.currentThread().getId());
        onButton = findViewById(R.id.onButtonId);
        offButton = findViewById(R.id.offButtonId);
        textView = findViewById(R.id.textViewId);

        onButton.setOnClickListener(this);
        offButton.setOnClickListener(this);
/*
        handler=new Handler(getApplicationContext().getMainLooper());*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.onButtonId:
                mStopLoop = true;
                Toast.makeText(getApplicationContext(), "This is a test ", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mStopLoop) {
                            try {
                                Thread.sleep(1000);
                                count++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            textView.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(" "+count);
                                }
                            });
                            Log.i(TAG, "Thread is in while Loop : " + Thread.currentThread().getId()+" count "+count);
/*                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(" "+count);
                                }
                            });*/
                        }
                    }
                }).start();
                break;

            case R.id.offButtonId: mStopLoop = false;
                break;
        }
    }
}
