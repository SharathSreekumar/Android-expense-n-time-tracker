package com.example.timetrial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelpert extends SQLiteOpenHelper {

	static String DATABASE_NAME="userdata";
	public static final String TABLE_NAME="timin";
	public static final String KEY_TASK="task";
	public static final String KEY_DATE="date";
	public static final String KEY_TIME="time";
	public static final String KEY_ID="id";
	
	public DBHelpert(Context context) {super(context, DATABASE_NAME, null, 1);}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_TASK+" TEXT, "+KEY_DATE+" DATE, "+ KEY_TIME +" FLOAT)";
		db.execSQL(CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(db);

	}
	
}
