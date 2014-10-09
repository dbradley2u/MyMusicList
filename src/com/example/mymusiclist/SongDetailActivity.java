package com.example.mymusiclist;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.net.URL;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import search.ImageResults;
import search.Result;
import util.Keys;
import util.UrlFetchUtil;

public class SongDetailActivity extends Activity {
	
	private static final String TAG = "MusicList";
	private static final String SONG_TITLE = "song_title";
	private SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy (EEE)");
	private static final String URL="https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_song_detail);
		
		Intent intent = getIntent();
		String name = intent.getStringExtra(SONG_TITLE);
		Song song = new MyMusicListService().findOne(name);
		Log.d(TAG, "Passed in " + song.getArtist());
		
		TextView songTitle = (TextView)findViewById(R.id.textViewSongTitleText);
		songTitle.setText(song.getName());
		
		TextView songArtist = (TextView)findViewById(R.id.textViewSongArtistText);
        songArtist.setText(song.getArtist());

        TextView songAlbum = (TextView)findViewById(R.id.textViewSongAlbumText);
        songAlbum.setText(song.getAlbum());

        TextView songPublishedDate = (TextView)findViewById(R.id.textViewSongDateText);
        songPublishedDate.setText(df.format(song.getPublishedDate()));
        
        new RandomImageAsyncTask(null).execute(song.getName(),song.getAlbum(),song.getArtist());
        
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
	
	class RandomImageAsyncTask extends AsyncTask<String, Integer, Bitmap>{
		private Context context;
        public RandomImageAsyncTask(Context context){
            this.context=context;
        }
		private static final String URL="https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
		
		@Override
		protected Bitmap doInBackground(String... params){
			String json = UrlFetchUtil.getJSON(URL + joinString(params));
            Log.d("",json);
			
			//search google for images
			ImageResults imageResults = new ImageResults();
			
			//attempt at least three times to get an image
			String image2get = getRandomImageUrl(imageResults,0);
			
			//return image
            Bitmap bitmap = UrlFetchUtil.fetchImage(image2get,context);
            if(bitmap!=null){
                return bitmap;
            }else{
                return BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.loading);
            }
		}
		
		@Override
		protected void onPostExecute(Bitmap bitmap){
			
				ImageView iv = (ImageView)findViewById(R.id.imageViewSong);
				if (iv != null && bitmap != null) {
					iv.setImageBitmap(bitmap);
				}
			
			
		}
		
		private String getJSON(String url, int timeout){
			try {
                URL u = new URL(url);
                HttpURLConnection c = (HttpURLConnection)u.openConnection();
                c.setRequestMethod("GET");
                c.setRequestProperty("Content-Length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(timeout);
                c.setReadTimeout(timeout);
                c.connect();
                int status = c.getResponseCode();
                
                switch(status){
                	case 200:
                	case 201:
                		BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                		StringBuilder sb = new StringBuilder();
                		String line;
                		while ((line = br.readLine()) != null){
                			sb.append(line+"\n");
                		}
                		br.close();
                		return sb.toString();
                }
				
            } catch (MalformedURLException ex){
				Log.e("", ex.getMessage());
			} catch (IOException ex){
				Log.e("", ex.getMessage());
			}
			return null;
		}
		
		private String getRandomImageUrl(ImageResults imageResults, int count){
			if (imageResults==null){
					return null;
				}
				int lCount = count;
				if (lCount>3){
					Log.e(TAG,"Unable to retrieve an image on three attempts");
					return null;
				}
				List<Result> results = imageResults.getResponseData().getResults();
				int randomNumber = ( int )( Math.random() * results.size());
				for(int i = randomNumber; i<results.size(); i++){
					Result result = results.get(i);
					if (Integer.parseInt(result.getHeight())<=600 &&
							Integer.parseInt(result.getWidth())<=800){
						return result.getUnescapedUrl();
					}
				else{
					i++;
				}
			}
			return getRandomImageUrl(imageResults, count);
		}
		
		private Bitmap fetchImage(String strUrl, Context context){
			if(strUrl==null){
	            return null;
	        }

	        try {
	            URL url = new URL(strUrl);
	            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
	            if(httpCon.getResponseCode()!=200){
	                throw new Exception("Failed to Connect");
	            }

	            InputStream is = httpCon.getInputStream();
	            return BitmapFactory.decodeStream(is);
	        } catch (MalformedURLException e) {
	            Log.e(TAG, "malformedurl: " + strUrl);
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        //return default image if nothing is loaded
	        return BitmapFactory.decodeResource(context.getResources(),
	                R.drawable.loading);
	    }
						
		private String joinString(String... params){
			StringBuilder sb = new StringBuilder();
			for(String param:params){
				param = param.replace(" ","+");//remove spaces
				sb.append(param + "+");
			}
			return sb.toString().substring(0, sb.toString().length() - 1);
		}
		
		
	}

}
