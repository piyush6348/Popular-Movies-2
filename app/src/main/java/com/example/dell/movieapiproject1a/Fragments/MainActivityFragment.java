package com.example.dell.movieapiproject1a.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.dell.movieapiproject1a.CustomListAdapter;
import com.example.dell.movieapiproject1a.DataActivity;
import com.example.dell.movieapiproject1a.DetailsActivity;
import com.example.dell.movieapiproject1a.FavouriteAdapter;
import com.example.dell.movieapiproject1a.MainActivity;
import com.example.dell.movieapiproject1a.R;
import com.example.dell.movieapiproject1a.Test;
import com.example.dell.movieapiproject1a.TestTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**

 */
public class MainActivityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public GridView gv;


    public MainActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainActivityFragment newInstance(String param1, String param2) {
        MainActivityFragment fragment = new MainActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    String ans = null;
    public static String key;
    public static Resources res;
    public static int life=0;
    List<Test> testRows;

    String url_to_be_passed;

    //Notifying Callback
    public static interface OnclicListener{
        public abstract void onClick(String s,int po);
    }

    private OnclicListener mListener;

    public static interface fav{
        public abstract void favClick(Bundle x);
    }
    private fav favListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnclicListener)activity;
            this.favListener=(fav)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo=cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        if(life== R.id.favourite || !(haveConnectedWifi || haveConnectedMobile))
            acessFavourite();
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        life=0;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main_activity, container, false);
        gv=(GridView)view.findViewById(R.id.gridView);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (life != R.id.favourite) {

                     if(MainActivity.mTwoPane!=true){
                        Intent i = new Intent(getActivity().getBaseContext(), DetailsActivity.class);
                        Bundle extras = new Bundle();
                        extras.putInt("Position", position);
                        extras.putString("j", ans);
                        i.putExtras(extras);
                        startActivity(i);
                    }

                    else if(MainActivity.mTwoPane ==true)
                    mListener.onClick(ans,position);


                } else if(life==R.id.favourite){

                    Test obj = testRows.get(position);
                    Log.e("Clicked", obj.titl);
                    Intent k = new Intent(getActivity().getApplicationContext(), DataActivity.class);
                    Bundle b = new Bundle();
                    b.putString("titl", obj.titl);
                    b.putInt("rate", obj.rate);
                    b.putString("path", obj.path);
                    b.putString("date", obj.date);
                    b.putString("syn", obj.syn);
                    b.putInt("movie_id", obj.movie_id);
                    k.putExtras(b);

                    if(MainActivity.mTwoPane==true)
                        favListener.favClick(b);
                    else if(MainActivity.mTwoPane!=true)
                    startActivity(k);
                }

            }
        });

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo=cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        if (!(haveConnectedWifi || haveConnectedMobile))
        {
            life=R.id.favourite;
            acessFavourite();
        }
        else
        {
            res = getResources();

            key = String.format(res.getString(R.string.key));
            url_to_be_passed = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + key;
            new Connection_and_Setimages().execute(url_to_be_passed);

        }
        return view;
    }
    public void acessFavourite() {
        // testRows=new ArrayList<>();
        if(MainActivity.mTwoPane==true)
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new DataFragment()).commit();

        Cursor cursor=getActivity().getContentResolver().query(TestTable.CONTENT_URI, null, null, null, null, null);
        testRows = TestTable.getRows(cursor, false);
        FavouriteAdapter fad=new FavouriteAdapter(getActivity().getApplicationContext(),cursor,0);
        gv.setAdapter(fad);
        // cursor.close();
        cursor.moveToFirst();
        //cursor.close();
    }

    private class Connection_and_Setimages extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.

            try {
                URL url=new URL(params[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream is=urlConnection.getInputStream();
                StringBuffer buff=new StringBuffer();

                if(is==null)
                    ans=null;

                reader=new BufferedReader(new InputStreamReader(is));

                String l;
                while ((l=reader.readLine())!=null){
                    buff.append(l+"\n");
                }

                if(buff.length()==0)
                    ans=null;

                ans=buff.toString();
            }catch (Exception e){
                e.printStackTrace();
                ans=null;
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
            // Log.("JSON-STRING",ans);
            // Log.v("JSON-STRING",ans);
            //Reading poster path..
            try {
                return getarray(ans);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }




        @Override
        protected void onPostExecute(String[] x) {

            CustomListAdapter ad=new CustomListAdapter(getActivity(),x);

            gv.setAdapter(ad);


        }


    }
    private String[] getarray(String ans2) throws JSONException {
        String ur="http://image.tmdb.org/t/p/w185/";
        JSONObject obj=new JSONObject(ans2);
        JSONArray arr=obj.optJSONArray("results");
        String[] posterpath2=new String[arr.length()];
        for(int i=0;i<arr.length();i++)
        {
            JSONObject last=arr.optJSONObject(i);
            posterpath2[i]=ur+last.getString("poster_path");
            Log.v("POSTER_PATH"+i,posterpath2[i]);
        }
        return posterpath2;
    }
    //https://www.youtube.com/watch?v=WzlWDJR8o04
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sort_by_popularity:
                url_to_be_passed="http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+key;
                life=R.id.menu_sort_by_popularity;

                boolean haveConnectedWifi = false;
                boolean haveConnectedMobile = false;
                ConnectivityManager cm=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo[] netInfo=cm.getAllNetworkInfo();
                for (NetworkInfo ni : netInfo) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                        if (ni.isConnected())
                            haveConnectedWifi = true;
                    if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (ni.isConnected())
                            haveConnectedMobile = true;
                }
                if(haveConnectedMobile==true|| haveConnectedWifi==true)
                new Connection_and_Setimages().execute(url_to_be_passed);
                else
                    Toast.makeText(getActivity().getApplicationContext(),"Please switch on ur Internet connection",Toast.LENGTH_SHORT)
                    .show();
                break;
            case R.id.menu_sort_by_user_rating:
                url_to_be_passed="http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key="+key;
                life=R.id.menu_sort_by_user_rating;

                boolean haveConnectedWifi2 = false;
                boolean haveConnectedMobile2 = false;
                ConnectivityManager cm2=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo[] netInfo2=cm2.getAllNetworkInfo();
                for (NetworkInfo ni : netInfo2) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                        if (ni.isConnected())
                            haveConnectedWifi2 = true;
                    if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (ni.isConnected())
                            haveConnectedMobile2 = true;
                }
                if(haveConnectedMobile2==true|| haveConnectedWifi2==true)
                new Connection_and_Setimages().execute(url_to_be_passed);
                else
                    Toast.makeText(getActivity().getApplicationContext(),"Please switch on ur Internet connection",Toast.LENGTH_SHORT)
                            .show();
                break;
            case R.id.favourite:
                life=R.id.favourite;
                acessFavourite();break;
        }
        return super.onOptionsItemSelected(item);
    }

}
