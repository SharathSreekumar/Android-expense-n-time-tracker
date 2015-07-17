package com.example.timetrial;

import java.util.ArrayList;

import com.example.timetrial.DisplayAdapter.Holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DisplayexAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> date;
	private ArrayList<String> description;
	private ArrayList<String> amount;
	private ArrayList<String> receipt;
	
	public DisplayexAdapter(Context c, ArrayList<String> exid,ArrayList<String> exdate, ArrayList<String> exdescription, ArrayList<String> examount, ArrayList<String> exreceipt) {
		this.mContext = c;

		this.id = exid;
		this.date = exdate;
		this.description = exdescription;
		this.amount = examount;
		this.receipt = exreceipt;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return id.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int pos, View child, ViewGroup parent) {
		Holder mHolder;
		LayoutInflater layoutInflater;
		if (child == null) {
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.layoutlist, null);
			mHolder = new Holder();
			mHolder.key_Id = (TextView) child.findViewById(R.id.listid);
			mHolder.ex_date = (TextView) child.findViewById(R.id.listdate);
			mHolder.ex_description = (TextView) child.findViewById(R.id.listdescription);
			mHolder.ex_amount = (TextView) child.findViewById(R.id.listamount);
			mHolder.ex_receipt = (TextView) child.findViewById(R.id.listreceipt);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		mHolder.key_Id.setText(id.get(pos));
		mHolder.ex_date.setText(date.get(pos));
		mHolder.ex_description.setText(description.get(pos));
		mHolder.ex_amount.setText(amount.get(pos));
		mHolder.ex_receipt.setText(receipt.get(pos));

		return child;
	}

	public class Holder {
		TextView key_Id;
		TextView ex_date;
		TextView ex_description;
		TextView ex_amount;
		TextView ex_receipt;
	}
}
