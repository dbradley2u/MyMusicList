/***********************************************************
 * Denise Bradley, Baker College CS351, Lab 4A & 4B
 * 10/22/14
 ***********************************************************/

package com.example.mymusiclist;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MusicListMainActivity extends Activity {
	
	private static final String TAG = "MusicList";
	private static final String SONG_TITLE = "song_title";
	private MediaPlayer mSound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_list_main);
		
		ListView listView = (ListView)findViewById(R.id.listViewSong);
		List<Song> songs = new MyMusicListService().findAll();
		
		mSound = MediaPlayer.create(this, R.raw.click);
		
		final SongAdapter songAdapter = new SongAdapter(this, R.layout.activity_music_list_main, songs);
		listView.setAdapter(songAdapter);
				
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mSound.start();
				Song song = (Song)songAdapter.getItem(position);
				Log.d(TAG, "You Selected " + song.getName());
				
				Intent intent = new Intent(view.getContext(), SongDetailActivity.class);
				intent.putExtra(SONG_TITLE, song.getName());
				startActivity(intent);
			}
			
		});
				
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mSound != null){
			mSound.stop();
			mSound.release();
			mSound = null;	
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.music_list_main, menu);
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
