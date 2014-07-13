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

public class DetailContentFragment extends Fragment{
	
	private Object conObject;
	
	private boolean isRelative = false;
	
	private TextView title;
	private TextView author;
	private TextView collect;
	private TextView updateTimes;
	private TextView playTimes;
	private TextView descript;
	
	
	public static Fragment getInstance(Object conObject,boolean isRelative){
		DetailContentFragment fragment = new DetailContentFragment();
		fragment.conObject = conObject;
		fragment.isRelative = isRelative;
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
		String str;
		String formatStr;
		if(isRelative){
			RelatedVideosListBean relatedVideosListBean = (RelatedVideosListBean)conObject;
			str = getActivity().getString(R.string.detail_author);
			formatStr = String.format(str, relatedVideosListBean.getArtistName());
			author.setText(formatStr);
			title.setText(relatedVideosListBean.getTitle());
			str = getActivity().getString(R.string.detail_updatetime);
			formatStr = String.format(str, relatedVideosListBean.getRegdate());
			updateTimes.setText(formatStr);
			str = getActivity().getString(R.string.detail_playtimes);
			formatStr = String.format(str, relatedVideosListBean.getTotalViews());
			playTimes.setText(formatStr);
			descript.setText(relatedVideosListBean.getDescription());
		}else{
			PeopleYueDanListBean peopleYueDanListBean = (PeopleYueDanListBean)conObject;
			str = getActivity().getString(R.string.detail_author);
			formatStr = String.format(str, peopleYueDanListBean.getCreator().getNickName());
			author.setText(formatStr);
			title.setText(peopleYueDanListBean.getTitle());
			str = getActivity().getString(R.string.detail_updatetime);
			formatStr = String.format(str, peopleYueDanListBean.getUpdateTime());
			updateTimes.setText(formatStr);
			str = getActivity().getString(R.string.detail_playtimes);
			formatStr = String.format(str, peopleYueDanListBean.getTotalViews());
			playTimes.setText(formatStr);
			descript.setText(peopleYueDanListBean.getDescription());
			str = getActivity().getString(R.string.detail_collect);
			formatStr = String.format(str, peopleYueDanListBean.getVideoCount());
			collect.setText(formatStr);
		}
	}
	public void update(RelatedVideos relatedVideos){
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
