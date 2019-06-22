package com.example.undefined.firestoreex;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class Note {
    private String id;
    private String title;
    private String note;
    List<String> tags;

    public Note() {
    }  //empty constructor need for firestore

    public Note(String title, String note,List<String> tags) {
        this.title = title;
        this.note = note;
        this.tags = tags;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
