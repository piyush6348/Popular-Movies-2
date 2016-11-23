package com.example.dell.movieapiproject1a;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * Created by dell on 2/2/2016.
 */
public class CustomListAdapter extends ArrayAdapter{

    private final Context con;
    private final String[]  a;

    public CustomListAdapter(Context con,String[] a) {
       // super(con, R.layout.myimg);
        super(con,R.layout.kk,a);
        this.a = a;
        this.con = con;



    }
    public View getView(int position,View view,ViewGroup parent) {

        Log.v("IMAGES",a[2]);
        View v;
        LayoutInflater m=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view==null)
            v=m.inflate(R.layout.kk,parent,false);
        else
            v=view;
        ImageView img=(ImageView)v.findViewById(R.id.imageView2);
        img.setAdjustViewBounds(true);
        Picasso.with(con).load(a[position]).into(img);
        return v;

    }
   /* private Context c;
    private List<String> list;
    public SetAdapter(Context c,List<String>list){
        this.c=c;
        this.list=list;
    }
    public View getView(int position,View view,ViewGroup parent) {
        View v;
        LayoutInflater m=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view==null)
            v=m.inflate(R.layout.kk,parent,false);
        else
            v=view;
        TextView tt=(TextView)v.findViewById(R.id.text00);
        tt.setText(list.get(position));
        return v;
    }*/
    }

