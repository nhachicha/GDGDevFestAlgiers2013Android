package com.example.androidcodelabdevfest.model;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidcodelabdevfest.R;
import com.example.androidcodelabdevfest.utils.Constants;
import com.squareup.picasso.Picasso;

public class ItemsAdapter extends BaseAdapter {
	private List<Item> itemsList = Collections.emptyList();
	
	public void setItemsList(List<Item> itemsList) {
		this.itemsList = itemsList;
	}

	private final Context context;

	public ItemsAdapter(Context ctx) {
		context = ctx;
	}

	
	@Override
	public int getCount() {
		return itemsList.size();
	}

	@Override
	public Item getItem(int idx) {
		return itemsList.get(idx);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ImageView itemPhoto;
		TextView itemDescription;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
			itemPhoto = (ImageView) convertView.findViewById(R.id.icon);
			itemDescription = (TextView) convertView
					.findViewById(R.id.itemDescription);

			convertView.setTag(new ViewHolder(itemPhoto, itemDescription));

		} else {
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			itemPhoto = viewHolder.itemPhoto;
			itemDescription = viewHolder.itemDescription;
		}

		Item employee = getItem(position);
		Picasso.with(context).setDebugging(true);
		Picasso.with(context)
				.load(new StringBuilder(Constants.BASE_URL)
						.append(Constants.SERVICE_PATH)
						.append(employee.getPhoto()).toString())
				.placeholder(R.drawable.place_holder).error(R.drawable.warning)
				.resize(150, 150).centerCrop().into(itemPhoto);

		itemDescription.setText(employee.getDescription());

		return convertView;
	}

	private static class ViewHolder {
		public final ImageView itemPhoto;
		public final TextView itemDescription;

		public ViewHolder(ImageView photo, TextView description) {
			this.itemPhoto = photo;
			this.itemDescription = description;
		}
	}
}
