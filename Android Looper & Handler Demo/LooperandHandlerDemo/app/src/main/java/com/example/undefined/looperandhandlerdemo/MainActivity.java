package com.example.undefined.looperandhandlerdemo;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView textView;
    private Button onButton, offButton;
    private boolean mStopLoop;
    private int count = 0;
    LooperThread looperThread;
    CustomHandlerThread customHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textViewId);
        onButton = findViewById(R.id.onButtonId);
        offButton = findViewById(R.id.offButtonId);

        onButton.setOnClickListener(this);
        offButton.setOnClickListener(this);

        looperThread = new LooperThread();
        looperThread.start();

        customHandlerThread=new CustomHandlerThread("customHandler");
        customHandlerThread.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.onButtonId:
                mStopLoop = true;
//                executeOnCustomLooper();
//                executeOnCustomLooperForUpdateUiThread();
//                setCustomHandlerExecuteOnCustomLooper();
                setCustomHandlerExecuteOnCustomLooperForUpdateUiThread();
                break;
            case R.id.offButtonId:
                mStopLoop = false;
                break;
        }

    }

    private void executeOnCustomLooperForUpdateUiThread() {
        Toast.makeText(getApplicationContext(), "on button clicked", Toast.LENGTH_SHORT).show();
        looperThread.handler.post(new Runnable() {
            @Override
            public void run() {
                while (mStopLoop) {
                    try {
                        Log.i(TAG, "Thread id :" + Thread.currentThread().getId());
                        Thread.sleep(1000);
                        count++;
                        Message message = new Message();
                        message.obj = count;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(" " + count);
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void executeOnCustomLooper() {
        Toast.makeText(getApplicationContext(), "on button clicked", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mStopLoop) {
                    try {
                        Log.i(TAG, "Thread id :" + Thread.currentThread().getId());
                        Thread.sleep(1000);
                        count++;
                        Message message = new Message();
                        message.obj = count;
                        looperThread.handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    private void setCustomHandlerExecuteOnCustomLooperForUpdateUiThread() {
        Toast.makeText(getApplicationContext(), "on button clicked", Toast.LENGTH_SHORT).show();
        customHandlerThread.handler.post(new Runnable() {
            @Override
            public void run() {
                while (mStopLoop) {
                    try {
                        Log.i(TAG, "Thread id :" + Thread.currentThread().getId());
                        Thread.sleep(1000);
                        count++;
                        Message message = new Message();
                        message.obj = count;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(" " + count);
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setCustomHandlerExecuteOnCustomLooper() {
        Toast.makeText(getApplicationContext(), "on button clicked", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mStopLoop) {
                    try {
                        Log.i(TAG, "Thread id :" + Thread.currentThread().getId());
                        Thread.sleep(1000);
                        count++;
                        Message message = new Message();
                        message.obj = count;
                        customHandlerThread.handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(looperThread!=null && looperThread.isAlive()){
            looperThread.handler.getLooper().quit();
        }
        if(customHandlerThread!=null){
            customHandlerThread.getLooper().quit();
        }
    }
}
