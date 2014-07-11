package com.devilyang.musicstation.bean;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.devilyang.musicstation.parse.JSONParser;
/**
 * MV列表
 * @author 80074242
 *
 */
public class MVListBean {
	public MVListBean() {
	}

	public MVListBean(JSONObject jsonData) throws JSONException {
		this.init(jsonData);
	}

	private int totalCount;
	private List<Videos> videosList;
	public class Videos {
		public Videos() {
		}

		public Videos(String jsonData) throws JSONException {
			this.init(new JSONObject(jsonData));
		}

		private String thumbnailPic;
		private int status;
		private String posterPic;
		private String hdUrl;
		private int videoSize;
		private String url;
		private int id;
		private int shdVideoSize;
		private List<Artists> artistsList;
		public class Artists {
			public Artists() {
			}

			public Artists(String jsonData) throws JSONException {
				this.init(new JSONObject(jsonData));
			}

			private int artistId;
			private String artistName;

			public void init(JSONObject jsonObject) throws JSONException {
				JSONParser parser = new JSONParser(jsonObject);

				artistId = parser.getInt("artistId");
				artistName = parser.getString("artistName");
			}


			public int getArtistId() {
				return artistId;
			}
			public void setArtistId(int artistId) {
				this.artistId = artistId;
			}

			public String getArtistName() {
				return artistName;
			}
			public void setArtistName(String artistName) {
				this.artistName = artistName;
			}
			@Override
			public boolean equals(Object o) {
				if(o==null){
					return false;
				}
				if(o instanceof Videos){
					Videos videos = (Videos)o;
					if(videos.getId()==getId()){
						return true;
					}
				}
				return false;
			}
		}

		private String uhdUrl;
		private String shdUrl;
		private int duration;
		private int hdVideoSize;
		private String title;
		private String description;
		private String artistName;
		private int uhdVideoSize;
		private String albumImg;

		public void init(JSONObject jsonObject) throws JSONException {
			JSONParser parser = new JSONParser(jsonObject);

			thumbnailPic = parser.getString("thumbnailPic");
			status = parser.getInt("status");
			posterPic = parser.getString("posterPic");
			hdUrl = parser.getString("hdUrl");
			videoSize = parser.getInt("videoSize");
			url = parser.getString("url");
			id = parser.getInt("id");
			shdVideoSize = parser.getInt("shdVideoSize");
			artistsList = new ArrayList<Artists>();
			JSONArray array0 = parser.getJSONArray("artists");
			if(array0!=null) {
				int length = array0.length();
				for (int i = 0; i < length; i++) {
					Artists obj = new Artists();
					obj.init(array0.getJSONObject(i));
					artistsList.add(obj);
				}
			}
			uhdUrl = parser.getString("uhdUrl");
			shdUrl = parser.getString("shdUrl");
			duration = parser.getInt("duration");
			hdVideoSize = parser.getInt("hdVideoSize");
			title = parser.getString("title");
			description = parser.getString("description");
			artistName = parser.getString("artistName");
			uhdVideoSize = parser.getInt("uhdVideoSize");
			albumImg = parser.getString("albumImg");
		}


		public String getThumbnailPic() {
			return thumbnailPic;
		}
		public void setThumbnailPic(String thumbnailPic) {
			this.thumbnailPic = thumbnailPic;
		}

		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}

		public String getPosterPic() {
			return posterPic;
		}
		public void setPosterPic(String posterPic) {
			this.posterPic = posterPic;
		}

		public String getHdUrl() {
			return hdUrl;
		}
		public void setHdUrl(String hdUrl) {
			this.hdUrl = hdUrl;
		}

		public int getVideoSize() {
			return videoSize;
		}
		public void setVideoSize(int videoSize) {
			this.videoSize = videoSize;
		}

		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}

		public int getShdVideoSize() {
			return shdVideoSize;
		}
		public void setShdVideoSize(int shdVideoSize) {
			this.shdVideoSize = shdVideoSize;
		}

		public List<Artists> getArtistsList() {
			return artistsList;
		}
		public void setArtistsList(List<Artists> artistsList) {
			this.artistsList = artistsList;
		}

		public String getUhdUrl() {
			return uhdUrl;
		}
		public void setUhdUrl(String uhdUrl) {
			this.uhdUrl = uhdUrl;
		}

		public String getShdUrl() {
			return shdUrl;
		}
		public void setShdUrl(String shdUrl) {
			this.shdUrl = shdUrl;
		}

		public int getDuration() {
			return duration;
		}
		public void setDuration(int duration) {
			this.duration = duration;
		}

		public int getHdVideoSize() {
			return hdVideoSize;
		}
		public void setHdVideoSize(int hdVideoSize) {
			this.hdVideoSize = hdVideoSize;
		}

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}

		public String getArtistName() {
			return artistName;
		}
		public void setArtistName(String artistName) {
			this.artistName = artistName;
		}

		public int getUhdVideoSize() {
			return uhdVideoSize;
		}
		public void setUhdVideoSize(int uhdVideoSize) {
			this.uhdVideoSize = uhdVideoSize;
		}

		public String getAlbumImg() {
			return albumImg;
		}
		public void setAlbumImg(String albumImg) {
			this.albumImg = albumImg;
		}

	}


	public void init(JSONObject jsonObject) throws JSONException {
		JSONParser parser = new JSONParser(jsonObject);

		totalCount = parser.getInt("totalCount");
		videosList = new ArrayList<Videos>();
		JSONArray array0 = parser.getJSONArray("videos");
		if(array0!=null) {
			int length = array0.length();
			for (int i = 0; i < length; i++) {
				Videos obj = new Videos();
				obj.init(array0.getJSONObject(i));
				videosList.add(obj);
			}
		}
	}


	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<Videos> getVideosList() {
		return videosList;
	}
	public void setVideosList(List<Videos> videosList) {
		this.videosList = videosList;
	}

}

