package com.example.ihjony.volleyex;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static  VolleySingleton singleInstance;
    private RequestQueue requestQueue;
    private VolleySingleton (Context context){
        requestQueue=Volley.newRequestQueue(context.getApplicationContext());
    }
    public static synchronized VolleySingleton getSingleInstance(Context context){
        if(singleInstance==null)
            singleInstance=new VolleySingleton(context);
        return singleInstance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
