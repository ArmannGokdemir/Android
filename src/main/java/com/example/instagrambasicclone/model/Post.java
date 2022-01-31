package com.example.instagrambasicclone.model;

import com.google.firebase.Timestamp;

public class Post {
    public String uid;
    public String downloadUrl;
    public String userEmail;
    public String comment;
    public long like;
    public Timestamp date;

    public Post(String url, String userEmail, String comment, String uid, long like,Timestamp date) {
        this.date = date;
        this.downloadUrl = url;
        this.userEmail = userEmail;
        this.comment = comment;
        this.uid = uid;
        this.like = like;
    }
}
