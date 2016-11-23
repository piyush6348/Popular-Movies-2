package com.example.dell.movieapiproject1a;



import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.example.dell.movieapiproject1a.Fragments.DataFragment;
import com.example.dell.movieapiproject1a.Fragments.DetailActivityFragment;
import com.example.dell.movieapiproject1a.Fragments.MainActivityFragment;


public class MainActivity extends AppCompatActivity implements MainActivityFragment.OnclicListener,MainActivityFragment.fav{
   // public static DatabaseHandler db;
   public static boolean mTwoPane=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  if(MainActivityFragment.life==R.id.favourite){
           // FragmentManager fm=getSupportFragmentManager();
          //  Fragment f=getFragmentManager().findFragmentById(R.id.frag1);
          //  Fragment f=getSupportFragmentManager().findFragmentById(R.id.frag1);
           //f.onStart();
           // MainActivityFragment obj=new MainActivityFragment();
            //obj.onStart();
            //getSupportFragmentManager().beginTransaction().replace(R.id.frag1,new MainActivityFragment()).commit();
        }*/
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.frame)!=null){
            mTwoPane=true;
            if(savedInstanceState==null){
                Log.e("MainActivity","onCreate");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new DetailActivityFragment()).commit();
            }
           // else
            //    mTwoPane=false;
        }
        else
            mTwoPane=false;
    }


    @Override
    public void onClick(String s, int po) {
       /* Intent i = new Intent(getBaseContext(), DetailActivityFragment.class);
        Bundle extras = new Bundle();
        extras.putInt("Position", po);
        extras.putString("j", s);
        i.putExtras(extras);
        startActivity(i);*/
        Log.e("json callback",s);
        Log.e("index callback"," "+po);
        Log.e("MainActivity","onClick");
        Bundle b=new Bundle();
        b.putInt("index",po);
        b.putString("json",s);
        DetailActivityFragment fg=new DetailActivityFragment();
        fg.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fg).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivityFragment.life=0;
    }

    @Override
    public void favClick(Bundle x) {
        DataFragment df=new DataFragment();
        df.setArguments(x);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,df).commit();
    }
}
