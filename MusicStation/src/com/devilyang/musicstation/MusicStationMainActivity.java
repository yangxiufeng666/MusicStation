package com.devilyang.musicstation;

import com.devilyang.musicstation.fragment.FistPageFragment;
import com.devilyang.musicstation.fragment.MVBaseFragment;
import com.devilyang.musicstation.fragment.MVPageFragment;
import com.devilyang.musicstation.fragment.VChartFragment;
import com.devilyang.musicstation.fragment.YueDanFragment;
import com.devilyang.musicstation.util.URLProviderUtil;
import com.devilyang.musicstation.util.Util;
import com.devilyang.musicstation.widget.FragmentTabHost;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MusicStationMainActivity extends FragmentActivity {
	private FragmentTabHost tabHost;
	private TextView titleTxt;
	private ImageView searchImg;
	private Class[] fragmentArray = { FistPageFragment.class,
			MVBaseFragment.class, VChartFragment.class, YueDanFragment.class };
	private int[] imageindex = { R.drawable.fist_page_selector,
			R.drawable.mv_page_selector, R.drawable.vchart_page_selector,
			R.drawable.yuedan_page_selector };
	private String[] tabTag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_station_main);
		initData();
	}
	private void initData(){
		tabTag = getResources().getStringArray(R.array.tab_indicator_name);
		tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
		titleTxt = (TextView)findViewById(R.id.title_name);
		searchImg = (ImageView)findViewById(R.id.title_search_img);
		tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);	
		for (int i = 0; i < fragmentArray.length; i++) {
			//给tab也添加按钮
			TabSpec tabSpec = tabHost.newTabSpec(tabTag[i]).setIndicator(getTabItemView(i));
			//tab对应的显示视图
			tabHost.addTab(tabSpec, fragmentArray[i], null);
			//tab添加背景
			tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
		}
		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				Log.e("tabid", "tabid = "+tabId);
				Log.e("tabid", "tabid1 = "+Util.getPhoneModel());
				Log.e("tabid", "tabid2 = "+Util.getSystemversion());
				Log.e("tabid", "tabid3 = "+URLProviderUtil.getMainPageUrl());
				titleTxt.setText(tabId);
			}
		});
	}
	/**
	 * 获取导航图标
	 */
	private View getTabItemView(int index){
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(imageindex[index]);
		return view;
	}
}
