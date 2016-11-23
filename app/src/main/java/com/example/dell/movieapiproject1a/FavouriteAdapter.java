package com.example.dell.movieapiproject1a;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by dell on 3/18/2016.
 */
public class FavouriteAdapter extends CursorAdapter {
    private LayoutInflater m;
    public FavouriteAdapter(Context context, Cursor c,int flags) {
        super(context, c, flags);
    m=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return m.inflate(R.layout.kk,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView img=(ImageView)view.findViewById(R.id.imageView2);
        img.setAdjustViewBounds(true);
        //Log.e("Index", String.valueOf(cursor.getColumnIndex(TestTable.FIELD_POSTER_PATH)));
//        Log.e("_id",cursor.getString(cursor.getColumnIndex(TestTable.FIELD__ID)));
        Picasso.with(context).load(
                cursor.getString(cursor.getColumnIndex(TestTable.FIELD_POSTER_PATH))
        ).networkPolicy(NetworkPolicy.OFFLINE).into(img);

    }
}
