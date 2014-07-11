package com.devilyang.musicstation;

import com.devilyang.musicstation.net.RequestManager;

import android.app.Application;

public class MusicStationApplication extends Application{
	private static volatile MusicStationApplication instance = null;

	public static MusicStationApplication getInstance() {
		if(instance==null){
			synchronized (instance) {
				instance = new MusicStationApplication();
			}
		}
		return instance;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		RequestManager.init(this);
	}
	
}
