package com.devilyang.musicstation.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.devilyang.musicstation.R;
import com.devilyang.musicstation.adapter.VChartPagerAdapter;
import com.devilyang.musicstation.bean.AreaBean;
import com.devilyang.musicstation.indicator.TabPageIndicator;
import com.devilyang.musicstation.interfaces.GetVChartInterface;
import com.devilyang.musicstation.net.LogUtil;
import com.devilyang.musicstation.util.URLProviderUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class VChartFragment extends BaseFragment implements GetVChartInterface{
	private static final String TAG = "VChartFragment";
	private View rootView;
	private TabPageIndicator indicator;
	private ViewPager viewPager;
	private ProgressBar progressBar;
	private TextView failTips;
	private ArrayList<AreaBean> areaBeans = new ArrayList<AreaBean>();
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	private VChartPagerAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(rootView==null){
			rootView=inflater.inflate(R.layout.vchart_fragment, null, false);
			findView(rootView);
			startLoadData();
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if(parent!=null){
			parent.removeView(rootView);
		}
		return rootView;
	}
	private void findView(View view){
		failTips = (TextView)view.findViewById(R.id.failed_tips);
		indicator = (TabPageIndicator)view.findViewById(R.id.vhart_indicator);
		viewPager = (ViewPager)view.findViewById(R.id.vchart_pager);
		progressBar = (ProgressBar)view.findViewById(R.id.vchart_progress);
		adapter = new VChartPagerAdapter(getChildFragmentManager(), fragments, areaBeans);
		viewPager.setAdapter(adapter);
		indicator.setViewPager(viewPager);
		failTips.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				startLoadData();
				failTips.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
				return true;
			}
		});
	}
	private void startLoadData(){
		executeRequest(new JsonArrayRequest(URLProviderUtil.getVChartAreasUrl(), responseListener(), errorSponseListener()), "getVChartArea");
	}
	private void updateUi(){
		adapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}
	@Override
	public Listener<JSONArray> responseListener() {
		return new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				LogUtil.d(TAG, "GET AREA SUCCESS RESPONSE = "+response.toString());
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
					areaBeans.clear();
					areaBeans.addAll(tempBeans);
					for (int i = 0; i < areaBeans.size(); i++) {
						VChartPagerFragment fragment = VChartPagerFragment.getInstance(areaBeans.get(i).getCode());
						fragments.add(fragment);
					}
					updateUi();
				}
			}
		};
	}

	@Override
	public ErrorListener errorSponseListener() {
		return new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				LogUtil.e(TAG, "GET AREA fail RESPONSE = "+error.getLocalizedMessage());
				progressBar.setVisibility(View.GONE);
				failTips.setVisibility(View.VISIBLE);
			}
		};
	}

	@Override
	public void getVChartResponseOK(String type) {
		
	}

	@Override
	public void getVChartResponseFail(String type) {
		
	}

}
