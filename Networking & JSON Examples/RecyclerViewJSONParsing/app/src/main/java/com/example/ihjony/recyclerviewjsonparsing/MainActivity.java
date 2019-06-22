package com.example.ihjony.recyclerviewjsonparsing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_URL="imageUrl";
    public static final String EXTRA_CREATOR="creatorName";
    public static final String EXTRA_LIKES="likeCount";


    private RecyclerView recyclerView;
    private ExAdapter exAdapter;
    private ArrayList<ExampleItem> arrayList;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        parseJson();
    }

    private void parseJson() {
        String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String creatorName = hit.getString("user");
                        String imageUrl = hit.getString("webformatURL");
                        int likeCount = hit.getInt("likes");

                        arrayList.add(new ExampleItem(imageUrl, creatorName, likeCount));
                    }
                    exAdapter = new ExAdapter(MainActivity.this, arrayList);
                    recyclerView.setAdapter(exAdapter);
                    exAdapter.setOnItemClickLister(new ExAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
//                            Toast.makeText(getApplicationContext(),"this is test",Toast.LENGTH_SHORT).show();
                            Intent deatialIntent=new Intent(MainActivity.this,DetailActivity.class);

                            ExampleItem clickedItem=arrayList.get(position);

                            deatialIntent.putExtra(EXTRA_URL,clickedItem.getImageUrl());
                            deatialIntent.putExtra(EXTRA_CREATOR,clickedItem.getCreatorName());
                            deatialIntent.putExtra(EXTRA_LIKES,clickedItem.getLike());

                            startActivity(deatialIntent);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(objectRequest);
    }
}
