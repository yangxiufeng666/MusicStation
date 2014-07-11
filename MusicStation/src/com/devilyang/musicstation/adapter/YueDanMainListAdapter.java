package com.devilyang.musicstation.adapter;

import java.util.ArrayList;

import com.android.volley.toolbox.NetworkImageView;
import com.devilyang.musicstation.R;
import com.devilyang.musicstation.bean.YueDanListBean.PlayLists;
import com.devilyang.musicstation.net.RequestManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class YueDanMainListAdapter extends BaseAdapter{

	private ArrayList<PlayLists> playLists;
	private Context context;
	
	public YueDanMainListAdapter(ArrayList<PlayLists> playLists, Context context) {
		super();
		this.playLists = playLists;
		this.context = context;
	}

	@Override
	public int getCount() {
		return playLists.size();
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
			convertView = LayoutInflater.from(context).inflate(R.layout.yue_dan_main_list_item, null);
			holder.imageView = (NetworkImageView)convertView.findViewById(R.id.poster_img);
			holder.authorImage = (NetworkImageView)convertView.findViewById(R.id.author_img);
			holder.authorName = (TextView)convertView.findViewById(R.id.author_name);
			holder.count = (TextView)convertView.findViewById(R.id.collect_count);
			holder.title = (TextView)convertView.findViewById(R.id.title_name);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.authorImage.setImageUrl(playLists.get(position).getCreator()
				.getSmallAvatar(), RequestManager.getImageLoader());
		holder.authorName.setText(playLists.get(position).getCreator()
				.getNickName());
		String str = context.getString(R.string.collect_mv_count);
		String count = String.format(str, playLists.get(position).getVideoCount());
		holder.count.setText(count);
		holder.title.setText(playLists.get(position).getTitle());
		holder.imageView.setDefaultImageResId(R.drawable.title_mv);
		holder.imageView.setImageUrl(playLists.get(position).getPlayListPic(),
				RequestManager.getImageLoader());
		return convertView;
	}
	class ViewHolder{
		NetworkImageView imageView;
		NetworkImageView authorImage;
		TextView title;
		TextView count;
		TextView authorName;
	}
}
