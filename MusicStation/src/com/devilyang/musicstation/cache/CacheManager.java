package com.devilyang.musicstation.cache;

import java.io.IOException;

import android.content.Context;

import com.devilyang.musicstation.MusicStationApplication;

public class CacheManager {
	private static volatile CacheManager instance = null;
	private DiskLruCache mDiskLruCache;
	
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
	public DiskLruCache getmDiskLruCache(Context context) {
		if(mDiskLruCache !=null){
			return mDiskLruCache;
		}
		try {
			mDiskLruCache = DiskLruCache.open(
					Utils.getDiskCacheDir(context, "thumb"),
					Utils.getAppVersion(context), 10, 10 * 1024 * 1024);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mDiskLruCache;
	}
	
}
