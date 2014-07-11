package com.devilyang.musicstation.adapter;

import java.util.ArrayList;

import com.android.volley.toolbox.NetworkImageView;
import com.devilyang.musicstation.R;
import com.devilyang.musicstation.bean.MVListBean;
import com.devilyang.musicstation.net.RequestManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MVListAdapter extends BaseAdapter{
	private ArrayList<MVListBean.Videos> mvVideoBeans;
	private Context context;
	
	public MVListAdapter(ArrayList<MVListBean.Videos> mvListBean, Context context) {
		super();
		this.mvVideoBeans = mvListBean;
		this.context = context;
	}

	@Override
	public int getCount() {
		return mvVideoBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.mv_list_item, null);
			holder.imageView = (NetworkImageView)convertView.findViewById(R.id.poster_img);
			holder.name = (TextView)convertView.findViewById(R.id.name);
			holder.author = (TextView)convertView.findViewById(R.id.author);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.imageView.setDefaultImageResId(R.drawable.title_mv);
		holder.imageView.setImageUrl(mvVideoBeans.get(position).getPosterPic(), RequestManager.getImageLoader());
		holder.author.setText(mvVideoBeans.get(position).getArtistName());
		holder.name.setText(mvVideoBeans.get(position).getTitle());
		return convertView;
	}
	class ViewHolder{
		NetworkImageView imageView;
		TextView name;
		TextView author;
	}
}
