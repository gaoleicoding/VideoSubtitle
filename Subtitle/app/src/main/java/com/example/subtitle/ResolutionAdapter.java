package com.example.subtitle;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ResolutionAdapter extends BaseAdapter {

	List<VideoPathObject> list;
	LayoutInflater inflater;
	Context context;
	int selectedPosition = 0;

	public ResolutionAdapter(List<VideoPathObject> list, Context context) {
		selectedPosition=list.size()-1;
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void changeList(List<VideoPathObject> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void changePosition(int position) {
		selectedPosition = position;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.resolution_item, null);

			holder.resolution_name = (TextView) convertView
					.findViewById(R.id.resolution_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final VideoPathObject object = list.get(position);
		if (position == selectedPosition) {
			holder.resolution_name.setTextColor(Color.parseColor("#00a1f1"));
		} else {
			holder.resolution_name.setTextColor(Color.parseColor("#bbffffff"));
		}
		holder.resolution_name.setText(object.videoStatus);
		return convertView;
	}

	
	class ViewHolder {
		TextView resolution_name;
	}
}
