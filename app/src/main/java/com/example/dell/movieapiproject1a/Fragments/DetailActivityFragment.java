package com.example.dell.movieapiproject1a.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.movieapiproject1a.MainActivity;
import com.example.dell.movieapiproject1a.R;
import com.example.dell.movieapiproject1a.SavingImage;
import com.example.dell.movieapiproject1a.Test;
import com.example.dell.movieapiproject1a.TestTable;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DetailActivityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    ImageView img;
    TextView t1,t2,t3,t4;
    ListView lv;
    static int ct=1;
    int ck=0;
    static int pos=0;
    public static String key;
    static String fil="";
    CheckBox cb;
    static JSONObject obj= null;
    public static int choice =1;
    public String review="",trail="",str="";
    JSONObject last=null;
    public static Resources res;
     String[] trailer_list;
    Bundle bn;

  //  private OnFragmentInteractionListener mListener;

    public DetailActivityFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DetailActivityFragment newInstance(Bundle w) {
        DetailActivityFragment fragment = new DetailActivityFragment();
       /* Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
        args.putInt("index",index);
        args.putString("json", json);*/
        fragment.setArguments(w);
        if(w!=null)
            Log.e("Bundel Obtained","from MainActivity");
        else
            Log.e("Bundle is","null");
        return fragment;
    /*public static DetailActivityFragment newInstance(int index){
        DetailActivityFragment f=new DetailActivityFragment();
        Bundle b=new Bundle();
        b.putInt("index",index);
        f.setArguments(b);
        return  f;*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
           // pos=this.getArguments().getInt("index");
            //fil=this.getArguments().getString("json");
            bn=getArguments();
            if(bn!=null)
                Log.e("Bundel Obtained","from MainActivity");
            else
                Log.e("Bundle is","null");
        }
        setHasOptionsMenu(true);
    }
    @Override
    public void onStart() {
        super.onStart();
        //if()
      //  View v=getView();
      //  if(v!=null)
      //  {
        try {
            obj = new JSONObject(fil);
            JSONArray arr = obj.optJSONArray("results");
            last = arr.optJSONObject(pos);
            trail = "http://api.themoviedb.org/3/movie/" + last.getString("id") + "/videos?api_key=" + key;
            SavingImage obj = new SavingImage(getActivity().getApplicationContext());
            new back().execute(trail);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Cursor cur = getActivity().getContentResolver().query(TestTable.CONTENT_URI, null, null, null, null);
        //Test tr=TestTable.getRow(cur, true);
        final List<Test> movie_list = TestTable.getRows(cur, false);
        for (int i = 0; i < movie_list.size(); i++) {
            try {
                if (movie_list.get(i).movie_id == last.getInt("id")) {
                    cb.setChecked(true);
                    cur.close();
                    break;
                } else
                    cb.setChecked(false);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        cur.close();
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb.isChecked()) {

                    String z = null;
                    try {
                        z = "http://image.tmdb.org/t/p/w185/" + last.getString("poster_path");
                        ContentValues values = new ContentValues();
                        // values.put("_id",1);
                        //  values.put("_id",Test);
                        values.put("MOVIE_ID", last.getInt("id"));
                        values.put("TITLE", last.getString("original_title"));
                        values.put("SYNOPSIS", last.getString("overview"));
                        values.put("RATE", last.getInt("vote_average"));
                        values.put("DATE", last.getString("release_date"));
                        values.put("POSTER_PATH", z);


                        try {
                            //getContentResolver().insert(TestTable.CONTENT_URI,TestTable.getContentValues(v,false));
                            getActivity().getContentResolver().insert(TestTable.CONTENT_URI, values);
                            Log.e(last.getString("original_title"), " Added to database");
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Insertion failed in", "DATABASE");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Log.e(last.getString("original_title"), " removed from database");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        //String[] p=Integer.toString(last.getInt("id"));
                        String[] p = new String[1];
                        p[0] = Integer.toString(last.getInt("id"));
                        getActivity().getContentResolver().delete(TestTable.CONTENT_URI, TestTable.FIELD_MOVIE_ID + "=?", p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    //}

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


      /* if(savedInstanceState==null) {
            return null;
        }*/
        /*if(MainActivity.mTwoPane==true){
            if(savedInstanceState==null)
                return null;
        }*/
       // Bundle bn=getArguments();
        View view =inflater.inflate(R.layout.fragment_detail_activity, container, false);
        img = (ImageView) view.findViewById(R.id.imageView);
        t1 = (TextView) view.findViewById(R.id.data);
        t2 = (TextView) view.findViewById(R.id.synopsis);
        t3 = (TextView) view.findViewById(R.id.name);
        t4 = (TextView) view.findViewById(R.id.reviews);
        lv = (ListView) view.findViewById(R.id.trailer_list);
        cb = (CheckBox) view.findViewById(R.id.fav);

            Bundle p = getActivity().getIntent().getExtras();
       if(p!=null && bn==null) {
            Log.e("Intent","running");
            pos = p.getInt("Position");
            fil = p.getString("j");
        }
        else if(bn!=null && p==null){
            Log.e("Get_arguments","running");
            pos=bn.getInt("index");
            fil=bn.getString("json");
        }
      /*  if(MainActivity.mTwoPane==true){
            Log.e("Get_arguments","running");
            pos=bn.getInt("index");
            fil=bn.getString("json");

        }
        else{
            Log.e("Intent","running");
            pos = p.getInt("Position");
            fil = p.getString("j");
        }*/
            String ur = "";
            res = getResources();
            key = String.format(res.getString(R.string.key));




            try {
                obj = new JSONObject(fil);
                JSONArray arr = obj.optJSONArray("results");
                JSONObject last = arr.optJSONObject(pos);
                trail = "http://api.themoviedb.org/3/movie/" + last.getString("id") + "/videos?api_key=" + key;
                new back().execute(trail);
                ur = "http://image.tmdb.org/t/p/w185/" + last.getString("poster_path");

                review = "http://api.themoviedb.org/3/movie/" + last.getString("id") + "/reviews?api_key=" + key;

                t1.setText("User Rating- " + last.getString("vote_average") + "/10" + "\n\n" + "Date of Release " + last.getString("release_date"));
                Picasso.with(getActivity().getApplicationContext()).load(ur).into(img);
                t3.setText(last.getString("original_title"));
                t2.setText("\n" + last.getString("overview"));
                t4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ct % 2 != 0) {
                            t4.setTypeface(null, Typeface.BOLD);
                            //t4.setTextSize(20);
                            choice = 2;
                            new back().execute(review);
                            t4.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);
                        } else {
                            t4.setText("Reviews");
                            //t4.setText(review);
                            t4.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);
                        }
                        ct += 1;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


        return view;
    }









    private void trailer() {
        Intent i=new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, trailer_list[0]);
        startActivity(Intent.createChooser(i, "Share Trailer"));
    }
    private class back extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url=new URL(params[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream is=urlConnection.getInputStream();
                StringBuffer buff=new StringBuffer();

                if(is==null)
                    str=null;

                reader=new BufferedReader(new InputStreamReader(is));

                String l;
                while ((l=reader.readLine())!=null){
                    buff.append(l+"\n");
                }

                if(buff.length()==0)
                    str=null;

                str=buff.toString();
            }catch (Exception e){
                e.printStackTrace();
                str=null;
            }finally {
                if(urlConnection!=null)
                    urlConnection.disconnect();
                if(reader!=null)
                    try {

                        reader.close();
                    }catch (Exception e){
                        e.printStackTrace();

                    }
            }
            return str;
        }

        @Override
        protected void onPostExecute(String k) {
            //super.onPostExecute(aVoid);
            try {
                Log.e("JSON", k);

                JSONObject obj2 = new JSONObject(k);
                JSONArray tr = obj2.optJSONArray("results");
                trailer_list=new String[tr.length()];
                final String[] err_trail_list =new String[tr.length()];

                String[] name=new String[tr.length()];
                if(choice==1)
                {
                    for(int i=0;i<tr.length();i++){
                         JSONObject lnk = tr.optJSONObject(i);
                        trailer_list[i]="https://www.youtube.com/watch?v=" + lnk.getString("key");
                        err_trail_list[i]="https://www.youtube.com/watch?v=" + lnk.getString("key");
                        name[i]="Trailer "+(i+1);
                        Log.e("Trailer ",(i+1)+trailer_list[i]);
                    }
         /*  Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + lnk.getString("key")));
                            startActivity(browser);*/
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(trailer_list[position]==null){
                                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(err_trail_list[position]));
                                startActivity(browser);
                            }
                            else{
                                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer_list[position]));
                                startActivity(browser);
                            }

                        }
                    });
            try
                {
                    ArrayAdapter adap=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.list_view,name);
                    lv.setAdapter(adap);
                }catch (Exception e){
                            e.printStackTrace();
        }
                   // choice=2;

                }
                 if(choice==2){
                    // String[] piyu=new String[tr.length()];
                    //Spanned text= Html.fromHtml("<b>Reviews<b>");
                    String text="Reviews\n\n";
                    for(int i=0;i<tr.length();i++){
                        JSONObject b=tr.optJSONObject(i);
                        text=text+"\n"+(i+1)+")"+b.getString("content")+"\n";
                    }
                    t4.setText(text);
                    choice=1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater infla) {
        infla.inflate(R.menu.share_feature,menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:
                trailer();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
