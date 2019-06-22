package com.example.ihjony.recyclerviewjsonparsing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.util.ArrayList;

public class ExAdapter extends RecyclerView.Adapter<ExAdapter.ExViewHolder> {

    private Context context;
    private ArrayList<ExampleItem> exampleList;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickLister(OnItemClickListener onItemClickLister){
        listener=onItemClickLister;
    }

    public ExAdapter(Context context,ArrayList<ExampleItem>exampleList){
        this.context=context;
        this.exampleList=exampleList;
    }

    @Override
    public ExViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(context).inflate(R.layout.item_example,viewGroup,false);
        return new ExViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ExViewHolder exViewHolder, int i) {
            ExampleItem currentItem=exampleList.get(i);

            String imageUrl=currentItem.getImageUrl();
            String creatorName=currentItem.getCreatorName();
            int likeCount=currentItem.getLike();

            exViewHolder.mTextViewCreator.setText(creatorName);
            exViewHolder.mTextViewLikes.setText("Like : "+likeCount);
            Picasso.get().load(imageUrl).into(exViewHolder.mImageView);

    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    public class ExViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;

        public ExViewHolder( View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageViewId);
            mTextViewCreator = itemView.findViewById(R.id.creatorNameId);
            mTextViewLikes = itemView.findViewById(R.id.likeTextId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
