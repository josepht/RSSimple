package com.xenno.rssparser;

public class RSSEntry {
	private String title;
	private String link;

	public void setTitle(String s) {
		this.title = s;
	}

	public String getTitle() {
		return title;
	}

	public void setLink(String s) {
		this.link = s;
	}

	public String getLink() {
		return link;
	}

	public String toString() {
		return (this.title);
	}
}
