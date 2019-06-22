package com.example.viewmodelsample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private TextView textView1,textView2,textView3;
    SampleViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 =  findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        model = ViewModelProviders.of(this).get(SampleViewModel.class);

        final Observer<IpInfoModel> ipInfoOb = new Observer<IpInfoModel>() {
            @Override
            public void onChanged(IpInfoModel ipInfoModel) {
                Log.d("data",ipInfoModel.getCountryName()+"___"+ipInfoModel.getIp());

                textView1.setText("IP "+ipInfoModel.getIp());
                textView2.setText("Country "+ipInfoModel.getCountryName());
                textView3.setText("Location "+ipInfoModel.getLoc());
            }
        };
        model.getIpConfig().observe(this,ipInfoOb);
    }
}
