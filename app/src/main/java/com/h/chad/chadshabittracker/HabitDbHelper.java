package com.h.chad.chadshabittracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.h.chad.chadshabittracker.HabitContract.HabitEntry;

/**
 * Created by chad on 2/8/2017.
 * dbHelper manages database creattion
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = HabitDbHelper.class.getName();

    //name of the db file
    public final static String DATABASE_NAME = "habittracker.db";
    //database version
    public final static int DATABASE_VERSION = 1;

    /*
    * Construct a new instance of HabitDbHelper
    * */
    public HabitDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //When the Db is created for the first time onCreate is called
    @Override
    public void onCreate(SQLiteDatabase database) {
        String SQL_CREATE_HABITTRACKER_TABLE =
                "CREATE TABLE " + HabitEntry.TABLE_NAME + " (" +
                        HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL," +
                        HabitEntry.COLUMN_HABIT_DAY_TIME + " INTEGER NOT NULL," +
                        HabitEntry.COLUMN_HABIT_TYPE  + "  INTEGER NOT NULL );";
        //Execute the SQL command
        database.execSQL(SQL_CREATE_HABITTRACKER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //The database is at V1.
    }
    public static SQLiteDatabase getReadableDatabase(Context context){
        HabitDbHelper myDbHelper = new HabitDbHelper(context);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        return db;
    }
}