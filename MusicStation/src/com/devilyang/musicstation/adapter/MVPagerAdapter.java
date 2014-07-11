package com.devilyang.musicstation.adapter;

import java.util.ArrayList;

import com.devilyang.musicstation.bean.AreaBean;
import com.devilyang.musicstation.fragment.MVPageFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MVPagerAdapter extends FragmentStatePagerAdapter{

	private ArrayList<AreaBean> areaList;
	private ArrayList<Fragment> fragments;
	
	public MVPagerAdapter(FragmentManager fm, ArrayList<AreaBean> areaList,
			ArrayList<Fragment> fragments) {
		super(fm);
		this.areaList = areaList;
		this.fragments = fragments;
	}
	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);//MVPageFragment.newInstance(areaList.get(position).getCode());
	}
	@Override
	public CharSequence getPageTitle(int position) {
		return areaList.get(position).getName();
	}
	@Override
	public int getCount() {
		return areaList.size();
	}

}
