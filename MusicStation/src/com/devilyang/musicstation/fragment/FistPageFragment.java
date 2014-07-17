package com.devilyang.musicstation.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.devilyang.musicstation.R;
import com.devilyang.musicstation.adapter.FirstPagerAdapter;
import com.devilyang.musicstation.bean.FirstPageBean;
import com.devilyang.musicstation.cache.CacheManager;
import com.devilyang.musicstation.indicator.CirclePageIndicator;
import com.devilyang.musicstation.net.LogUtil;
import com.devilyang.musicstation.util.URLProviderUtil;
import com.devilyang.musicstation.util.Util;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FistPageFragment extends BaseFragment {
	private View rootView;
	private ViewPager viewPager;
	private CirclePageIndicator cIndicator;
	private ImageView catImg;
	private TextView titleTxt;
	private TextView authorTxt;
	private ImageView detailImg;
	private TextView failTips;
	private RelativeLayout bottomLayout;
	private ProgressBar progressBar;
	private ArrayList<FirstPageBean> firstBeans = new ArrayList<FirstPageBean>();;
	private FirstPagerAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtil.d("FistPageFragment", "FistPageFragment onCreateView");
		if(rootView==null){
			rootView = inflater.inflate(R.layout.fist_page_fragment, null, false);
			findView(rootView);
			startLoad();
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if(parent!=null){
			parent.removeView(rootView);
		}
		return rootView;
	}
	@Override
	public void onResume() {
		super.onResume();
		LogUtil.d("FistPageFragment", "FistPageFragment onResume()");
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LogUtil.d("FistPageFragment", "FistPageFragment onPause()");
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtil.d("FistPageFragment", "FistPageFragment onDestroyView()");
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		LogUtil.d("MusicStation", "FistPageFragment onViewCreated()");
	}
	private void findView(View v){
		failTips = (TextView)v.findViewById(R.id.failed_tips);
		viewPager = (ViewPager)v.findViewById(R.id.first_pager);
		cIndicator = (CirclePageIndicator)v.findViewById(R.id.circle_indicator);
		catImg = (ImageView)v.findViewById(R.id.home_page_img);
		titleTxt = (TextView)v.findViewById(R.id.title);
		authorTxt = (TextView)v.findViewById(R.id.author);
		detailImg = (ImageView)v.findViewById(R.id.detail_img);
		progressBar = (ProgressBar)v.findViewById(R.id.progress);
		bottomLayout = (RelativeLayout)v.findViewById(R.id.bottom_layout);
		adapter = new FirstPagerAdapter(firstBeans,getChildFragmentManager(),getActivity());
		viewPager.setAdapter(adapter);
		cIndicator.setViewPager(viewPager);
		cIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				updateBottom(position);
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		failTips.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				startLoad();
				failTips.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
				return true;
			}
		});
		updateBottom(0);
	}
	private void updateUi(){
		adapter.notifyDataSetChanged();
		cIndicator.notifyDataSetChanged();
		updateBottom(0);
	}
	private void updateBottom(int index) {
		if(firstBeans.size()==0){
			return;
		}
		if("ACTIVITY".equalsIgnoreCase(firstBeans.get(index).getType())){//活动相关
			catImg.setImageResource(R.drawable.home_page_activity);
		}else if("VIDEO".equalsIgnoreCase(firstBeans.get(index).getType())){//首播，点击进去显示MV描述，相关MV
			catImg.setImageResource(R.drawable.home_page_video);
		}else if("WEEK_MAIN_STAR".equalsIgnoreCase(firstBeans.get(index).getType())){//(悦单)点击进去跟显示悦单详情一样
			catImg.setImageResource(R.drawable.home_page_star);
		}else if("PLAYLIST".equalsIgnoreCase(firstBeans.get(index).getType())){//(悦单)点击进去跟显示悦单详情一样
			catImg.setImageResource(R.drawable.home_page_playlist);
		}else{
			catImg.setImageResource(R.drawable.home_page_video);
		}
		titleTxt.setText(firstBeans.get(index).getTitle());
		authorTxt.setText(firstBeans.get(index).getDescription());
	}
	private void startLoad() {
		JSONArray jsonArray = CacheManager.getInstance().getACache().getAsJSONArray(URLProviderUtil.getMainPageUrl());
		if(jsonArray==null){
			executeRequest(new JsonArrayRequest(URLProviderUtil.getMainPageUrl(),
					responseListener(), errorSponseListener()), "tag");
		}else{
			parseResponse(jsonArray);
		}
		
	}
	@Override
	public Response.Listener<JSONArray> responseListener() {
		return new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				LogUtil.v("FirstPage", "responseListener..."+response.toString());
				CacheManager.getInstance().getACache().put(URLProviderUtil.getMainPageUrl(), response,Util.SAVE_TIME);
				parseResponse(response);
			}
		};
	}
	@Override
	public Response.ErrorListener errorSponseListener() {
		return new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				LogUtil.v("FirstPage", "errorSponseListener..."+error.getLocalizedMessage());
				failTips.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
			}
		};
	}
	private void parseResponse(JSONArray response) {
		boolean isDataChange = true;
		ArrayList<FirstPageBean> tempBeans = new ArrayList<FirstPageBean>();
		for (int i = 0; i < response.length(); i++) {
			try {
				FirstPageBean bean = new FirstPageBean(response.getJSONObject(i));
				if(firstBeans.size()!=response.length()){
					firstBeans.clear();
				}
				if(firstBeans.size()!=0){
					if (firstBeans.get(i).equals(bean)) {
						isDataChange = false;
					}else{
						isDataChange = true;
					}
				}
				tempBeans.add(bean);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		viewPager.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
		bottomLayout.setVisibility(View.VISIBLE);
		if(isDataChange){
			firstBeans.clear();
			firstBeans.addAll(tempBeans);
			updateUi();
		}
	}
}
