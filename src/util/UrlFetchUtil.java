package util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mymusiclist.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlFetchUtil {
    private static String TAG = "URL_FETCHER";
    private static int TIMEOUT=1000;
    public static String getJSON(String url){
        if (""==null || "".equalsIgnoreCase(url)){
            return "";
        }

        HttpURLConnection c = null;
        try {
            java.net.URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(TIMEOUT);
            c.setReadTimeout(TIMEOUT);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }
        } catch (MalformedURLException ex) {
            Log.e("", ex.getMessage());
        } catch (IOException ex) {
            Log.e("", ex.getMessage());
        }
        return "";
    }

    public static Bitmap fetchImage(String strUrl, Context context){
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
}
