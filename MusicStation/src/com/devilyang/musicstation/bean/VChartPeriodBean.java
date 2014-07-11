package com.devilyang.musicstation.bean;

import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.devilyang.musicstation.parse.JSONParser;

public class VChartPeriodBean {
	public VChartPeriodBean() {
	}

	public VChartPeriodBean(JSONObject jsonData) throws JSONException {
		this.init(jsonData);
	}

	private List<Periods> periodsList;
	public class Periods {
		public Periods() {
		}

		public Periods(String jsonData) throws JSONException {
			this.init(new JSONObject(jsonData));
		}

		private int no;
		private String beginDateText;
		private int year;
		private String endDateText;
		private int dateCode;

		public void init(JSONObject jsonObject) throws JSONException {
			JSONParser parser = new JSONParser(jsonObject);

			no = parser.getInt("no");
			beginDateText = parser.getString("beginDateText");
			year = parser.getInt("year");
			endDateText = parser.getString("endDateText");
			dateCode = parser.getInt("dateCode");
		}


		public int getNo() {
			return no;
		}
		public void setNo(int no) {
			this.no = no;
		}

		public String getBeginDateText() {
			return beginDateText;
		}
		public void setBeginDateText(String beginDateText) {
			this.beginDateText = beginDateText;
		}

		public int getYear() {
			return year;
		}
		public void setYear(int year) {
			this.year = year;
		}

		public String getEndDateText() {
			return endDateText;
		}
		public void setEndDateText(String endDateText) {
			this.endDateText = endDateText;
		}

		public int getDateCode() {
			return dateCode;
		}
		public void setDateCode(int dateCode) {
			this.dateCode = dateCode;
		}

	}

	private String[] years;

	public void init(JSONObject jsonObject) throws JSONException {
		JSONParser parser = new JSONParser(jsonObject);

		periodsList = new ArrayList<Periods>();
		JSONArray array0 = parser.getJSONArray("periods");
		if(array0!=null) {
			int length = array0.length();
			for (int i = 0; i < length; i++) {
				Periods obj = new Periods();
				obj.init(array0.getJSONObject(i));
				periodsList.add(obj);
			}
		}
		years = parser.getStringArray("years");
	}


	public List<Periods> getPeriodsList() {
		return periodsList;
	}
	public void setPeriodsList(List<Periods> periodsList) {
		this.periodsList = periodsList;
	}

	public String[] getYears() {
		return years;
	}
	public void setYears(String[] years) {
		this.years = years;
	}

}

