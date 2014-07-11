package com.devilyang.musicstation.adapter;

import java.util.ArrayList;

import com.devilyang.musicstation.bean.AreaBean;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class VChartPagerAdapter extends FragmentStatePagerAdapter{

	private ArrayList<Fragment> fragments;
	private ArrayList<AreaBean> areaBeans;
	
	public VChartPagerAdapter(FragmentManager fm,
			ArrayList<Fragment> fragments, ArrayList<AreaBean> areaBeans) {
		super(fm);
		this.fragments = fragments;
		this.areaBeans = areaBeans;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}
	@Override
	public CharSequence getPageTitle(int position) {
		return areaBeans.get(position).getName();
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
