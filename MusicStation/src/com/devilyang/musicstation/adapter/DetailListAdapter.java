package com.devilyang.musicstation.adapter;

import java.util.ArrayList;

import com.android.volley.toolbox.NetworkImageView;
import com.devilyang.musicstation.R;
import com.devilyang.musicstation.bean.PeopleYueDanListBean;
import com.devilyang.musicstation.bean.RelatedVideosListBean;
import com.devilyang.musicstation.bean.RelatedVideosListBean.RelatedVideos;
import com.devilyang.musicstation.net.RequestManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DetailListAdapter extends BaseAdapter{

	private ArrayList<Object> objects;
	private Context context;
	private boolean isRelative;
	public DetailListAdapter(ArrayList<Object> objects, Context context,
			boolean isRelative) {
		super();
		this.objects = objects;
		this.context = context;
		this.isRelative = isRelative;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
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
		if(isRelative){
			RelatedVideos relatedVideos = (RelatedVideos)objects.get(position);
			holder.imageView.setDefaultImageResId(R.drawable.title_mv);
			holder.imageView.setImageUrl(relatedVideos.getPosterPic(), RequestManager.getImageLoader());
			holder.author.setText(relatedVideos.getArtistName());
			holder.name.setText(relatedVideos.getTitle());
		}else{
			PeopleYueDanListBean.Videos videos = (PeopleYueDanListBean.Videos)objects.get(position);
			holder.imageView.setDefaultImageResId(R.drawable.title_mv);
			holder.imageView.setImageUrl(videos.getPosterPic(), RequestManager.getImageLoader());
			holder.author.setText(videos.getArtistName());
			holder.name.setText(videos.getTitle());
		}
		return convertView;
	}
	class ViewHolder{
		NetworkImageView imageView;
		TextView name;
		TextView author;
	}
}
