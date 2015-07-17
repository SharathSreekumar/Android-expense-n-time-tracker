package com.example.timetrial;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class DisplayexActivity extends Activity {

	private DBHelperex exHelper;
	private SQLiteDatabase dataBase;

	private ArrayList<String> listid = new ArrayList<String>();
	private ArrayList<String> listdate = new ArrayList<String>();
	private ArrayList<String> listdescription = new ArrayList<String>();
	private ArrayList<String> listamount = new ArrayList<String>();
	private ArrayList<String> listreceipt = new ArrayList<String>();

	private ListView expenseList;
	private ImageButton add;
	
	private AlertDialog.Builder build ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_display);
		
		expenseList = (ListView) findViewById(R.id.listViewDB);

		exHelper = new DBHelperex(this);

		add = (ImageButton)findViewById(R.id.add);
		
		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), AddexActivity.class);
				i.putExtra("update", false);
				startActivity(i);
			}
		});
		
		//click to update data
        expenseList.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Intent i = new Intent(getApplicationContext(), AddexActivity.class);
                
                i.putExtra("Id", listid.get(arg2));
                i.putExtra("Date", listdate.get(arg2));
                i.putExtra("Description", listdescription.get(arg2));
                i.putExtra("Amount", listamount.get(arg2));
                i.putExtra("Receipt", listreceipt.get(arg2));
                i.putExtra("update", true);
                startActivity(i);

            }
        });
        
      //long click to delete data
      		expenseList.setOnItemLongClickListener(new OnItemLongClickListener() {

      			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,final int arg2, long arg3) {

      				build = new AlertDialog.Builder(DisplayexActivity.this);
      				build.setTitle("Delete " + listdate.get(arg2) + " "+ listdescription.get(arg2)+" "+listamount.get(arg2)+" "+listreceipt.get(arg2));
      				build.setMessage("Do you want to delete ?");
      				build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
      							public void onClick(DialogInterface dialog,int which) {

      								Toast.makeText(
      										getApplicationContext(),listdate.get(arg2) + " "+ listdescription.get(arg2) +" "+ listreceipt.get(arg2) + " is deleted.", 3000).show();

      								dataBase.delete(DBHelperex.TABLE_NAME,DBHelperex.KEY_ID  + "="+ listid.get(arg2), null);
      								displayData();
      								dialog.cancel();
      							}
      						});
      				build.setNegativeButton("No",new DialogInterface.OnClickListener() {

      							public void onClick(DialogInterface dialog,int which) {
      								dialog.cancel();
      							}
      						});
      				AlertDialog alert = build.create();
      				alert.show();

      				return true;
      			}
      		});
	}
	
	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}
	
	private void displayData() {
		dataBase = exHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "+ DBHelperex.TABLE_NAME, null);

		listdate.clear();
		listdescription.clear();
		listamount.clear();
		listreceipt.clear();
		if (mCursor.moveToFirst()) {
			do {
				listid.add(mCursor.getString(mCursor.getColumnIndex(DBHelperex.KEY_ID)));
				listdate.add(mCursor.getString(mCursor.getColumnIndex(DBHelperex.KEY_DATE)));
				listdescription.add(mCursor.getString(mCursor.getColumnIndex(DBHelperex.KEY_DESCRIPTION)));
				listamount.add(mCursor.getString(mCursor.getColumnIndex(DBHelperex.KEY_AMOUNT)));
				listreceipt.add(mCursor.getString(mCursor.getColumnIndex(DBHelperex.KEY_RECEIPT)));

			} while (mCursor.moveToNext());
		}
		DisplayexAdapter disadpt = new DisplayexAdapter(DisplayexActivity.this,listid,listdate,listdescription, listamount, listreceipt);
		expenseList.setAdapter(disadpt);
		mCursor.close();
	}
}
