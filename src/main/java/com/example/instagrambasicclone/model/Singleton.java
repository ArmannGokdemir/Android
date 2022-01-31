package com.example.instagrambasicclone.model;

public class Singleton {
    private  static  Singleton singleton;
    private Post post;
     private  static Object lock = new Object();

     private Singleton(){

     }
     public void setPost(Post post){
         this.post=post;
     }
     public Post getPost(){
         return this.post;
     }

    public static Singleton getInstance(){

        if(singleton == null){

            synchronized (lock){

                if(singleton == null){

                    singleton = new Singleton();

                }


            }

        }
        return singleton;

    }
}


