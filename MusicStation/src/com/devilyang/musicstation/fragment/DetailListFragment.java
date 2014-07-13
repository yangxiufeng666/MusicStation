package com.devilyang.musicstation.fragment;

import java.util.ArrayList;

import com.devilyang.musicstation.DetailActivity;
import com.devilyang.musicstation.R;
import com.devilyang.musicstation.VideoViewPlayerActivity;
import com.devilyang.musicstation.adapter.DetailListAdapter;
import com.devilyang.musicstation.bean.PeopleYueDanListBean;
import com.devilyang.musicstation.bean.PlayInfoBean;
import com.devilyang.musicstation.bean.RelatedVideosListBean;
import com.devilyang.musicstation.interfaces.DetailDataChangeInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DetailListFragment extends Fragment{

	private Object object;
	private boolean isRelative;
	private PeopleYueDanListBean peopleYueDanListBean;
	private RelatedVideosListBean relatedVideosListBean;
	private ListView listView;
	private DetailListAdapter adapter;
	private ArrayList<Object> objects = new ArrayList<Object>();
	private DetailDataChangeInterface dataChangeInterface;
	
	public static Fragment getInstance(Object object,boolean isRelative){
		DetailListFragment fragment = new DetailListFragment();
		fragment.object = object;
		fragment.isRelative = isRelative;
		if(isRelative){
			fragment.relatedVideosListBean = (RelatedVideosListBean)object;
		}else{
			fragment.peopleYueDanListBean = (PeopleYueDanListBean)object;
		}
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.detail_list, null);
		findView(v);
		return v;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		dataChangeInterface = (DetailDataChangeInterface)activity;
	}
	private void findView(View v){
		listView = (ListView)v.findViewById(R.id.detail_list);
		if (isRelative) {
			objects.addAll(relatedVideosListBean.getRelatedVideosList());
			
		}else{
			objects.addAll(peopleYueDanListBean.getVideosList());
		}
		adapter = new DetailListAdapter(objects, getActivity(), isRelative);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(isRelative){
//					dataChangeInterface.detailDataChange(relatedVideosListBean
//							.getRelatedVideosList().get(position));
					PlayInfoBean playInfoBean = new PlayInfoBean();
					playInfoBean.setTitle(relatedVideosListBean
							.getRelatedVideosList().get(position).getTitle());
					playInfoBean.setUrl(relatedVideosListBean
							.getRelatedVideosList().get(position).getUrl());
					playInfoBean.setHurl(relatedVideosListBean
							.getRelatedVideosList().get(position).getHdUrl());
					playInfoBean.setUhurl(relatedVideosListBean
							.getRelatedVideosList().get(position).getUhdUrl());
					Intent intent = new Intent();
					intent.setClass(getActivity(), VideoViewPlayerActivity.class);
					intent.putExtra("playInfo", playInfoBean);
					getActivity().startActivity(intent);
				} else {
					//do nothing
					PlayInfoBean playInfoBean = new PlayInfoBean();
					playInfoBean.setTitle(peopleYueDanListBean.getVideosList()
							.get(position).getTitle());
					playInfoBean.setUrl(peopleYueDanListBean.getVideosList()
							.get(position).getUrl());
					playInfoBean.setHurl(peopleYueDanListBean.getVideosList()
							.get(position).getHdUrl());
					playInfoBean.setUhurl(peopleYueDanListBean.getVideosList()
							.get(position).getUhdUrl());
					Intent intent = new Intent();
					intent.setClass(getActivity(), VideoViewPlayerActivity.class);
					intent.putExtra("playInfo", playInfoBean);
					getActivity().startActivity(intent);
				}
			}
		});
	}
}
