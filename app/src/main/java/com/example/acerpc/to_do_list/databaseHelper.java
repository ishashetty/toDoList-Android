package com.example.acerpc.to_do_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Acer PC on 27/08/2017.
 */

public class databaseHelper extends SQLiteOpenHelper{
    private static final String to_DOList="to_do_list";
    public static String DB_table="Task";
    public static String DB_column="id";
    public static String DB_column1="TaskName";
    public static String DB_column2="TaskDescription";
    public static String DB_column3="TaskDate";
    public databaseHelper(Context context) {
        super(context,to_DOList, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+DB_table+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, TaskName TEXT not null,TaskDescription String, TaskDate String)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_table);
        onCreate(db);
    }
    public boolean insertData(String tname,String tdes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ContentValues cV=new ContentValues();
        cV.put(DB_column1,tname);
        cV.put(DB_column2,tdes);
        cV.put(DB_column3,dateFormat.format(new Date()));
        long result = db.insert(DB_table,null,cV);
        if (result ==-1)
            return false;
        else
            return true;
    }
    public ArrayList<String> getAllTask()
    {
        SQLiteDatabase db = this.getReadableDatabase();
         ArrayList<String> al=new ArrayList<String>();
        //ArrayList<String> a=new ArrayList<String>();
        Cursor res = db.rawQuery("select * from "+DB_table+"  order by "+DB_column3 +" desc",null);
        while (res.moveToNext()){
            int index=res.getColumnIndex(DB_column1);
          //  int index1=res.getColumnIndex(DB_column3);
            al.add(res.getString(index));
            //al.add(res.getString(index1));

        }
        res.close();
        db.close();
        return al;
    }

    public  ArrayList<String> getAllTaskDtae(String ts)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> al=new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        String selectQuery = "SELECT Taskname,TaskDate,TaskDescription FROM "+DB_table+" WHERE TaskName=?";
        Cursor c = db.rawQuery(selectQuery, new String[] { ts });
     if (c.moveToFirst()) {
           String temp = c.getString(c.getColumnIndex("TaskName"));
           al.add(temp);
         String temp1 = c.getString(c.getColumnIndex("TaskDate"));
         al.add(temp1);
         try {
             c1.setTime(sdf.parse(c.getString(c.getColumnIndex("TaskDate"))));
         } catch (ParseException e) {
             e.printStackTrace();
         }
         String temp4 = c.getString(c.getColumnIndex("TaskDescription"));
         //int all=Integer.parseInt(temp4);
         //c1.add(Calendar.DAY_OF_MONTH, 2);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
         //SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
         //String temp3 = sdf1.format(c1.getTime());
         al.add(temp4);

       }
        c.close();
        return al;

    }
    public Integer deleteData (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DB_table, "TaskName = ?",new String[] {name});
    }

    public boolean update(String name,String newname) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String selectQuery = "SELECT Taskname,TaskDate,TaskDescription FROM "+DB_table+" WHERE Taskname=?";
        Cursor c = db.rawQuery(selectQuery, new String[] { name });
        if (c.moveToFirst()) {
            String temp = c.getString(c.getColumnIndex("TaskDescription"));
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(temp);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.DATE,1);
            date1=calendar.getTime();
            String postpone_date=dateFormat.format(date1);
            contentValues.put(DB_column2,postpone_date);
        }
        if(newname!=null) {
            contentValues.put(DB_column1, newname);
            contentValues.put(DB_column3, dateFormat.format(new Date()));
            db.update(DB_table, contentValues, "TaskName = ?", new String[]{name});
            return true;
        }
        else{
            contentValues.put(DB_column3, dateFormat.format(new Date()));
            db.update(DB_table, contentValues, "TaskName = ?", new String[]{name});
            return true;
        }
    }
    public ArrayList<String> showNotification(String date1){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(date1);
        String s1="2018-03-20";
        ArrayList<String> al=new ArrayList<String>();
        String selectQuery = "SELECT Taskname,TaskDate,TaskDescription FROM "+DB_table+" WHERE TaskDate= ?";
        Cursor c = db.rawQuery(selectQuery,new String[]{s1});
        if (c.moveToFirst()) {
            String temp = c.getString(c.getColumnIndex("TaskDescription"));
            al.add(temp);
        }
        c.close();
        return al;


    }
    public ArrayList<String> getTodayTask(String tdes1)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String tdes="'"+tdes1.trim()+"'";
        ArrayList<String> al=new ArrayList<String>();
       //String query="select * from "+DB_table+" where TRIM(TaskDescription) = ?";
       Cursor res = db.rawQuery("SELECT * FROM "+ DB_table +" WHERE TRIM(TaskDescription) = '"+tdes1.trim()+"'", null);
     //   Cursor res = db.rawQuery(query,new String[] {"'"+tdes1.trim()+"'"});
       // Cursor res = db.query(DB_table, new String[] { DB_column1},
         //       "TaskDescription like " + tdes, null, null, null, null);

        // Cursor res = db.rawQuery("select * from "+DB_table+" where TaskDescription "+todayd +" and order by "+DB_column3 +" desc",null);
        while (res.moveToNext()){
            int index=res.getColumnIndex(DB_column1);
            //  int index1=res.getColumnIndex(DB_column3);
            al.add(res.getString(index));
            //al.add(res.getString(index1));

        }
        res.close();
        db.close();
        return al;
    }

}
