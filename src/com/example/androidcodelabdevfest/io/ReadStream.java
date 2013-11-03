package com.example.androidcodelabdevfest.io;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.androidcodelabdevfest.utils.Constants;
import com.squareup.okhttp.OkHttpClient;

public class ReadStream {
	private static OkHttpClient CLIENT = new OkHttpClient();
	
	public static InputStream get () throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append(Constants.BASE_URL).append(Constants.SERVICE_PATH).append("photos");
		URL url = new URL(builder.toString());
		
		HttpURLConnection connection = CLIENT.open(url);
	    return connection.getInputStream();
	    
	}
}
