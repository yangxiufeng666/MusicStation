package com.devilyang.musicstation.fragment;

import com.devilyang.musicstation.R;
import com.devilyang.musicstation.bean.PeopleYueDanListBean;
import com.devilyang.musicstation.bean.RelatedVideosListBean;
import com.devilyang.musicstation.bean.RelatedVideosListBean.RelatedVideos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UpdateDetailFragment extends Fragment {
	RelatedVideos relatedVideos;
	private TextView title;
	private TextView author;
	private TextView collect;
	private TextView updateTimes;
	private TextView playTimes;
	private TextView descript;
	
	public static Fragment getInstance(RelatedVideos relatedVideos){
		UpdateDetailFragment fragment = new UpdateDetailFragment();
		fragment.relatedVideos = relatedVideos;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.detail_content_fragment, null); 
		initData(v);
		return v;
	}
	private void initData(View v){
		author = (TextView)v.findViewById(R.id.author);
		title = (TextView)v.findViewById(R.id.title);
		collect = (TextView)v.findViewById(R.id.count);
		updateTimes = (TextView)v.findViewById(R.id.update_time);
		playTimes = (TextView)v.findViewById(R.id.play_times);
		descript = (TextView)v.findViewById(R.id.descript);
		update(relatedVideos);
	}
	private void update(RelatedVideos relatedVideos){
		String str;
		String formatStr;
		str = getActivity().getString(R.string.detail_author);
		formatStr = String.format(str, relatedVideos.getArtistName());
		author.setText(formatStr);
		title.setText(relatedVideos.getTitle());
		str = getActivity().getString(R.string.detail_updatetime);
		formatStr = String.format(str, relatedVideos.getRegdate());
		updateTimes.setText(formatStr);
		descript.setText(relatedVideos.getDescription());
	}
}
