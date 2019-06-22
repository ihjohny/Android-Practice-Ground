package com.example.viewmodelsample;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class SampleViewModel extends AndroidViewModel {

    public SampleViewModel(@NonNull Application application) {
        super(application);
    }

    private final String apiUrl = "https://ifconfig.co/json";
    public String Country,ip,loc;
    IpInfoModel infoModel = new IpInfoModel();

    public MutableLiveData<IpInfoModel> ipConfig;
    public MutableLiveData<IpInfoModel> getIpConfig(){
        if(ipConfig == null){
            ipConfig = new MutableLiveData<>();
            getIpInfo();
        }
        return ipConfig;
    }
    private void getIpInfo(){

        StringRequest request = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res :: ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Country = jsonObject.getString("country");
                    ip = jsonObject.getString("ip");
                    loc = jsonObject.getString("latitude") +" , "+jsonObject.getString("longitude");

                    infoModel.setCountryName(Country);
                    infoModel.setIp(ip);
                    infoModel.setLoc(loc);

                    ipConfig.setValue(infoModel);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(request);
    }
}
