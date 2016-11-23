package com.example.dell.movieapiproject1a;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by dell on 3/23/2016.
 */
public class SavingImage extends Application{
    Context c;
    public SavingImage(Context c){
        this.c=c;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        Picasso.Builder builder = new Picasso.Builder(c);
        builder.downloader(new OkHttpDownloader(c,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

    }
}
