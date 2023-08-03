/**
 * @author panweixing
 */
package com.sh3h.mobileutil.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface IViewBuilder {

	View build(Context context, LayoutInflater inflater, View convertView, ViewGroup parent,
			Object data, int position);

}
