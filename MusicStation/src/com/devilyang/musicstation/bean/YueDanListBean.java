package com.devilyang.musicstation.bean;

import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.devilyang.musicstation.parse.JSONParser;

/**
 * 首页悦单列表
 * @author 80074242
 *
 */
public class YueDanListBean {
	public YueDanListBean() {
	}

	public YueDanListBean(JSONObject jsonData) throws JSONException {
		this.init(jsonData);
	}

	private int totalCount;
	private List<PlayLists> playListsList;
	public class PlayLists {
		public PlayLists() {
		}

		public PlayLists(String jsonData) throws JSONException {
			this.init(new JSONObject(jsonData));
		}

		private int weekIntegral;
		private String thumbnailPic;
		private int videoCount;
		private String createdTime;
		private String updateTime;
		private int status;
		private int totalUser;
		private String playListBigPic;
		private int totalViews;
		private String playListPic;
		private int totalFavorites;
		private int integral;
		private Creator creator;
		public class Creator {
			public Creator() {
			}

			public Creator(String jsonData) throws JSONException {
				this.init(new JSONObject(jsonData));
			}

			private int uid;
			private String largeAvatar;
			private String smallAvatar;
			private String nickName;

			public void init(JSONObject jsonObject) throws JSONException {
				JSONParser parser = new JSONParser(jsonObject);

				uid = parser.getInt("uid");
				largeAvatar = parser.getString("largeAvatar");
				smallAvatar = parser.getString("smallAvatar");
				nickName = parser.getString("nickName");
			}


			public int getUid() {
				return uid;
			}
			public void setUid(int uid) {
				this.uid = uid;
			}

			public String getLargeAvatar() {
				return largeAvatar;
			}
			public void setLargeAvatar(String largeAvatar) {
				this.largeAvatar = largeAvatar;
			}

			public String getSmallAvatar() {
				return smallAvatar;
			}
			public void setSmallAvatar(String smallAvatar) {
				this.smallAvatar = smallAvatar;
			}

			public String getNickName() {
				return nickName;
			}
			public void setNickName(String nickName) {
				this.nickName = nickName;
			}

		}

		private int id;
		private int rank;
		private String title;
		private String category;
		private String description;

		public void init(JSONObject jsonObject) throws JSONException {
			JSONParser parser = new JSONParser(jsonObject);

			weekIntegral = parser.getInt("weekIntegral");
			thumbnailPic = parser.getString("thumbnailPic");
			videoCount = parser.getInt("videoCount");
			createdTime = parser.getString("createdTime");
			updateTime = parser.getString("updateTime");
			status = parser.getInt("status");
			totalUser = parser.getInt("totalUser");
			playListBigPic = parser.getString("playListBigPic");
			totalViews = parser.getInt("totalViews");
			playListPic = parser.getString("playListPic");
			totalFavorites = parser.getInt("totalFavorites");
			integral = parser.getInt("integral");
			creator = new Creator();
			JSONObject object0 = parser.getJSONObject("creator");
			if(object0!=null) {
				creator.init(object0);
			}
			id = parser.getInt("id");
			rank = parser.getInt("rank");
			title = parser.getString("title");
			category = parser.getString("category");
			description = parser.getString("description");
		}


		public int getWeekIntegral() {
			return weekIntegral;
		}
		public void setWeekIntegral(int weekIntegral) {
			this.weekIntegral = weekIntegral;
		}

		public String getThumbnailPic() {
			return thumbnailPic;
		}
		public void setThumbnailPic(String thumbnailPic) {
			this.thumbnailPic = thumbnailPic;
		}

		public int getVideoCount() {
			return videoCount;
		}
		public void setVideoCount(int videoCount) {
			this.videoCount = videoCount;
		}

		public String getCreatedTime() {
			return createdTime;
		}
		public void setCreatedTime(String createdTime) {
			this.createdTime = createdTime;
		}

		public String getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}

		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}

		public int getTotalUser() {
			return totalUser;
		}
		public void setTotalUser(int totalUser) {
			this.totalUser = totalUser;
		}

		public String getPlayListBigPic() {
			return playListBigPic;
		}
		public void setPlayListBigPic(String playListBigPic) {
			this.playListBigPic = playListBigPic;
		}

		public int getTotalViews() {
			return totalViews;
		}
		public void setTotalViews(int totalViews) {
			this.totalViews = totalViews;
		}

		public String getPlayListPic() {
			return playListPic;
		}
		public void setPlayListPic(String playListPic) {
			this.playListPic = playListPic;
		}

		public int getTotalFavorites() {
			return totalFavorites;
		}
		public void setTotalFavorites(int totalFavorites) {
			this.totalFavorites = totalFavorites;
		}

		public int getIntegral() {
			return integral;
		}
		public void setIntegral(int integral) {
			this.integral = integral;
		}

		public Creator getCreator() {
			return creator;
		}
		public void setCreator(Creator creator) {
			this.creator = creator;
		}

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}

		public int getRank() {
			return rank;
		}
		public void setRank(int rank) {
			this.rank = rank;
		}

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}

		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}

		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}

	}


	public void init(JSONObject jsonObject) throws JSONException {
		JSONParser parser = new JSONParser(jsonObject);

		totalCount = parser.getInt("totalCount");
		playListsList = new ArrayList<PlayLists>();
		JSONArray array0 = parser.getJSONArray("playLists");
		if(array0!=null) {
			int length = array0.length();
			for (int i = 0; i < length; i++) {
				PlayLists obj = new PlayLists();
				obj.init(array0.getJSONObject(i));
				playListsList.add(obj);
			}
		}
	}


	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<PlayLists> getPlayListsList() {
		return playListsList;
	}
	public void setPlayListsList(List<PlayLists> playListsList) {
		this.playListsList = playListsList;
	}

}

