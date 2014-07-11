package com.devilyang.musicstation.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.devilyang.musicstation.parse.JSONParser;
/**
 * 地区
 * @author 80074242
 *
 */
public class AreaBean {
	public AreaBean() {
	}

	public AreaBean(JSONObject jsonData) throws JSONException {
		this.init(jsonData);
	}

	private String name;
	private String code;

	public void init(JSONObject jsonObject) throws JSONException {
		JSONParser parser = new JSONParser(jsonObject);

		name = parser.getString("name");
		code = parser.getString("code");
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public boolean equals(Object o) {
		if(o==null){
			return false;
		}
		if (this.getClass() == o.getClass()) {
			AreaBean areaBean = (AreaBean) o;
			if (this.getName().equalsIgnoreCase(areaBean.getName())
					&& this.getCode().equals(areaBean.getCode())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}
}

