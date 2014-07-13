package com.devilyang.musicstation.bean;

import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.devilyang.musicstation.parse.JSONParser;

//�Զ�����ʵ��bean
public class RelatedVideosListBean {
	public RelatedVideosListBean() {
	}

	public RelatedVideosListBean(JSONObject jsonData) throws JSONException {
		this.init(jsonData);
	}

	private String thumbnailPic;
	private String albumImg;
	private int uhdVideoSize;
	private int shdVideoSize;
	private String description;
	private String posterPic;
	private String title;
	private String url;
	private int videoSize;
	private int duration;
	private String uhdUrl;
	private int totalComments;
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

	}

	private String regdate;
	private String artistName;
	private int totalViews;
	private int id;
	private String hdUrl;
	private int hdVideoSize;
	private boolean favorite;
	private List<RelatedVideos> relatedVideosList;
	public class RelatedVideos {
		public RelatedVideos() {
		}

		public RelatedVideos(String jsonData) throws JSONException {
			this.init(new JSONObject(jsonData));
		}

		private String thumbnailPic;
		private String albumImg;
		private String shdUrl;
		private int uhdVideoSize;
		private int shdVideoSize;
		private String description;
		private String posterPic;
		private String title;
		private String url;
		private int videoSize;
		private int duration;
		private String uhdUrl;
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

		}

		private String regdate;
		private String artistName;
		private int id;
		private String hdUrl;
		private int hdVideoSize;
		private int status;

		public void init(JSONObject jsonObject) throws JSONException {
			JSONParser parser = new JSONParser(jsonObject);

			thumbnailPic = parser.getString("thumbnailPic");
			albumImg = parser.getString("albumImg");
			shdUrl = parser.getString("shdUrl");
			uhdVideoSize = parser.getInt("uhdVideoSize");
			shdVideoSize = parser.getInt("shdVideoSize");
			description = parser.getString("description");
			posterPic = parser.getString("posterPic");
			title = parser.getString("title");
			url = parser.getString("url");
			videoSize = parser.getInt("videoSize");
			duration = parser.getInt("duration");
			uhdUrl = parser.getString("uhdUrl");
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
			regdate = parser.getString("regdate");
			artistName = parser.getString("artistName");
			id = parser.getInt("id");
			hdUrl = parser.getString("hdUrl");
			hdVideoSize = parser.getInt("hdVideoSize");
			status = parser.getInt("status");
		}


		public String getThumbnailPic() {
			return thumbnailPic;
		}
		public void setThumbnailPic(String thumbnailPic) {
			this.thumbnailPic = thumbnailPic;
		}

		public String getAlbumImg() {
			return albumImg;
		}
		public void setAlbumImg(String albumImg) {
			this.albumImg = albumImg;
		}

		public String getShdUrl() {
			return shdUrl;
		}
		public void setShdUrl(String shdUrl) {
			this.shdUrl = shdUrl;
		}

		public int getUhdVideoSize() {
			return uhdVideoSize;
		}
		public void setUhdVideoSize(int uhdVideoSize) {
			this.uhdVideoSize = uhdVideoSize;
		}

		public int getShdVideoSize() {
			return shdVideoSize;
		}
		public void setShdVideoSize(int shdVideoSize) {
			this.shdVideoSize = shdVideoSize;
		}

		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}

		public String getPosterPic() {
			return posterPic;
		}
		public void setPosterPic(String posterPic) {
			this.posterPic = posterPic;
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

		public int getVideoSize() {
			return videoSize;
		}
		public void setVideoSize(int videoSize) {
			this.videoSize = videoSize;
		}

		public int getDuration() {
			return duration;
		}
		public void setDuration(int duration) {
			this.duration = duration;
		}

		public String getUhdUrl() {
			return uhdUrl;
		}
		public void setUhdUrl(String uhdUrl) {
			this.uhdUrl = uhdUrl;
		}

		public List<Artists> getArtistsList() {
			return artistsList;
		}
		public void setArtistsList(List<Artists> artistsList) {
			this.artistsList = artistsList;
		}

		public String getRegdate() {
			return regdate;
		}
		public void setRegdate(String regdate) {
			this.regdate = regdate;
		}

		public String getArtistName() {
			return artistName;
		}
		public void setArtistName(String artistName) {
			this.artistName = artistName;
		}

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}

		public String getHdUrl() {
			return hdUrl;
		}
		public void setHdUrl(String hdUrl) {
			this.hdUrl = hdUrl;
		}

		public int getHdVideoSize() {
			return hdVideoSize;
		}
		public void setHdVideoSize(int hdVideoSize) {
			this.hdVideoSize = hdVideoSize;
		}

		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}

	}

	private int status;

	public void init(JSONObject jsonObject) throws JSONException {
		JSONParser parser = new JSONParser(jsonObject);

		thumbnailPic = parser.getString("thumbnailPic");
		albumImg = parser.getString("albumImg");
		uhdVideoSize = parser.getInt("uhdVideoSize");
		shdVideoSize = parser.getInt("shdVideoSize");
		description = parser.getString("description");
		posterPic = parser.getString("posterPic");
		title = parser.getString("title");
		url = parser.getString("url");
		videoSize = parser.getInt("videoSize");
		duration = parser.getInt("duration");
		uhdUrl = parser.getString("uhdUrl");
		totalComments = parser.getInt("totalComments");
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
		regdate = parser.getString("regdate");
		artistName = parser.getString("artistName");
		totalViews = parser.getInt("totalViews");
		id = parser.getInt("id");
		hdUrl = parser.getString("hdUrl");
		hdVideoSize = parser.getInt("hdVideoSize");
		favorite = parser.getBoolean("favorite");
		relatedVideosList = new ArrayList<RelatedVideos>();
		JSONArray array1 = parser.getJSONArray("relatedVideos");
		if(array1!=null) {
			int length = array1.length();
			for (int i = 0; i < length; i++) {
				RelatedVideos obj = new RelatedVideos();
				obj.init(array1.getJSONObject(i));
				relatedVideosList.add(obj);
			}
		}
		status = parser.getInt("status");
	}


	public String getThumbnailPic() {
		return thumbnailPic;
	}
	public void setThumbnailPic(String thumbnailPic) {
		this.thumbnailPic = thumbnailPic;
	}

	public String getAlbumImg() {
		return albumImg;
	}
	public void setAlbumImg(String albumImg) {
		this.albumImg = albumImg;
	}

	public int getUhdVideoSize() {
		return uhdVideoSize;
	}
	public void setUhdVideoSize(int uhdVideoSize) {
		this.uhdVideoSize = uhdVideoSize;
	}

	public int getShdVideoSize() {
		return shdVideoSize;
	}
	public void setShdVideoSize(int shdVideoSize) {
		this.shdVideoSize = shdVideoSize;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getPosterPic() {
		return posterPic;
	}
	public void setPosterPic(String posterPic) {
		this.posterPic = posterPic;
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

	public int getVideoSize() {
		return videoSize;
	}
	public void setVideoSize(int videoSize) {
		this.videoSize = videoSize;
	}

	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getUhdUrl() {
		return uhdUrl;
	}
	public void setUhdUrl(String uhdUrl) {
		this.uhdUrl = uhdUrl;
	}

	public int getTotalComments() {
		return totalComments;
	}
	public void setTotalComments(int totalComments) {
		this.totalComments = totalComments;
	}

	public List<Artists> getArtistsList() {
		return artistsList;
	}
	public void setArtistsList(List<Artists> artistsList) {
		this.artistsList = artistsList;
	}

	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public int getTotalViews() {
		return totalViews;
	}
	public void setTotalViews(int totalViews) {
		this.totalViews = totalViews;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getHdUrl() {
		return hdUrl;
	}
	public void setHdUrl(String hdUrl) {
		this.hdUrl = hdUrl;
	}

	public int getHdVideoSize() {
		return hdVideoSize;
	}
	public void setHdVideoSize(int hdVideoSize) {
		this.hdVideoSize = hdVideoSize;
	}

	public boolean getFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public List<RelatedVideos> getRelatedVideosList() {
		return relatedVideosList;
	}
	public void setRelatedVideosList(List<RelatedVideos> relatedVideosList) {
		this.relatedVideosList = relatedVideosList;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}

