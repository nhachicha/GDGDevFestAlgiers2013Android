package com.example.androidcodelabdevfest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.androidcodelabdevfest.io.ReadStream;
import com.example.androidcodelabdevfest.model.Item;
import com.example.androidcodelabdevfest.model.ItemsAdapter;

public class ListOfProducts extends ListActivity {
	private ItemsAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAdapter = new ItemsAdapter(this);
		setListAdapter(mAdapter);
		
		new AsyncTask<Void, Void, List<Item>>() {
			
			@Override
			protected List<Item> doInBackground(Void... arg0) {
				List<Item> items = new ArrayList<Item>();
				
				try {
					InputStream input = ReadStream.get();
					BufferedReader br = new BufferedReader (new InputStreamReader(input));
					String line = null;
					StringBuilder sb = new StringBuilder ();
					
					while( null != (line=br.readLine()) ) {
						sb.append(line);
					}
					
					JSONArray jsonArray = new JSONArray(sb.toString());
					
					if (null != jsonArray) {
						
						int length=jsonArray.length();
						for (int i=0; i<length; i++) {
							JSONObject jObj =  jsonArray.getJSONObject(i);
							String name = (String) jObj.get("name");//imageName
							String imageURI = (String) jObj.get("image");//imageURI
							items.add(new Item (name, imageURI));
						}
						
					}
					

				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return items;
			}

			@Override
			protected void onPostExecute(List<Item> result) {
				mAdapter.setItemsList(result);
				mAdapter.notifyDataSetChanged();
			}
			
			
			
		}.execute();
		// download from network

	}

}