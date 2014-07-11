package com.devilyang.musicstation.interfaces.impl;

import org.json.JSONArray;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.devilyang.musicstation.interfaces.ResponseListener;

public class GetVChartPeriodListener implements ResponseListener{

	
	@Override
	public Listener<JSONArray> responseListener() {
		return new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				
			}
		};
	}

	@Override
	public ErrorListener errorSponseListener() {
		return new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				
			}
		};
	}

}
