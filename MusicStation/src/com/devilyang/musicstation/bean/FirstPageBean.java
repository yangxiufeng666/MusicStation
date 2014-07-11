package com.devilyang.musicstation.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.devilyang.musicstation.parse.JSONParser;

/**
 * 首页
 * @author 80074242
 *
 */
public class FirstPageBean {
	public FirstPageBean() {
	}

	public FirstPageBean(JSONObject jsonData) throws JSONException {
		this.init(jsonData);
	}

	private int id;
	private String uhdUrl;
	private int hdVideoSize;
	private String title;
	private String thumbnailPic;
	private int status;
	private String description;
	private String posterPic;
	private String hdUrl;
	private String type;
	private int videoSize;
	private int uhdVideoSize;
	private String url;

	public void init(JSONObject jsonObject) throws JSONException {
		JSONParser parser = new JSONParser(jsonObject);

		id = parser.getInt("id");
		uhdUrl = parser.getString("uhdUrl");
		hdVideoSize = parser.getInt("hdVideoSize");
		title = parser.getString("title");
		thumbnailPic = parser.getString("thumbnailPic");
		status = parser.getInt("status");
		description = parser.getString("description");
		posterPic = parser.getString("posterPic");
		hdUrl = parser.getString("hdUrl");
		type = parser.getString("type");
		videoSize = parser.getInt("videoSize");
		uhdVideoSize = parser.getInt("uhdVideoSize");
		url = parser.getString("url");
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getUhdUrl() {
		return uhdUrl;
	}
	public void setUhdUrl(String uhdUrl) {
		this.uhdUrl = uhdUrl;
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

	public String getHdUrl() {
		return hdUrl;
	}
	public void setHdUrl(String hdUrl) {
		this.hdUrl = hdUrl;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public int getVideoSize() {
		return videoSize;
	}
	public void setVideoSize(int videoSize) {
		this.videoSize = videoSize;
	}

	public int getUhdVideoSize() {
		return uhdVideoSize;
	}
	public void setUhdVideoSize(int uhdVideoSize) {
		this.uhdVideoSize = uhdVideoSize;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			if (this.getClass() == obj.getClass()) {
				FirstPageBean u = (FirstPageBean) obj;
				if (this.getId() == u.getId()) {
					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}
		}
	}
}

