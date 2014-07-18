package com.devilyang.musicstation.fragment;


import com.android.volley.toolbox.NetworkImageView;
import com.devilyang.musicstation.DetailActivity;
import com.devilyang.musicstation.R;
import com.devilyang.musicstation.bean.FirstPageBean;
import com.devilyang.musicstation.net.LogUtil;
import com.devilyang.musicstation.net.RequestManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FistPagerItemFragment extends Fragment{
	private FirstPageBean bean;
	private NetworkImageView imageView;
	public static Fragment newInstance(FirstPageBean bean) {
		FistPagerItemFragment fragment = new FistPagerItemFragment();
		fragment.bean = bean;
		return fragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fist_page_item, null);
		imageView = (NetworkImageView)v.findViewById(R.id.fist_page_img);
		imageView.setDefaultImageResId(R.drawable.default_big_poster);
		imageView.setImageUrl(bean.getPosterPic(), RequestManager.getImageLoader());
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LogUtil.d("FistPagerItemFragment", "onClick.....");
				if("ACTIVITY".equalsIgnoreCase(bean.getType())){//活动相关
					Toast.makeText(getActivity(), R.string.not_open, Toast.LENGTH_SHORT).show();
				}else if("VIDEO".equalsIgnoreCase(bean.getType())){//首播，点击进去显示MV描述，相关MV
					Intent intent = new Intent();
					intent.putExtra("id", bean.getId());
					intent.putExtra("isRelativeVideo", true);
					intent.setClass(getActivity(), DetailActivity.class);
					getActivity().startActivity(intent);
				}else if("WEEK_MAIN_STAR".equalsIgnoreCase(bean.getType())){//(悦单)点击进去跟显示悦单详情一样
					Intent intent = new Intent();
					intent.putExtra("id", bean.getId());
					intent.putExtra("isRelativeVideo", false);
					intent.setClass(getActivity(), DetailActivity.class);
					getActivity().startActivity(intent);
				}else if("PLAYLIST".equalsIgnoreCase(bean.getType())){//(悦单)点击进去跟显示悦单详情一样
					Intent intent = new Intent();
					intent.putExtra("id", bean.getId());
					intent.putExtra("isRelativeVideo", false);
					intent.setClass(getActivity(), DetailActivity.class);
					getActivity().startActivity(intent);
				}else if("AD".equalsIgnoreCase(bean.getType())){
					Toast.makeText(getActivity(), R.string.not_open, Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getActivity(), R.string.not_open, Toast.LENGTH_SHORT).show();
				}
			}
		});
		return v;
	}
	
}
