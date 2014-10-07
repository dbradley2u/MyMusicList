package com.example.mymusiclist;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class SongDetailActivity extends Activity {
	
	private static final String TAG = "MusicList";
	private static final String SONG_TITLE = "song_title";
	private SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy (EEE)");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_song_detail);
		
		Intent intent = getIntent();
		String name = intent.getStringExtra(SONG_TITLE);
		Song song = new MyMusicListService().findOne(name);
		Log.d(TAG, "Passed in " + song.getArtist());
		
		TextView songArtist = (TextView)findViewById(R.id.textViewSongArtistText);
        songArtist.setText(song.getArtist());

        TextView songAlbum = (TextView)findViewById(R.id.textViewSongAlbumText);
        songAlbum.setText(song.getAlbum());

        TextView songPublishedDate = (TextView)findViewById(R.id.textViewSongDateText);
        songPublishedDate.setText(df.format(song.getPublishedDate()));
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.song_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
