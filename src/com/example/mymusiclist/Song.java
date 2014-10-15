/***********************************************************
 * Denise Bradley, Baker College CS351, Lab 3A & 3B
 * 10/15/14 
 ***********************************************************/

package com.example.mymusiclist;

import java.util.Date;

public class Song {

	private String name;
	private String artist;
	private String album;
	private Date publishedDate;
	
	public Song() {
		super();
		this.name = "";
		this.artist = "";
		this.album = "";
		this.publishedDate = new Date();
	}
	
	public Song(String name, String artist, String album, Date publishedDate) {
		super();
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.publishedDate = publishedDate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbumn(String album) {
		this.album = album;
	}
	public Date getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}
	
	
}
