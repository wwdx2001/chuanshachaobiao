/**
 * @author panweixing
 * 
 */
package com.sh3h.mobileutil.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SimpleArrayAdapter<T> extends BaseAdapter {
	/**
	 * Contains the list of objects that represent the data of this
	 * ArrayAdapter. The content of this list is referred to as "the array" in
	 * the documentation.
	 */
	private List<T> mObjects;

	//private int _position;
	private IViewBuilder _viewBuilder;

	/**
	 * Lock used to modify the content of {@link #mObjects}. Any write operation
	 * performed on the array should be synchronized on this lock. This lock is
	 * also used by the filter (see {@link #getFilter()} to make a synchronized
	 * copy of the original array of data.
	 */
	private final Object mLock = new Object();

	private boolean mNotifyOnChange = true;

	private Context mContext;

	private ArrayList<T> mOriginalValues;
	// private ArrayFilter mFilter;

	private LayoutInflater mInflater;

	public SimpleArrayAdapter(Context context, List<T> objects) {
		this(context, new SimpleViewBuilder(), objects);
	}

	public SimpleArrayAdapter(Context context, IViewBuilder viewBuilder, List<T> objects) {
		mContext = context;
		_viewBuilder = viewBuilder;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mObjects = objects;

	}
	
	/**
	 * @return the mObjects
	 */
	protected List<T> getmObjects() {
		return mObjects;
	}

	/**
	 * Adds the specified object at the end of the array.
	 * 
	 * @param object
	 *            The object to add at the end of the array.
	 */
	public void add(T object) {
		if (mOriginalValues != null) {
			synchronized (mLock) {
				mOriginalValues.add(object);
				if (mNotifyOnChange)
					notifyDataSetChanged();
			}
		} else {
			mObjects.add(object);
			if (mNotifyOnChange)
				notifyDataSetChanged();
		}
	}

	public void add(List<T> objects) {

		if (mOriginalValues != null) {
			synchronized (mLock) {
				for (T item : objects) {
					mOriginalValues.add(item);
				}
				if (mNotifyOnChange)
					notifyDataSetChanged();
			}
		} else {
			for (T item : objects) {
				mObjects.add(item);
			}
			if (mNotifyOnChange)
				notifyDataSetChanged();
		}
	}

	/**
	 * Inserts the specified object at the specified index in the array.
	 * 
	 * @param object
	 *            The object to insert into the array.
	 * @param index
	 *            The index at which the object must be inserted.
	 */
	public void insert(T object, int index) {
		if (mOriginalValues != null) {
			synchronized (mLock) {
				mOriginalValues.add(index, object);
				if (mNotifyOnChange)
					notifyDataSetChanged();
			}
		} else {
			mObjects.add(index, object);
			if (mNotifyOnChange)
				notifyDataSetChanged();
		}
	}

	/**
	 * Removes the specified object from the array.
	 * 
	 * @param object
	 *            The object to remove.
	 */
	public void remove(T object) {
		if (mOriginalValues != null) {
			synchronized (mLock) {
				mOriginalValues.remove(object);
			}
		} else {
			mObjects.remove(object);
		}
		if (mNotifyOnChange)
			notifyDataSetChanged();
	}

	/**
	 * Remove all elements from the list.
	 */
	public void clear() {
		if (mOriginalValues != null) {
			synchronized (mLock) {
				mOriginalValues.clear();
			}
		} else {
			mObjects.clear();
		}
		if (mNotifyOnChange)
			notifyDataSetChanged();
	}

	/**
	 * Sorts the content of this adapter using the specified comparator.
	 * 
	 * @param comparator
	 *            The comparator used to sort the objects contained in this
	 *            adapter.
	 */
	/*
	 * public void sort(Comparator<? super T> comparator) {
	 * Collections.sort(mObjects, comparator); if (mNotifyOnChange)
	 * notifyDataSetChanged(); }
	 */

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		mNotifyOnChange = true;
	}

	/**
	 * Control whether methods that change the list ({@link #add},
	 * {@link #insert}, {@link #remove}, {@link #clear}) automatically call
	 * {@link #notifyDataSetChanged}. If set to false, caller must manually call
	 * notifyDataSetChanged() to have the changes reflected in the attached
	 * view.
	 * 
	 * The default is true, and calling notifyDataSetChanged() resets the flag
	 * to true.
	 * 
	 * @param notifyOnChange
	 *            if true, modifications to the list will automatically call
	 *            {@link #notifyDataSetChanged}
	 */
	public void setNotifyOnChange(boolean notifyOnChange) {
		mNotifyOnChange = notifyOnChange;
	}

	/**
	 * Returns the context associated with this array adapter. The context is
	 * used to create views from the resource passed to the constructor.
	 * 
	 * @return The Context associated with this adapter.
	 */
	public Context getContext() {
		return mContext;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getCount() {
		return mObjects.size();
	}

	/**
	 * {@inheritDoc}
	 */
	public T getItem(int position) {
		return mObjects.get(position);
	}

	/**
	 * Returns the position of the specified item in the array.
	 * 
	 * @param item
	 *            The item to retrieve the position of.
	 * 
	 * @return The position of the specified item.
	 */
	/*
	 * public int getPosition(T item) { return mObjects.indexOf(item); }
	 */
	/**
	 * {@inheritDoc}
	 */
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = _viewBuilder.build(mContext, mInflater, convertView, parent,
				getItem(position), position);
		//_position = position;
		return convertView;
	}

}
