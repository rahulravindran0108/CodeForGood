package com.example.yuvaparivarthan;

public class Data {
	String date;
	String location;
	String attendance;
	String campCode;
	String feesCollected;
	
	Data(String date,String location,String attendance,String campCode,String feesCollected) {
		this.date = date;
		this.location = location;
		this.attendance = attendance;
		this.campCode = campCode;
		this.feesCollected = feesCollected;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getCampCode() {
		return campCode;
	}

	public void setCampCode(String campCode) {
		this.campCode = campCode;
	}

	public String getFeesCollected() {
		return feesCollected;
	}

	public void setFeesCollected(String feesCollected) {
		this.feesCollected = feesCollected;
	}
	

}
