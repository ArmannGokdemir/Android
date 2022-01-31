package com.example.instagrambasicclone.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagrambasicclone.databinding.RecyclerRowBinding;
import com.example.instagrambasicclone.model.Post;
import com.example.instagrambasicclone.model.Singleton;
import com.example.instagrambasicclone.view.PostDetailsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private ArrayList<Post> postArrayList;
    FirebaseAuth firebaseAuth;
    int index;
    Context context;
    public  boolean check;
    PostAdapter postAdapter;
    Singleton singleton;

    public PostAdapter(ArrayList<Post> postArrayList) {
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        return new PostHolder(recyclerRowBinding);


    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.recyclerRowBinding.recyclerEmailText.setText(postArrayList.get(position).userEmail);
        holder.recyclerRowBinding.recyclerViewCommentText.setText(postArrayList.get(position).comment);
        Picasso.get().load(postArrayList.get(position).downloadUrl).into(holder.recyclerRowBinding.recyclerViewImageView);
        holder.recyclerRowBinding.likeCount.setText(String.valueOf(postArrayList.get(position).like));
        index = position;
        firebaseAuth = FirebaseAuth.getInstance();
       holder.recyclerRowBinding.likeButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FirebaseFirestore.getInstance().collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("Likes").document(postArrayList.get(position).uid).set(postArrayList.get(position)).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {

                   }
               });
                FirebaseFirestore.getInstance().collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("Likes").document(postArrayList.get(position).uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        Map<String,Object> mape = value.getData();
                        if(mape.get("uid") == postArrayList.get(position).uid) check=true;
                        else check=false;
                    }
                });


               if(!check ){
                   postArrayList.get(position).like++;
                   HashMap<String,Object> map = new HashMap<>();
                   map.put("comment",postArrayList.get(position).comment);
                   map.put("date",postArrayList.get(position).date);
                   map.put("downloadURL",postArrayList.get(position).downloadUrl);
                   map.put("like",postArrayList.get(position).like);
                   map.put("uid",postArrayList.get(position).uid);
                   map.put("userEmail",postArrayList.get(position).userEmail);


                   holder.recyclerRowBinding.likeCount.setText(String.valueOf(postArrayList.get(position).like));
                   FirebaseFirestore.getInstance().collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("Likes").document(postArrayList.get(position).uid).set(postArrayList.get(position)).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {
                           FirebaseFirestore.getInstance().collection("Posts").document(postArrayList.get(position).uid).update(map);
                       }
                   });


               }

           }
       });


        holder.recyclerRowBinding.recyclerViewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleton=Singleton.getInstance();

                Post post = postArrayList.get(position);
                singleton.setPost(post);
                System.out.println(post.uid);
                Intent intent = new Intent(holder.itemView.getContext(), PostDetailsActivity.class);
                holder.itemView.getContext().startActivity(intent);


            }
        });



    }

    class PostHolder extends RecyclerView.ViewHolder{
        RecyclerRowBinding recyclerRowBinding;


        public PostHolder(@NonNull RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding=recyclerRowBinding;
        }
    }




}
