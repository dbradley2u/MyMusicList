/***********************************************************
 * Denise Bradley, Baker College CS351, Lab 2
 * 10/08/14 
 ***********************************************************/

package com.example.mymusiclist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyMusicListService {

	private List<Song> songs;
	
	{
		songs = new ArrayList<Song>();
		
		songs.add(new Song("He Knows My Name", "Francesca Battistelli", "If We're Honest", getDate(2014, 3, 18)));
		songs.add(new Song("Fix My Eyes", "for King & Country", "Run Wild. Live Free. Love Strong.", getDate(2014, 9, 16)));
		songs.add(new Song("Moon Trance", "Lindsey Striling", "Lindsey Stirling (Deluxe Version)", getDate(2013, 10, 29)));
		songs.add(new Song("Mo Ghile Mear", "Celtic Woman", "A New Journey", getDate(2007, 1, 30)));
		songs.add(new Song("Cruise", "Florida Georgia Line", "Here's to the Good Times", getDate(2012, 12, 4)));
		songs.add(new Song("White Christmas", "Bing Crosby", "White Christmas", getDate(1995, 6, 1)));
		songs.add(new Song("Boys Round Here", "Blake Shelton", "Based On A True Story...", getDate(2013, 3, 26)));
		songs.add(new Song("Moonlight Serenade", "Glenn Miller & His Orchestra", "Glenn Miller - Greatest Hits", getDate(1996, 4, 16)));
		songs.add(new Song("It's Go Time", "Pieces of a Dream", "No Assembly Required", getDate(2004, 4, 27)));
		songs.add(new Song("Your Great Name", "Natalie Grant", "Love Revolution", getDate(2010, 8, 24)));
		
	}
	
	public List<Song>findAll(){
		return songs;
	}
	
	public Song findOne(String name){
		for(Song song:songs){
			if(song.getName().equalsIgnoreCase(name)) {
				return song;
			}
		}
		return new Song();
	}
	
	private static Date getDate(int year, int month, int day){
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, 0, 0);
		return c.getTime();
	}
	
}
