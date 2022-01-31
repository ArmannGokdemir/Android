package com.example.instagrambasicclone.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.instagrambasicclone.R;
import com.example.instagrambasicclone.databinding.ActivityPostDetailsBinding;
import com.example.instagrambasicclone.model.Post;
import com.example.instagrambasicclone.model.Singleton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class PostDetailsActivity extends AppCompatActivity {
    private ActivityPostDetailsBinding binding;
    Post post;
    Singleton singleton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityPostDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        singleton=Singleton.getInstance();
        post=singleton.getPost();
        long a = post.like;
        String like = Long.toString(a);


        Picasso.get().load(post.downloadUrl).into(binding.imageView2);
        binding.textView.setText(post.userEmail);
        binding.textView2.setText(post.comment);
        binding.textView3.setText(like);


    }

    public void goBack(View view){
        Intent intent = new Intent(this,FeedActivity.class);
        startActivity(intent);
        finish();

    }


}