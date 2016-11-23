package com.example.dell.movieapiproject1a;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.dell.movieapiproject1a.Fragments.DetailActivityFragment;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainActivity.mTwoPane==true)
            finish();
        if (MainActivity.mTwoPane == false){
            setContentView(R.layout.activity_details);
        if (savedInstanceState == null && MainActivity.mTwoPane!=true) {
            getSupportFragmentManager().beginTransaction().add(R.id.frame, new DetailActivityFragment()).commit();
            Log.e("Details_layout", "added");
        }
    }
        else
            finish();
    }

}
