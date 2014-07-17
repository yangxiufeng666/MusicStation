package com.devilyang.musicstation.cache;

import com.devilyang.musicstation.MusicStationApplication;

import android.content.Context;

public class CacheManager {
	private static volatile CacheManager instance = null;
	private static ACache aCache;
	
	private CacheManager() {
	}

	public static CacheManager getInstance() {
		if(instance==null){
			synchronized("instance") {
				instance = new CacheManager();
			}
		}
		return instance;
	}

	public ACache getACache(){
		return ACache.get(MusicStationApplication.getInstance());
	}
	
}
