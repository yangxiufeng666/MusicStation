package com.devilyang.musicstation.fragment;

import com.android.volley.Request;
import com.devilyang.musicstation.interfaces.ResponseListener;
import com.devilyang.musicstation.net.RequestManager;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment implements ResponseListener{
	
	public BaseFragment() {
		super();
	}

	public void executeRequest(Request<?> request,Object tag) {
		RequestManager.addRequest(request, tag);
	}
}
