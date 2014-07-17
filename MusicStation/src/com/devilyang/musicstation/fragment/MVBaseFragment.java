package com.devilyang.musicstation.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.OnTouchListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.devilyang.musicstation.R;
import com.devilyang.musicstation.adapter.MVPagerAdapter;
import com.devilyang.musicstation.bean.FirstPageBean;
import com.devilyang.musicstation.bean.AreaBean;
import com.devilyang.musicstation.cache.CacheManager;
import com.devilyang.musicstation.indicator.TabPageIndicator;
import com.devilyang.musicstation.net.LogUtil;
import com.devilyang.musicstation.util.URLProviderUtil;
import com.devilyang.musicstation.util.Util;

public class MVBaseFragment extends BaseFragment{
	private View rootView;
	private TabPageIndicator tabiIndicator;
	private TextView failTips;
	private ViewPager viewPager;
	private ProgressBar progressBar;
	private MVPagerAdapter mvPagerAdapter;
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	
	private ArrayList<AreaBean> areaBeans=new ArrayList<AreaBean>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtil.d("MVBaseFragment", "MVBaseFragment onCreateView()");
		if(rootView==null){
			rootView = inflater.inflate(R.layout.mv_root_fragment, null);
			initData(rootView);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if(parent!=null){
			parent.removeView(rootView);
		}
		return rootView;
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
	@Override
	public void onResume() {
		super.onResume();
		LogUtil.d("MVBaseFragment", "MVBaseFragment onResume()");
	}
	@Override
	public void onPause() {
		super.onPause();
		LogUtil.d("MVBaseFragment", "MVBaseFragment onPause()");
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogUtil.d("MVBaseFragment", "MVBaseFragment onDestroyView()");
	}
	private void initData(View view) {
		failTips = (TextView)view.findViewById(R.id.failed_tips);
		tabiIndicator = (TabPageIndicator)view.findViewById(R.id.tab_indicator);
		viewPager = (ViewPager)view.findViewById(R.id.mv_pager);
		progressBar=(ProgressBar)view.findViewById(R.id.mv_root_progress);
		mvPagerAdapter = new MVPagerAdapter(getChildFragmentManager(), areaBeans,fragments);
		viewPager.setAdapter(mvPagerAdapter);
		tabiIndicator.setViewPager(viewPager);
		failTips.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				startLoadArea();
				failTips.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
				return true;
			}
		});
		startLoadArea();
	}
	private void startLoadArea(){
		String url = URLProviderUtil.getMVareaUrl();
		JSONArray jsonArray = CacheManager.getInstance().getACache().getAsJSONArray(url);
		if(jsonArray==null){
			executeRequest(new JsonArrayRequest(url,
					responseListener(), errorSponseListener()),
					MVBaseFragment.class);
		} else {
			parseResponse(jsonArray);
		}
		
	}
	private void updateUi(){
		LogUtil.d("MVBaseFragment", "MVBaseFragment updateUi()");
		viewPager.setVisibility(View.VISIBLE);
		tabiIndicator.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
		mvPagerAdapter.notifyDataSetChanged();
		tabiIndicator.notifyDataSetChanged();
	}
	@Override
	public Listener<JSONArray> responseListener() {
		return new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				LogUtil.d("MVBaseFragment", "response = "+response.toString());
				CacheManager.getInstance().getACache().put(URLProviderUtil.getMVareaUrl(), response,Util.SAVE_TIME);
				parseResponse(response);
			}
		};
	}
	@Override
	public ErrorListener errorSponseListener() {
		return new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				LogUtil.v("MVBaseFragment", "errorSponseListener..."+error.getLocalizedMessage());
				progressBar.setVisibility(View.GONE);
				failTips.setVisibility(View.VISIBLE);
			}
		};
	}
	private void parseResponse(JSONArray response) {
		boolean isDataChange = true;
		ArrayList<AreaBean> tempBeans = new ArrayList<AreaBean>();
		for (int i = 0; i < response.length(); i++) {
			try {
				AreaBean areaBean = new AreaBean(response.getJSONObject(i));
				if(areaBeans.size()!=response.length()){
					areaBeans.clear();
				}
				if(areaBeans.size()!=0){
					if (areaBeans.get(i).equals(areaBean)) {
						isDataChange = false;
					}else{
						isDataChange = true;
					}
				}
				tempBeans.add(areaBean);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		progressBar.setVisibility(View.GONE);
		if(isDataChange){
			areaBeans.addAll(tempBeans);
			for (int i = 0; i < areaBeans.size(); i++) {
				MVPageFragment fragment = MVPageFragment.newInstance(areaBeans.get(i).getCode());
				fragments.add(fragment);
			}
			updateUi();
		}
	}
}
