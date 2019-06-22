package com.example.firebaseimageuploadexample;

import com.google.firebase.database.Exclude;

public class Upload {
    private String name;
    private String imageUrl;
    private String key;
    public Upload(){
        //empty constructor for firebase
    }

    public Upload(String Name, String ImageUrl){
        if(Name.trim().equals("")){
            Name = "No Name";
        }
        name = Name;
        imageUrl = ImageUrl;
    }
    public String getName(){
        return name;
    }
    public void setName(String Name){
        name=Name;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String ImageUrl){
        imageUrl=ImageUrl;
    }
    @Exclude
    public String getKey(){ return key;}
    @Exclude
    public void setKey(String Key){ key = Key;}

}
