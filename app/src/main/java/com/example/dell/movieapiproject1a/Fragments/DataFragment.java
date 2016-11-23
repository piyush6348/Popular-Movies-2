package com.example.dell.movieapiproject1a.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.movieapiproject1a.FavouriteAdapter;
import com.example.dell.movieapiproject1a.MainActivity;
import com.example.dell.movieapiproject1a.R;
import com.example.dell.movieapiproject1a.Test;
import com.example.dell.movieapiproject1a.TestTable;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DataFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Bundle bn;
    List<Test> testRows;
    ImageView iv;
    TextView t1,t2,t3;
    CheckBox cb;
    String[] p;
    String dat,syn,titl,path;
    int movie_id,rate;
    GridView gv;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

  //  private OnFragmentInteractionListener mListener;

    public DataFragment() {
        // Required empty public constructor
    }

    public static DataFragment newInstance(Bundle m) {
        DataFragment fragment = new DataFragment();
       // Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(m);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          //  mParam1 = getArguments().getString(ARG_PARAM1);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
            bn=getArguments();
        }
    }
    @Override
    public void onStart() {
        super.onStart();

        Bundle b=getActivity().getIntent().getExtras();
if(b!=null && bn==null) {
    dat = b.getString("date");
    syn = b.getString("syn");
    titl = b.getString("titl");
    path = b.getString("path");
    rate = b.getInt("rate");
    movie_id = b.getInt("movie_id");
}
    else if(b==null &&bn!=null){
    dat = bn.getString("date");
    syn = bn.getString("syn");
    titl = bn.getString("titl");
    path = bn.getString("path");
    rate = bn.getInt("rate");
    movie_id = bn.getInt("movie_id");
}
    t3.setText(titl);
    t2.setText(syn);
    t1.setText("User Rating- " +rate+ "/10" + "\n\n" + "Date of Release "+dat);
    Picasso.with(getActivity().getApplicationContext()).load(path).into(iv);
    p=new String[1];
    p[0]=Integer.toString(movie_id);
    cb.setChecked(true);
    cb.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!cb.isChecked())
                getActivity().getContentResolver().delete(TestTable.CONTENT_URI, TestTable.FIELD_MOVIE_ID + "=?", p);
            else
            {
                ContentValues val=new ContentValues();
                val.put("MOVIE_ID",movie_id);
                val.put("TITLE",titl);
                val.put("SYNOPSIS", syn);
                val.put("RATE", rate);
                val.put("DATE",dat);
                val.put("POSTER_PATH", path);
                getActivity().getContentResolver().insert(TestTable.CONTENT_URI,val);
            }
           // MainActivityFragment k=new MainActivityFragment();
           // k.acessFavourite();
           /* if(MainActivity.mTwoPane==true){
                Cursor cursor=getActivity().getContentResolver().query(TestTable.CONTENT_URI, null, null, null, null, null);
                testRows = TestTable.getRows(cursor, false);
                FavouriteAdapter fad=new FavouriteAdapter(getActivity().getApplicationContext(),cursor,0);
                gv.setAdapter(fad);
            }*/
           // if(MainActivity.mTwoPane==true)
          // new MainActivityFragment().acessFavourite();
            if(MainActivity.mTwoPane==true){
               // Bundle b=new Bundle();
               // new MainActivity().onCreate(new Bundle());
                Fragment f=getFragmentManager().findFragmentById(R.id.frag1);
                f.onStart();
            }


        }
    });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_data, container, false);
        iv=(ImageView)v.findViewById(R.id.imageView);
        t1=(TextView)v.findViewById(R.id.data);
        t2=(TextView)v.findViewById(R.id.synopsis);
        t3=(TextView)v.findViewById(R.id.name);
        cb=(CheckBox)v.findViewById(R.id.fav);
        gv=(GridView)v.findViewById(R.id.gridView);
        return v;
    }

}
