package com.devilyang.musicstation.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.devilyang.musicstation.DetailActivity;
import com.devilyang.musicstation.R;
import com.devilyang.musicstation.R.layout;
import com.devilyang.musicstation.adapter.YueDanMainListAdapter;
import com.devilyang.musicstation.bean.YueDanListBean;
import com.devilyang.musicstation.bean.YueDanListBean.PlayLists;
import com.devilyang.musicstation.net.LogUtil;
import com.devilyang.musicstation.pullrefresh.PullToRefreshBase;
import com.devilyang.musicstation.pullrefresh.PullToRefreshBase.Mode;
import com.devilyang.musicstation.pullrefresh.PullToRefreshListView;
import com.devilyang.musicstation.util.URLProviderUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class YueDanFragment extends BaseFragment{
	private static final String TAG  = "YueDanFragment";
	private static final int SIZE = 15;
	private int offset = 0;
	private PullToRefreshListView mPullToRefreshListView;
	private ProgressBar mProgressBar;
	private View rootView;
	private TextView failTips;
	private YueDanListBean bean;
	private ArrayList<YueDanListBean.PlayLists> playLists = new ArrayList<YueDanListBean.PlayLists>();
	private YueDanMainListAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(rootView==null){
			rootView = inflater.inflate(R.layout.pull_to_refresh_listview, container, false);
			findView();
			startLoadData(offset,SIZE);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		} 
		return rootView;
	}
	private void findView(){
		failTips = (TextView)rootView.findViewById(R.id.failed_tips);
		mPullToRefreshListView = (PullToRefreshListView)rootView.findViewById(R.id.pull_refresh_list);
		mProgressBar = (ProgressBar)rootView.findViewById(R.id.mv_root_progress);
		
		mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				
			}
			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(),
						System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy()
						.setLastUpdatedLabel(label);
				startLoadData(offset,SIZE);
			}
		});
		mPullToRefreshListView.setMode(Mode.DISABLED);
		mPullToRefreshListView.setScrollingWhileRefreshingEnabled(false);
		ListView actualList = mPullToRefreshListView.getRefreshableView();
		adapter = new YueDanMainListAdapter(playLists, getActivity());
		actualList.setAdapter(adapter);
		actualList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("id", playLists.get(position-1).getId());
				intent.setClass(getActivity(), DetailActivity.class);
				getActivity().startActivity(intent);
			}
		});
		failTips.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				startLoadData(offset,SIZE);
				failTips.setVisibility(View.GONE);
				mProgressBar.setVisibility(View.VISIBLE);
				return true;
			}
		});
	}
	private void startLoadData(int offset,int size){
		if(bean!=null){
			if(offset>=bean.getTotalCount()-1){
				return;
			}
		}
		executeRequest(
				new JsonObjectRequest(URLProviderUtil.getMainPageYueDanUrl(offset,
						size), null, responseListener(), errorSponseListener()),
				"YuedanFragment");
	}
	private void updateUI(){
		mPullToRefreshListView.setMode(Mode.PULL_FROM_END);
		offset +=SIZE; 
		adapter.notifyDataSetChanged();
	}
	@Override
	public Listener<JSONObject> responseListener() {
		return new Response.Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject response) {
				try {
					LogUtil.d(TAG, "responseListener response = "+response);
					bean = new YueDanListBean(response);
					playLists.addAll(bean.getPlayListsList());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				mProgressBar.setVisibility(View.GONE);
				mPullToRefreshListView.setMode(Mode.PULL_FROM_END);
				mPullToRefreshListView.onRefreshComplete();
				updateUI();
			}
			
		};
	}
	@Override
	public ErrorListener errorSponseListener() {
		return new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				LogUtil.d(TAG, "errorSponseListener error = "+error.getLocalizedMessage());
				mPullToRefreshListView.onRefreshComplete();
				if(bean==null){
					mProgressBar.setVisibility(View.GONE);
					failTips.setVisibility(View.VISIBLE);
				}
			}
		};
	}
}
