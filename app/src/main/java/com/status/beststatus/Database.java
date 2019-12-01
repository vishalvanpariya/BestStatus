package com.status.beststatus;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;


public class Database extends SQLiteOpenHelper {
    static final private String DB_NAME = "FavData";
    static final private String DB_TABLE = "FavTable";
    static final private int DB_VER = 1;

    Context context;
    SQLiteDatabase db;

    public Database(Context context){
        super(context,DB_NAME,null,DB_VER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+DB_NAME+" (id INTEGER primary key autoincrement,status text,lang text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+DB_TABLE);
        onCreate(db);
    }

    public void insert(String status,String lang){
        db = getWritableDatabase();
        db.execSQL("insert into "+DB_NAME+" (status,lang) values ('"+status+"','"+lang+"')");
    }

    public LinkedList<Status> getfav(){
        db=getReadableDatabase();
        LinkedList<Status> list = new LinkedList<Status>();
        Cursor cr = db.rawQuery("Select * from "+DB_NAME,null);
        while (cr.moveToNext()){
            list.add(
                    new Status(
                            cr.getInt(0),cr.getString(1),cr.getString(2),0F,0F
                    )
            );
        }
        return list;
    }

    public boolean exist(String status){
        boolean temp=false;
        db=getReadableDatabase();
        Cursor cr = db.rawQuery("Select * from "+DB_NAME+" where status='"+status+"'",null);
        while (cr.moveToNext()){
            temp=true;
        }
        return temp;
    }

    public void delete(String id){
        db = getWritableDatabase();
        db.execSQL("delete from "+DB_NAME+" where status='"+id+"'");
    }
}
