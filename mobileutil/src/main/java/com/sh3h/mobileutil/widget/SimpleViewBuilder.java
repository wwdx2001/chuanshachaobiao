/**
 * @author panweixing
 */
package com.sh3h.mobileutil.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 提供最基础的列表选择方式
 */
public class SimpleViewBuilder implements IViewBuilder {

	private int _resouceId = android.R.layout.simple_list_item_1;

	public SimpleViewBuilder() {

	}

	public SimpleViewBuilder(int resourceId) {
		this._resouceId = resourceId;
	}

	@Override
	public View build(Context context, LayoutInflater inflater, View convertView, ViewGroup parent,
					  Object data, int position) {

		View view = convertView == null? inflater.inflate(this._resouceId, null) : convertView;

		TextView text = (TextView)view.findViewById(android.R.id.text1);
		text.setText(data.toString());

		return view;
	}

}
