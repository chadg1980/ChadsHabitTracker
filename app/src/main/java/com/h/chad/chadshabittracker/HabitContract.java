package com.h.chad.chadshabittracker;

import android.provider.BaseColumns;

/**
 * Created by chad on 2/8/2017.
 */

public class HabitContract {
    //Empty constructor so the class is not accidentally initialized
    private HabitContract(){}

    public static final class HabitEntry implements BaseColumns{
        //Name of the table
        public static final String TABLE_NAME = "habit";
        //Name of the columns
        public final static String COLUMN_HABIT_NAME = "name";
        public final static String COLUMN_HABIT_DAY_TIME = "dayTime";
        public final static String COLUMN_HABIT_TYPE = "type";

        //possible values for type of habit
        public final static int GOOD_HABIT = 0;
        public final static int BAD_HABIT = 1;
    }
}
