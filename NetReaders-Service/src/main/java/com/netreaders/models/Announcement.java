package com.netreaders.models;

import java.util.Date;

public class Announcement {
	private Date announcement_date; 
	private String description;
	private boolean published;
	public Date getAnnouncement_date() {
		return announcement_date;
	}
	public void setAnnouncement_date(Date announcement_date) {
		this.announcement_date = announcement_date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	
	
}
