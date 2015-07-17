package com.example.timetrial;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DisplayAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> task;
	private ArrayList<String> date;
	private ArrayList<String> time;
	
	public DisplayAdapter(Context c, ArrayList<String> tid,ArrayList<String> ttask, ArrayList<String> tdate, ArrayList<String> ttime) {
		this.mContext = c;

		this.id = tid;
		this.task = ttask;
		this.date = tdate;
		this.time = ttime;
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
			child = layoutInflater.inflate(R.layout.listcell, null);
			mHolder = new Holder();
			mHolder.key_Id = (TextView) child.findViewById(R.id.keyId);
			mHolder.t_task = (TextView) child.findViewById(R.id.task);
			mHolder.t_date = (TextView) child.findViewById(R.id.date);
			mHolder.t_time = (TextView) child.findViewById(R.id.time);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		mHolder.key_Id.setText(id.get(pos));
		mHolder.t_task.setText(task.get(pos));
		mHolder.t_date.setText(date.get(pos));
		mHolder.t_time.setText(time.get(pos));

		return child;
	}

	public class Holder {
		TextView key_Id;
		TextView t_task;
		TextView t_date;
		TextView t_time;
	}

}
