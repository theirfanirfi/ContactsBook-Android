package com.contactsapp.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB extends SQLiteOpenHelper {
    //database name
    public final static String DB_NAME = "contacts_db.db";
    //name of the table inside the database
    public final static String TABLE_CONTACTS = "contacts";

    //columns of the @TABLE_CONTACTS are:
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String NUMBER = "number";
    public static final String COUNTRY_CODE = "country_code";

    //query for creating table inside the database

    public final static String CREATE_TABLE = "create table "+TABLE_CONTACTS+" ( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(50), "+ADDRESS+" TEXT, "+NUMBER+" TEXT,"+COUNTRY_CODE+" INTEGER);";

    //in the constructor the database wll be created.
    public DB(Context context) {
        super(context, DB_NAME, null, 6);
       // Log.i("DATABASECONTACTAPP: ","created DATABAE");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //here the query to create database will be executed
        try {
            db.execSQL(CREATE_TABLE);
        }catch (Exception e){
            Log.i("DATABASECONTACTAPP: ",e.getMessage());

        }
       // Log.i("DATABASECONTACTAPP: ","created table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE "+TABLE_CONTACTS+" ADD COLUMN country_code INTEGER DEFAULT 0");
           // Log.i("DATABASECONTACTAPP: ","upgraded.");

        }
    }

    public boolean addContact(ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
        long res = db.insert(TABLE_CONTACTS,null,contentValues);
        if(res == -1){
            Log.i("DATABASECONTACTAPP: ","true");
            return false;
        }else {
            Log.i("DATABASECONTACTAPP: ","false");
            return true;
        }
        }catch (Exception e){
            Log.i("DATABASECONTACTAPP: ",e.getMessage());
            return false;

        }
    }

    //to get all contacts of the table
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT id as _id,name FROM "+TABLE_CONTACTS,null);
        return res;
    }

    //get contact by Id
    public Cursor getById(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_CONTACTS+" WHERE id = '"+id+"'",null);
        return res;
    }

    //deleting a contact
    public boolean deleteContact(String id){
        SQLiteDatabase db = this.getWritableDatabase();
       int delete = db.delete(TABLE_CONTACTS,"id = ?",new String[]{id});
        return delete > 0 ? true: false;
    }

    //updating contacting by ID
    public boolean updateContact(ContentValues values,String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int updated = db.update(TABLE_CONTACTS,values," id = ?",new String[]{id});
        return updated > 0 ? true: false;

    }
}
