package com.devilyang.musicstation.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayInfoBean implements Parcelable{

	private String title;
	private String url;
	private String hurl;
	private String uhurl;

	
	public static final Creator<PlayInfoBean> CREATOR = new Parcelable.Creator<PlayInfoBean>() {
		public PlayInfoBean createFromParcel(Parcel in) {
			PlayInfoBean bean = new PlayInfoBean();
			bean.title = in.readString();
			bean.url = in.readString();
			bean.hurl = in.readString();
			bean.uhurl = in.readString();
			return bean;
		}

		public PlayInfoBean[] newArray(int size) {
			return new PlayInfoBean[size];
		}
	};
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(url);
		dest.writeString(hurl);
		dest.writeString(uhurl);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHurl() {
		return hurl;
	}

	public void setHurl(String hurl) {
		this.hurl = hurl;
	}

	public String getUhurl() {
		return uhurl;
	}

	public void setUhurl(String uhurl) {
		this.uhurl = uhurl;
	}

}
