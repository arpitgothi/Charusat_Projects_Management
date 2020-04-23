package com.example.prakash.charusatprojects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super( context,"registration.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "create table reg_data(fname text,lname text,charusatid text PRIMARY KEY,email text,password text,phon text)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL( "drop table if exists reg_data" );
    }
    //inserting
    public boolean insert(String fname ,String lname, String charusatid,String email,String password,String phon)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(  );
        contentValues.put( "fname",fname );
        contentValues.put( "lname",lname );
        contentValues.put( "charusatid",charusatid );
        contentValues.put( "email",email );
        contentValues.put( "password",password );
        contentValues.put( "phon",phon );
        long ins = db.insert( "reg_data",null,contentValues );
        if(ins==-1) return false;
        else return true;

    }

    public boolean checkid(String charusatid)
    {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "select * from reg_data where charusatid=?",new String[]{charusatid} );
        if (cursor.getCount()>0) return false;
        else return true;

    }

    public boolean checkemail(String email)
    {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "select * from reg_data where email=?",new String[]{ email } );
        if (cursor.getCount()>0) return true;
        else return false;

    }

    public boolean checkpass(String password)
    {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "select * from reg_data where password=?",new String[]{password} );
        if (cursor.getCount()>0) return true;
        else return false;

    }
}
