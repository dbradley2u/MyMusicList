/***********************************************************
 * Denise Bradley, Baker College CS351, Lab 4A & 4B
 * 10/22/14
 ***********************************************************/

package com.example.mymusiclist;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SongAdapter extends ArrayAdapter<Song> {

	private Context mContext;
	private List<Song> mEntries;
	private SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy (EEE)");
		
	
	public SongAdapter(Context context, int textViewResourceID, List<Song> entries) {
		super(context, textViewResourceID, entries);
		// TODO Auto-generated constructor stub
		mContext = context;
		mEntries = entries;
		
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if(view==null){
			
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_view_for_each_song, parent, false);			
		}
		
		final Song song = mEntries.get(position);
		
		TextView textViewTitle = (TextView)view.findViewById(R.id.textViewSongTitle);
        textViewTitle.setText(song.getName() + " (" + song.getArtist() + ")");

        TextView textViewAlbum = (TextView)view.findViewById(R.id.textViewSongArtist);
        textViewAlbum.setText(song.getAlbum());

        TextView textViewPublishedDate = (TextView)view.findViewById(R.id.textViewSongDate);
        textViewPublishedDate.setText(df.format(song.getPublishedDate()));
		
		return view;
	}
	
	

}
