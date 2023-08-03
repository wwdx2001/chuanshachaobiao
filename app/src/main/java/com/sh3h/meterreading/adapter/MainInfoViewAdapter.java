package com.sh3h.meterreading.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sh3h.meterreading.R;

import java.util.ArrayList;

/**
 * Created by liurui on 2016/2/1.
 */
public class MainInfoViewAdapter extends BaseAdapter {

    public static final int TYPE_LIST = 1;
    public static final int TYPE_GRID = 2;
    private Context mContext;
    private LayoutInflater mInflater;
    private int mIconID;
    private int mIconName;
    private int mIconImg;
    private int mCount;
    private ArrayList<ViewHolder> mViewHolders;
    private int[] mUnReads;
    private int mType;

    public MainInfoViewAdapter(Context mContext, LayoutInflater mInflater, int mIconID, int mIconName, int mIconImg, int mType) {
        this.mContext = mContext;
        this.mInflater = mInflater;
        this.mIconID = mIconID;
        this.mIconName = mIconName;
        this.mIconImg = mIconImg;
        this.mType = mType;
        mCount = 0;
        initData();
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public void initData() {
        int[] ids;
        String[] names;
        TypedArray icons;

        ids = mContext.getResources().getIntArray(mIconID);
        names = mContext.getResources().getStringArray(mIconName);
        icons = mContext.getResources().obtainTypedArray(mIconImg);

        if ((ids == null) || (names == null) || (icons == null)
                || (ids.length != names.length)
                || (names.length != icons.length())) {
            return;
        }
        mCount = ids.length;
        mUnReads = new int[mCount];
        mViewHolders = new ArrayList<>();

        for (int i = 0; i < mCount; i++) {
//            if (icons.getText(i).equals("0")) {
//                mViewHolders.add(new ViewHolder(ids[i], icons.getDrawable(i),
//                        names[i], mUnReads[i]));
//                continue;
//            }

            mViewHolders.add(new ViewHolder(ids[i], icons.getDrawable(i),
                    names[i], mUnReads[i]));
        }

        refreshData();

        icons.recycle();

    }

    @Override
    public int getCount() {
        if (mViewHolders != null)
            return mViewHolders.size();
        return mCount;
    }

    @Override
    public Object getItem(int position) {
        return mViewHolders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.mViewHolders.get(position).getId();
    }

    public void setmUnReads(int[] mUnReads) {
        this.mUnReads = mUnReads;
    }

    public int[] getmUnReads() {
        return mUnReads;
    }

    public void refreshData() {
        if ((mViewHolders == null) || (mCount <= 0)) {
            return;
        }
        for (int i = 0; i < mCount; i++) {
            ViewHolder viewHolder = mViewHolders.get(i);
            viewHolder.setUnread(mUnReads[i]);
        }

        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = (ViewHolder) this.getItem(position);
        //加载MyWork页面布局
//        convertView = mInflater.inflate(
//                    R.layout.item_main_gridview, null);
        if (mType == TYPE_LIST)
            convertView = mInflater.inflate(R.layout.item_main_listview, null);
        else
            convertView = mInflater.inflate(
                    R.layout.item_main_gridview, null);

        ImageView icon = (ImageView) convertView
                .findViewById(R.id.item_icon);
        TextView title = (TextView) convertView
                .findViewById(R.id.item_title);
        if (viewHolder.getId() != 0) {
            icon.setImageDrawable(viewHolder.getIcon());
            title.setText(viewHolder.getText());
        }

        setFragmentMyworkAdapterView(convertView, viewHolder);

        return convertView;
    }

    private void setFragmentMyworkAdapterView(View view, ViewHolder viewHolder) {

        TextView unread = (TextView) view
                .findViewById(R.id.item_unread);

        if (viewHolder.getUnread() > 0) {
            unread.setText(String.valueOf(viewHolder.getUnread()));
            unread.setVisibility(View.VISIBLE);
        } else {
            unread.setVisibility(View.GONE);
        }
    }

    private class ViewHolder {
        private int _id;
        private Drawable _icon;
        private String _text;
        private int _unread;

        public ViewHolder(int id, Drawable icon, String text, int unread) {
            this._id = id;
            this._icon = icon;
            this._text = text;
            this._unread = unread;
        }

        public int getUnread() {
            return _unread;
        }

        public void setUnread(int unread) {
            this._unread = unread;
        }

        public int getId() {
            return _id;
        }

        public void setId(int id) {
            this._id = id;
        }

        public Drawable getIcon() {
            return _icon;
        }

        public void setIcon(Drawable icon) {
            this._icon = icon;
        }

        public String getText() {
            return _text;
        }

        public void setText(String text) {
            this._text = text;
        }
    }
}
