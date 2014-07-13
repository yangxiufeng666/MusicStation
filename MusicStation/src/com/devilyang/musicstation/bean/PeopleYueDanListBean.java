package com.devilyang.musicstation.bean;

import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.devilyang.musicstation.parse.JSONParser;

/**
 * 某个人的悦单详情
 * @author 20082755
 *
 */
public class PeopleYueDanListBean {
	public PeopleYueDanListBean() {
	}

	public PeopleYueDanListBean(JSONObject jsonData) throws JSONException {
		this.init(jsonData);
	}

	private String thumbnailPic;
	private Creator creator;
	public class Creator {
		public Creator() {
		}

		public Creator(String jsonData) throws JSONException {
			this.init(new JSONObject(jsonData));
		}

		private int uid;
		private String nickName;
		private String smallAvatar;
		private String largeAvatar;

		public void init(JSONObject jsonObject) throws JSONException {
			JSONParser parser = new JSONParser(jsonObject);

			uid = parser.getInt("uid");
			nickName = parser.getString("nickName");
			smallAvatar = parser.getString("smallAvatar");
			largeAvatar = parser.getString("largeAvatar");
		}


		public int getUid() {
			return uid;
		}
		public void setUid(int uid) {
			this.uid = uid;
		}

		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public String getSmallAvatar() {
			return smallAvatar;
		}
		public void setSmallAvatar(String smallAvatar) {
			this.smallAvatar = smallAvatar;
		}

		public String getLargeAvatar() {
			return largeAvatar;
		}
		public void setLargeAvatar(String largeAvatar) {
			this.largeAvatar = largeAvatar;
		}

	}

	private int totalFavorites;
	private String description;
	private String updateTime;
	private List<Videos> videosList;
	public class Videos {
		public Videos() {
		}

		public Videos(String jsonData) throws JSONException {
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

	private String title;
	private String playListPic;
	private int videoCount;
	private int integral;
	private int weekIntegral;
	private int totalUser;
	private String createdTime;
	private int rank;
	private int totalViews;
	private int id;
	private String playListBigPic;
	private String category;
	private int status;

	public void init(JSONObject jsonObject) throws JSONException {
		JSONParser parser = new JSONParser(jsonObject);

		thumbnailPic = parser.getString("thumbnailPic");
		creator = new Creator();
		JSONObject object0 = parser.getJSONObject("creator");
		if(object0!=null) {
			creator.init(object0);
		}
		totalFavorites = parser.getInt("totalFavorites");
		description = parser.getString("description");
		updateTime = parser.getString("updateTime");
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
		title = parser.getString("title");
		playListPic = parser.getString("playListPic");
		videoCount = parser.getInt("videoCount");
		integral = parser.getInt("integral");
		weekIntegral = parser.getInt("weekIntegral");
		totalUser = parser.getInt("totalUser");
		createdTime = parser.getString("createdTime");
		rank = parser.getInt("rank");
		totalViews = parser.getInt("totalViews");
		id = parser.getInt("id");
		playListBigPic = parser.getString("playListBigPic");
		category = parser.getString("category");
		status = parser.getInt("status");
	}


	public String getThumbnailPic() {
		return thumbnailPic;
	}
	public void setThumbnailPic(String thumbnailPic) {
		this.thumbnailPic = thumbnailPic;
	}

	public Creator getCreator() {
		return creator;
	}
	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	public int getTotalFavorites() {
		return totalFavorites;
	}
	public void setTotalFavorites(int totalFavorites) {
		this.totalFavorites = totalFavorites;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<Videos> getVideosList() {
		return videosList;
	}
	public void setVideosList(List<Videos> videosList) {
		this.videosList = videosList;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlayListPic() {
		return playListPic;
	}
	public void setPlayListPic(String playListPic) {
		this.playListPic = playListPic;
	}

	public int getVideoCount() {
		return videoCount;
	}
	public void setVideoCount(int videoCount) {
		this.videoCount = videoCount;
	}

	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public int getWeekIntegral() {
		return weekIntegral;
	}
	public void setWeekIntegral(int weekIntegral) {
		this.weekIntegral = weekIntegral;
	}

	public int getTotalUser() {
		return totalUser;
	}
	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}

	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
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

	public String getPlayListBigPic() {
		return playListBigPic;
	}
	public void setPlayListBigPic(String playListBigPic) {
		this.playListBigPic = playListBigPic;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}

