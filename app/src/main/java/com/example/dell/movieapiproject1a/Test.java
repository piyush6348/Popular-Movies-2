package com.example.dell.movieapiproject1a;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by dell on 3/11/2016.
 */
@SimpleSQLTable(table = "test",provider = "TestProvider")
public class Test {
    @SimpleSQLColumn(value ="_id",autoincrement = true)
    public int id=1;

    @SimpleSQLColumn(value = "MOVIE_ID",primary = true)
    public int movie_id;

    @SimpleSQLColumn("TITLE")
    public String titl;

    @SimpleSQLColumn("SYNOPSIS")
    public String syn;

    @SimpleSQLColumn("RATE")
    public int rate;

    @SimpleSQLColumn("DATE")
    public String date;

    @SimpleSQLColumn("POSTER_PATH")
    public String path;



/*
    public Test(int id, String original_title, String overview, int vote_average, String release_date, String z) {
        this.movie_id=id;
        this.titl=original_title;
        this.syn=overview;
        this.date=release_date;
        this.rate=vote_average;
        this.path=z;
    }*/
   /* public Test(){
        Log.e("Empty Constructor","Running");
    }*/
}
