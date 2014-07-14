package com.devilyang.musicstation;


import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.devilyang.musicstation.R;
import com.devilyang.musicstation.bean.PeopleYueDanListBean;
import com.devilyang.musicstation.bean.PlayInfoBean;
import com.devilyang.musicstation.bean.RelatedVideosListBean;
import com.devilyang.musicstation.bean.RelatedVideosListBean.RelatedVideos;
import com.devilyang.musicstation.fragment.DetailContentFragment;
import com.devilyang.musicstation.fragment.DetailListFragment;
import com.devilyang.musicstation.fragment.UpdateDetailFragment;
import com.devilyang.musicstation.interfaces.DetailDataChangeInterface;
import com.devilyang.musicstation.interfaces.ResponseListener;
import com.devilyang.musicstation.net.LogUtil;
import com.devilyang.musicstation.net.RequestManager;
import com.devilyang.musicstation.util.URLProviderUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


public class DetailActivity extends FragmentActivity implements ResponseListener,DetailDataChangeInterface{

	private static final String TAG = "PeopleYueDanActivity";	
	private TextView leftBtnImg;
	private TextView rightBtnImg;
	private ProgressBar progressBar;
	private NetworkImageView detailPoster;
	private LinearLayout layout;
	private TextView failTips;
	private TextView titleName;
	
	private PeopleYueDanListBean peopleYueDanListBean;
	private RelatedVideosListBean relatedVideosListBean;
	
	private int id;
	private boolean isRelativeVideo =false;
	
	private PlayInfoBean playInfoBean;
	
	private boolean isDestory;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.detail_fragment);
		Intent intent = getIntent();
		isRelativeVideo = intent.getBooleanExtra("isRelativeVideo", false);
		id = intent.getIntExtra("id", 0);
		findView();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		isDestory = true;
		RequestManager.cancelAll("getRelateVideo");
		RequestManager.cancelAll("getPeopleYueDanList");
	}
	private void findView(){
		titleName = (TextView)findViewById(R.id.title_name);
		titleName.setText(R.string.detail_title);
		failTips = (TextView)findViewById(R.id.failed_tips);
		layout = (LinearLayout)findViewById(R.id.main_content);
		progressBar = (ProgressBar)findViewById(R.id.progress);
		detailPoster = (NetworkImageView)findViewById(R.id.detail_poster);
		detailPoster.setDefaultImageResId(R.drawable.default_splash_bg);
		leftBtnImg = (TextView)findViewById(R.id.detail_left_btn);
		rightBtnImg = (TextView)findViewById(R.id.detail_right_btn);
		startLoadData();
		leftBtnImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rightBtnImg.setBackgroundResource(R.drawable.tab_n_bg);
				leftBtnImg.setBackgroundResource(R.drawable.tab_pressed_bg);
				if (isRelativeVideo) {
					showContentFragment(relatedVideosListBean);
				}else{
					showContentFragment(peopleYueDanListBean);
				}
			}
		});
		rightBtnImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rightBtnImg.setBackgroundResource(R.drawable.tab_pressed_bg2);
				leftBtnImg.setBackgroundResource(R.drawable.tab_n_bg2);
				if (isRelativeVideo) {
					showListFragment(relatedVideosListBean);
				}else{
					showListFragment(peopleYueDanListBean);
				}
			}
		});
		detailPoster.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DetailActivity.this, VideoViewPlayerActivity.class);
				playInfoBean = new PlayInfoBean();
				if(isRelativeVideo){
					playInfoBean.setTitle(relatedVideosListBean.getTitle());
					playInfoBean.setUrl(relatedVideosListBean.getUrl());
					playInfoBean.setHurl(relatedVideosListBean.getHdUrl());
					playInfoBean.setUhurl(relatedVideosListBean.getUhdUrl());
				}else{
					playInfoBean.setTitle(peopleYueDanListBean.getVideosList().get(0).getTitle());
					playInfoBean.setUrl(peopleYueDanListBean.getVideosList().get(0).getUrl());
					playInfoBean.setHurl(peopleYueDanListBean.getVideosList().get(0).getHdUrl());
					playInfoBean.setUhurl(peopleYueDanListBean.getVideosList().get(0).getUhdUrl());
				}
				intent.putExtra("playInfo", playInfoBean);
				startActivity(intent);
			}
		});
		failTips.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				failTips.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
				startLoadData();
			}
		});
	}
	private void startLoadData() {
		if (isRelativeVideo) {
			executeRequest(
					new JsonObjectRequest(Method.GET,
							URLProviderUtil.getRelativeVideoListUrl(id), null,
							responseListener(), errorSponseListener()),
					"getRelativeVideoList");
		} else {
			executeRequest(
					new JsonObjectRequest(Method.GET,
							URLProviderUtil.getPeopleYueDanList(id), null,
							responseListener(), errorSponseListener()),
					"getPeopleYueDanList");
		}
	}
	private void updateUI(Object object){
		if(isDestory){
			return;
		}
		getSupportFragmentManager()
		.beginTransaction()
		.add(R.id.content, DetailContentFragment.getInstance(object, isRelativeVideo),
				"DetailContentFragment").commit();
	}
	private void showContentFragment(Object object){
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content, DetailContentFragment.getInstance(object, isRelativeVideo),
				"DetailContentFragment").commit();
	}
	private void showListFragment(Object object){
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content, DetailListFragment.getInstance(object, isRelativeVideo),
				"DetailListFragment").commit();
	}
	public void executeRequest(Request<?> request,Object tag) {
		RequestManager.addRequest(request, tag);
	}
	@Override
	public Listener<JSONObject> responseListener() {
		return new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				LogUtil.d(TAG, "responseListener response"+response);
				if(isRelativeVideo){
					try {
						relatedVideosListBean = new RelatedVideosListBean(response);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					try {
						peopleYueDanListBean = new PeopleYueDanListBean(response);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				if (isRelativeVideo) {
					updateUI(relatedVideosListBean);
					rightBtnImg.setText(R.string.mv_relate);
					leftBtnImg.setText(R.string.mv_descript);
					detailPoster.setImageUrl(relatedVideosListBean.getPosterPic(), RequestManager.getImageLoader());
					
				}else{
					updateUI(peopleYueDanListBean);
					rightBtnImg.setText(R.string.yuedan_list);
					leftBtnImg.setText(R.string.yuedan_descript);
					detailPoster.setImageUrl(peopleYueDanListBean.getPlayListBigPic(), RequestManager.getImageLoader());
				}
				progressBar.setVisibility(View.GONE);
				layout.setVisibility(View.VISIBLE);
			}
		};
	}

	@Override
	public ErrorListener errorSponseListener() {
		return new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				LogUtil.e(TAG, "errorSponseListener error = "+error.getLocalizedMessage());
				failTips.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
			}
		};
	}
	@Override
	public void detailDataChange(RelatedVideos relatedVideos) {
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content, UpdateDetailFragment.getInstance(relatedVideos),
				"UpdateDetailFragment").commit();
	}

}
