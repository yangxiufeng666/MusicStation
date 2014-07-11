package com.devilyang.musicstation.adapter;

import java.util.ArrayList;

import com.android.volley.toolbox.NetworkImageView;
import com.devilyang.musicstation.R;
import com.devilyang.musicstation.bean.FirstPageBean;
import com.devilyang.musicstation.fragment.FistPagerItemFragment;
import com.devilyang.musicstation.net.RequestManager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FirstPagerAdapter extends FragmentStatePagerAdapter{

	private ArrayList<FirstPageBean> beans;
	private Context context;
	
	public FirstPagerAdapter(ArrayList<FirstPageBean> beans,FragmentManager fm,Context context) {
		super(fm);
		this.beans = beans;
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		return FistPagerItemFragment.newInstance(beans.get(position));
	}
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return beans.size();
	}
	class HolderView{
		NetworkImageView imageView;
	}
}