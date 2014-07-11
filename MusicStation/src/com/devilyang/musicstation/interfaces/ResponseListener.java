package com.devilyang.musicstation.interfaces;

import com.android.volley.Response;

public interface ResponseListener {
	public Response.Listener<? extends Object> responseListener();
	public Response.ErrorListener errorSponseListener();
}
