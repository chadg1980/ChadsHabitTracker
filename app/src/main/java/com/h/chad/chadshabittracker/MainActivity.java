package com.h.chad.chadshabittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.h.chad.chadshabittracker.HabitContract.HabitEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getName();
    public HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No UI for this project.
        //But I have some text to check my work
        setContentView(R.layout.activity_main);
        mDbHelper = new HabitDbHelper(this);
        intsertHabit();
        Cursor myCursor = cursorData();
    }
    private void intsertHabit() {
        //Get the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues v1 = valuesToInsert0();
        long columnID = db.insert(HabitEntry.TABLE_NAME, null, v1);
        if(columnID == -1){
            Log.i(LOG_TAG, "Column not inserted");
        }else{ Log.i(LOG_TAG, "Column " + columnID + " insterted");}

        ContentValues v2 = valuesToInsert1();
        columnID = db.insert(HabitEntry.TABLE_NAME, null, v2);
        if(columnID == -1){
            Log.i(LOG_TAG, "Column not inserted");
        }else{ Log.i(LOG_TAG, "Column " + columnID + " insterted");}

    }
    private ContentValues valuesToInsert0(){
        //Create values
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, "Ran 1 mile");
        values.put(HabitEntry.COLUMN_HABIT_DAY_TIME, System.currentTimeMillis() );
        values.put(HabitEntry.COLUMN_HABIT_TYPE, HabitEntry.GOOD_HABIT);
        return values;
    }

    private ContentValues valuesToInsert1(){
        //Create values
        //Date February 1, 2017
        String date = "2-1-2017";
        //8 AM
        String time = "8:00";
        //Combine date and time to parse
        String dateTime = date + " " + time;
        //switch the date and time to mills for db storage in int
        long mills = dateTimeToMilli(dateTime);
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, "smoked a cigarette");
        values.put(HabitEntry.COLUMN_HABIT_DAY_TIME, mills );
        values.put(HabitEntry.COLUMN_HABIT_TYPE, HabitEntry.BAD_HABIT);
        return values;
    }

    private long dateTimeToMilli(String dateTimeString){
        long dateMills = -1;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("M-d-yyyy hh:mm");
            Date date = formatter.parse(dateTimeString);
            dateMills = date.getTime();
        }catch (ParseException e){
            Log.i(LOG_TAG, "parse failed", e);
        }
        return dateMills;
    }
    private Cursor cursorData() {
        //access the database
        //create a sqlite instance
        SQLiteDatabase db = HabitDbHelper.getReadableDatabase(this);

        String [] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_DAY_TIME,
                HabitEntry.COLUMN_HABIT_TYPE
        };
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        TextView countTextView = (TextView)findViewById(R.id.showCount);
        countTextView.setText("There are :" + Integer.toString(cursor.getCount()) + " tables\n");
        countTextView.append("\n" +
                HabitEntry._ID + " - " +
                HabitEntry.COLUMN_HABIT_NAME + " - " +
                HabitEntry.COLUMN_HABIT_DAY_TIME + " - " +
                HabitEntry.COLUMN_HABIT_TYPE + " - "
        );

        int idColumn = cursor.getColumnIndex(HabitEntry._ID);
        int nameColumn = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
        int dateColumn = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DAY_TIME);
        int typeColumn = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TYPE);


        while (cursor.moveToNext()){
            int currentID = cursor.getInt(idColumn);
            String currentName = cursor.getString(nameColumn);
            int currentDate = cursor.getInt(dateColumn);
            int currentType = cursor.getInt(typeColumn);
            String habitType = getHabitType(currentType);


            countTextView.append("\n"   +
                    currentID   + " - " +
                    currentName + " - " +
                    currentDate + " - " +
                    habitType);
        }
        return cursor;
    }

    private String getHabitType(int typeColumn) {
        if(typeColumn == 0)return "good";
        return "bad";
    }

}