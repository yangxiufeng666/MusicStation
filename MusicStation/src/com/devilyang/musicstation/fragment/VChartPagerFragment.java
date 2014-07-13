package com.devilyang.musicstation.fragment;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.devilyang.musicstation.DetailActivity;
import com.devilyang.musicstation.R;
import com.devilyang.musicstation.adapter.VChartListAdapter;
import com.devilyang.musicstation.bean.VChartListBean;
import com.devilyang.musicstation.bean.VChartPeriodBean;
import com.devilyang.musicstation.bean.VChartPeriodBean.Periods;
import com.devilyang.musicstation.net.LogUtil;
import com.devilyang.musicstation.util.URLProviderUtil;

public class VChartPagerFragment extends BaseFragment {
	
	private static final String TAG = "VChartPagerFragment";
	private String areaCode;
	private View rootView;
	private TextView periodTxt;
	private ListView vchartList;
	private ProgressBar progressBar;
	private int periodIndex = 0;
	private ImageView leftPeriod;
	private ImageView rightPeriod;
	private PopupWindow popupWindow;
	
	private VChartPeriodBean periodBean;
	private ArrayList<VChartPeriodBean.Periods> periodsList = new ArrayList<VChartPeriodBean.Periods>();
	private ArrayList<VChartListBean.Videos> videos = new ArrayList<VChartListBean.Videos>();
	private VChartListBean vChartListBean;
	
	private VChartListAdapter listAdapter;
	
	public static VChartPagerFragment getInstance(String areaCode) {
		VChartPagerFragment fragment = new VChartPagerFragment();
		fragment.areaCode = areaCode;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.vhcart_pager_item, null);
			findView(rootView);
			startLoadData();
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}
	private void findView(View v){
		vchartList = (ListView)v.findViewById(R.id.vchart_list);
		periodTxt = (TextView)v.findViewById(R.id.vchart_period);
		progressBar = (ProgressBar)v.findViewById(R.id.vchart_list_progress);
		listAdapter = new VChartListAdapter(getActivity(),videos);
		vchartList.setAdapter(listAdapter);
		leftPeriod = (ImageView)v.findViewById(R.id.vchart_left_period);
		rightPeriod = (ImageView)v.findViewById(R.id.vchart_right_period);
		leftPeriod.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(periodsList.size()==0){
					return;
				}
				if(periodIndex > 0){
					periodIndex -=1;
					updatePeriod(periodsList.get(periodIndex));
					progressBar.setVisibility(View.VISIBLE);
					startLoadListData(periodsList.get(periodIndex).getDateCode());
				}
			}
		});
		rightPeriod.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(periodsList.size()==0){
					return;
				}
				if(periodIndex < periodsList.size()){
					periodIndex +=1;
					updatePeriod(periodsList.get(periodIndex));
					progressBar.setVisibility(View.VISIBLE);
					startLoadListData(periodsList.get(periodIndex).getDateCode());
				}
			}
		});
		periodTxt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				periodTxt.getTop();
				int y = periodTxt.getBottom() * 3 / 2;
				int x = getActivity().getWindowManager().getDefaultDisplay().getWidth() / 4;
				showPopupWindow(x,y);
			}
		});
		vchartList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("id", videos.get(position).getId());
				intent.putExtra("isRelativeVideo", true);
				intent.setClass(getActivity(), DetailActivity.class);
				getActivity().startActivity(intent);
			}
		});
	}
	private void updateUI(){
		LogUtil.d(TAG, " updateUI = "+vChartListBean.getVideosList().size());
		listAdapter.notifyDataSetChanged();
		progressBar.setVisibility(View.GONE);
	}
	private void showPopupWindow(int x, int y) {
		String[] title = new String[periodsList.size()];
		int i=0;
		for (VChartPeriodBean.Periods periods : periodsList) {
			String str = getString(R.string.vchart_period);
			String format = String.format(str, periods.getYear(), periods.getNo(),
					periods.getBeginDateText(), periods.getEndDateText());
			title[i] = format;
			i++;
		}
		LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
				R.layout.popupwin_layout, null);
		ListView listView = (ListView) layout.findViewById(R.id.popup_list);
		listView.setAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.popup_list_item, R.id.popup_list_item, title));

		popupWindow = new PopupWindow(getActivity());
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		popupWindow
				.setWidth(dm.widthPixels*2/3);
		popupWindow.setHeight(dm.heightPixels/2);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setContentView(layout);
		popupWindow.showAsDropDown(periodTxt);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				popupWindow.dismiss();
				popupWindow = null;
				startLoadListData(periodsList.get(position).getDateCode());
				progressBar.setVisibility(View.VISIBLE);
				updatePeriod(periodsList.get(position));
			}
		});
	}

	/**
	 * 下载时间
	 */
	private void startLoadData() {
		executeRequest(
				new JsonObjectRequest(Method.GET,
						URLProviderUtil.getVChartPeriodUrl(areaCode), null,
						responseListener(), errorSponseListener()), "getVChartPeriod");
	}
	/**
	 * 根据周期下载列表
	 * @param dateCode
	 */
	private void startLoadListData(int dateCode){
		executeRequest(new JsonObjectRequest(Method.GET, URLProviderUtil.getVChartListUrl(areaCode, dateCode), null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				LogUtil.d(TAG, "getVChartList success response = "+response.toString());
				videos.clear();
				try {
					vChartListBean = new VChartListBean(response);
					videos.addAll(vChartListBean.getVideosList());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				updateUI();
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				LogUtil.e(TAG, "getVChartList failed response = "+error.getLocalizedMessage());
			}
		}), "getVChartList");
	}
	private void updatePeriod(Periods periods) {
		String str = getString(R.string.vchart_period);
		String format = String.format(str, periods.getYear(), periods.getNo(),
				periods.getBeginDateText(), periods.getEndDateText());
		periodTxt.setText(format);
	}
	@Override
	public Listener<JSONObject> responseListener() {
		return new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				try {
					LogUtil.d(TAG, "load period success response = "+response.toString());
					periodBean = new VChartPeriodBean(response);
					startLoadListData(periodBean.getPeriodsList().get(0).getDateCode());
					updatePeriod(periodBean.getPeriodsList().get(0));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				periodsList.clear();
				periodsList.addAll(periodBean.getPeriodsList());
			}

		};
	}

	@Override
	public ErrorListener errorSponseListener() {
		return new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				LogUtil.e(TAG, "load period false response = "+error.getLocalizedMessage());
			}
		};
	}

}
