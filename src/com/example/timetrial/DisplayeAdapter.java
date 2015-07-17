package com.example.timetrial;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DisplayeAdapter extends BaseAdapter {

	DisplayActivity x;
	public int m=1;
	private Context mContext;
	private ArrayList<String> date;
	private ArrayList<String> description;
	private ArrayList<String> amount;
	private ArrayList<String> receipt;
	
	public DisplayeAdapter(Context c, ArrayList<String> edate,ArrayList<String> edescription, ArrayList<String> eamount, ArrayList<String> ereceipt) {
		this.mContext = c;

		this.date = edate;
		this.description = edescription;
		this.amount = eamount;
		this.receipt = ereceipt;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		//m=x.count;
		m=3;
		return m;
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
			mHolder.e_date = (TextView) child.findViewById(R.id.listdate);
			mHolder.e_description = (TextView) child.findViewById(R.id.listdescription);
			mHolder.e_amount = (TextView) child.findViewById(R.id.listamount);
			mHolder.e_receipt = (TextView) child.findViewById(R.id.listreceipt);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		mHolder.e_date.setText(date.get(pos));
		mHolder.e_description.setText(description.get(pos));
		mHolder.e_amount.setText(amount.get(pos));
		mHolder.e_receipt.setText(receipt.get(pos));

		return child;
	}

	public class Holder {
		TextView e_date;
		TextView e_description;
		TextView e_amount;
		TextView e_receipt;
	}
}
