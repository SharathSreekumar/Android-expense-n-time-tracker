package com.example.timetrial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper{
	
	private static final String CREATE_TABLE="create table "+Constants.TABLE_NAME+" ("+Constants.DATE_NAME+" long primary key, "+Constants.DESCRIPTION_NAME+" text not null, "+Constants.AMOUNT_NAME+" float not null, "+Constants.RECEIPT_NAME+" text not null);";

	public DbHelper(Context context) {super(context, Constants.DATABASE_NAME, null, 1);}

	@Override
	public void onCreate(SQLiteDatabase DB) {
		
		// TODO Auto-generated method stub
		//String tblCreateScript="CREATE TABLE TRIAL(DATE TEXT PRIMARY KEY "+" AMOUNT FLOAT NOT NULL"+"DESCRIPTION TEXT NOT NULL,"+"RECEIPT TEXT NOT NULL);";
		Log.v("DbHelper onCreate","Creating all the tables");
		try {
		DB.execSQL(CREATE_TABLE);
		} catch(SQLiteException ex) {
		Log.v("Create table exception", ex.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w("TaskDBAdapter", "Upgrading from version "+ oldVersion
				+" to "+ newVersion +", which will destroy all old data");
				db.execSQL("drop table if exists "+ Constants.TABLE_NAME);
				onCreate(db);
	}
}
