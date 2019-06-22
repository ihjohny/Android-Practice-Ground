package com.example.ihjony.recyclerviewjsonparsing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.ihjony.recyclerviewjsonparsing.MainActivity.EXTRA_CREATOR;
import static com.example.ihjony.recyclerviewjsonparsing.MainActivity.EXTRA_LIKES;
import static com.example.ihjony.recyclerviewjsonparsing.MainActivity.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent=getIntent();
        String imageUrl=intent.getStringExtra(EXTRA_URL);
        String creatorName=intent.getStringExtra(EXTRA_CREATOR);
        int likeCount=intent.getIntExtra(EXTRA_LIKES,0);

        ImageView imageView=findViewById(R.id.detailImageViewId);
        TextView creator=findViewById(R.id.detailCreatorNameId);
        TextView like=findViewById(R.id.detailLikeId);

        Picasso.get().load(imageUrl).into(imageView);
        creator.setText(creatorName);
        like.setText("Likes : " +likeCount);

    }
}
