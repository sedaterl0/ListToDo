package com.example.sedater.listtodo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Sedat Er on 18.12.2017.
 */

public class database extends SQLiteOpenHelper {
    private final static int db_version=1;
    private final static String db_name="dbtodolist";
    private final static String db_table="todolist";
    private final static String db_taskbaslik="taskbaslik";
    private final static String db_taskid="taskid";
    private final static String db_taskicerik="taskicerik";
    private final static String db_taskpriority="taskpriority";
    private final static String db_taskdate="taskdate";

    public database(Context context){
        super(context,db_name,null,db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query="CREATE TABLE "+ db_table +"("
                + db_taskid + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +db_taskbaslik+ " TEXT,"
                +db_taskicerik+ " TEXT,"
                +db_taskpriority+ " TEXT,"
                +db_taskdate+ " TEXT"+")";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertTask(String baslik,String icerik,String priority,String date){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(db_taskbaslik,baslik);
        values.put(db_taskicerik,icerik);
        values.put(db_taskpriority,priority);
        values.put(db_taskdate,date);

        db.insert(db_table,null,values);
        db.close();
    }
    public void deleteTask(int id){

        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(db_table,db_taskid+"=?",new String[]{String.valueOf(id)});
        db.close();

    }
    public HashMap<String,String>taskDetay(int id){
        HashMap<String,String>task=new HashMap<String, String>();
        String query="SELECT * FROM "+db_table+" WHERE taskid = "+id;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);

        cursor.moveToFirst();
        if(cursor.getCount()>0){
            task.put(db_taskbaslik,cursor.getString(1));
            task.put(db_taskicerik,cursor.getString(2));
            task.put(db_taskpriority,cursor.getString(3));
            task.put(db_taskdate,cursor.getString(4));
        }
        cursor.close();
        db.close();
        return task;
    }
    public ArrayList<HashMap<String,String>>tasks(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+db_table;
        Cursor cursor=db.rawQuery(query,null);

        ArrayList<HashMap<String,String>>tasklist=new ArrayList<HashMap<String, String>>();

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String>map=new HashMap<String, String>();
                for(int i=0;i<cursor.getColumnCount();i++){

                    map.put(cursor.getColumnName(i),cursor.getString(i));

                }
                tasklist.add(map);
            }while(cursor.moveToNext());
        }
        db.close();
        return tasklist;
    }

    public void taskDuzenle(String baslik,String icerik,String priority,String date,int id){


        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(db_taskbaslik,baslik);
        values.put(db_taskicerik,icerik);
        values.put(db_taskpriority,priority);
        values.put(db_taskdate,date);

        db.update(db_table,values,db_taskid + " = ?",new String[]{String.valueOf(id)});

    }

}
