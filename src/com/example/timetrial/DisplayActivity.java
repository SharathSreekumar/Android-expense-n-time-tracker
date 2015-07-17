package com.example.timetrial;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.os.Build;

import java.util.ArrayList;


public class DisplayActivity extends Activity {
	
	public int count=0;
	private DbHelper eHelper;
	private SQLiteDatabase DataBase;

	private ArrayList<String> amount = new ArrayList<String>();
	private ArrayList<String> description = new ArrayList<String>();
	private ArrayList<String> date = new ArrayList<String>();
	private ArrayList<String> receipt = new ArrayList<String>();

	private ListView expenseList;
	private ImageButton add_button;
	
	private AlertDialog.Builder build ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_display);
		
		expenseList = (ListView) findViewById(R.id.listViewDB);

		eHelper = new DbHelper(this);

		add_button = (ImageButton)findViewById(R.id.add);
		
		add_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), AddActivity.class);
				i.putExtra("update", false);
				startActivity(i);
			}
		});
		
		//click to update data
        expenseList.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Intent i = new Intent(getApplicationContext(), AddActivity.class);
                
                i.putExtra("Date", date.get(arg2));
                i.putExtra("Description", description.get(arg2));
                i.putExtra("Amount", amount.get(arg2));
                i.putExtra("Receipt", receipt.get(arg2));
                i.putExtra("update", true);
                startActivity(i);

            }
        });
				 
      //long click to delete data
		expenseList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {

				build = new AlertDialog.Builder(DisplayActivity.this);
				build.setTitle("Delete " + date.get(arg2) + " "+ description.get(arg2)+" "+amount.get(arg2)+" "+receipt.get(arg2));
				build.setMessage("Do you want to delete ?");
				build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {

								Toast.makeText(
										getApplicationContext(),date.get(arg2) + " "+ description.get(arg2) +" "+ receipt.get(arg2) + " is deleted.", 3000).show();

								DataBase.delete(Constants.TABLE_NAME,Constants.DATE_NAME + "="+ date.get(arg2), null);
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
		DataBase = eHelper.getWritableDatabase();
		Cursor mCursor = DataBase.rawQuery("SELECT * FROM "+ Constants.TABLE_NAME, null);

		date.clear();
		description.clear();
		amount.clear();
		receipt.clear();
		if (mCursor.moveToFirst()) {
			do {
				date.add(mCursor.getString(mCursor.getColumnIndex(Constants.DATE_NAME)));
				description.add(mCursor.getString(mCursor.getColumnIndex(Constants.DESCRIPTION_NAME)));
				amount.add(mCursor.getString(mCursor.getColumnIndex(Constants.AMOUNT_NAME)));
				receipt.add(mCursor.getString(mCursor.getColumnIndex(Constants.RECEIPT_NAME)));

			} while (mCursor.moveToNext());
		}
		DisplayeAdapter disadpt = new DisplayeAdapter(DisplayActivity.this,date,description, amount, receipt);
		expenseList.setAdapter(disadpt);
		mCursor.close();
	}
	
//	private void populateListViewfromDB(){
//		
//		DbHelper dbOpenHelper = new DbHelper(getApplicationContext(),Constants.DATABASE_NAME,null,1);
//		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
//		Cursor cursor = database.query(Constants.TABLE_NAME, null , null , null , null , null , null );
//		if(cursor!=null)
//		{
//			String[] fromFieldNames=new String[]{Constants.DATE_NAME,Constants.AMOUNT_NAME,Constants.DESCRIPTION_NAME,Constants.RECEIPT_NAME};
//			int[] toViewIDs=new int[]{R.id.checkBox1,R.id.listdate,R.id.listamount,R.id.listdescription,R.id.listreceipt};
//			if(cursor.moveToFirst())
//			{
//				do{
//					myCursorAdaptor=new SimpleCursorAdapter(this,R.layout.layoutlist,cursor,fromFieldNames,toViewIDs,0);
//					ListView myList=(ListView)findViewById(R.id.listViewDB);
//					myList.setAdapter(myCursorAdaptor);
//				}while(cursor.moveToNext());
//			}
//			
//		}
//		database.close();
//	}
}