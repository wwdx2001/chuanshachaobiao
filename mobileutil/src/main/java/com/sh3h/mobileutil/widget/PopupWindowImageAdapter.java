package com.sh3h.mobileutil.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class PopupWindowImageAdapter extends BaseAdapter {
	private List<ImageView> mImageViewList;

	public PopupWindowImageAdapter(List<ImageView> imageViewList) {
		mImageViewList = imageViewList;
	}

	@Override
	public int getCount() {
		if (mImageViewList != null) {
			return mImageViewList.size();
		} else {
			return 0;
		}
	}
	
	

	@Override
	public Object getItem(int position) {
		if (mImageViewList != null) {
			return mImageViewList.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (mImageViewList != null) {
			return mImageViewList.get(position);
		} else {
			return null;
		}
	}

}