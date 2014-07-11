package com.devilyang.musicstation.adapter;

import java.util.ArrayList;

import com.android.volley.toolbox.NetworkImageView;
import com.devilyang.musicstation.R;
import com.devilyang.musicstation.bean.VChartListBean;
import com.devilyang.musicstation.bean.VChartListBean.Videos;
import com.devilyang.musicstation.net.LogUtil;
import com.devilyang.musicstation.net.RequestManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VChartListAdapter extends BaseAdapter{

//	private VChartListBean chartListBean;
	private Context context;
	
	private ArrayList<VChartListBean.Videos> videos;
	
	

	public VChartListAdapter(Context context, ArrayList<Videos> videos) {
		super();
		this.context = context;
		this.videos = videos;
	}


	@Override
	public int getCount() {
		
		return videos.size();
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
			convertView = LayoutInflater.from(context).inflate(R.layout.vchart_list_item, null);
			holder.author = (TextView)convertView.findViewById(R.id.author_name);
			holder.number = (TextView)convertView.findViewById(R.id.vchart_number);
			holder.posterImg = (NetworkImageView)convertView.findViewById(R.id.poster_img);
			holder.titleName = (TextView)convertView.findViewById(R.id.title_name);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		LogUtil.d("VChartListAdapter", " getView postion = " +position);
		Videos video = videos.get(position);
		holder.author.setText(video.getArtistName());
		holder.number.setText(""+(position+1));
		holder.posterImg.setDefaultImageResId(R.drawable.title_mv);
		holder.posterImg.setImageUrl(video.getPosterPic(), RequestManager.getImageLoader());
		holder.titleName.setText(video.getTitle());
		return convertView;
	}
	class ViewHolder{
		NetworkImageView posterImg;
		TextView number;
		TextView titleName;
		TextView author;
	}
}
