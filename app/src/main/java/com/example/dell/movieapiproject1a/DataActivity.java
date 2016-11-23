package com.example.dell.movieapiproject1a;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.dell.movieapiproject1a.Fragments.DataFragment;

public class DataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainActivity.mTwoPane==false){
            setContentView(R.layout.activity_data);

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.frame, new DataFragment()).commit();
                Log.e("Data_layout", "added");
            }
        }
        else
            finish();


    }
}
