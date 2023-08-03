/**
 *
 */
package com.sh3h.mobileutil.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.sh3h.mobileutil.R;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.widget.IViewBuilder;
import com.sh3h.mobileutil.widget.SimpleArrayAdapter;


/**
 * 列表选择对话框
 */
public class ListDialog<T> extends BaseDialog implements IViewBuilder,
		OnItemClickListener, DialogInterface.OnClickListener {

	private List<Integer> _checkedIndexes = null;
	private boolean _isMultiChooice = false;
	private List<T> _data = null;

	private int _titleResId = -1;
	// private int _wordGroupId = -1;

	private OnSelectedListener _listener = null;

	private ListView _innerListView = null;
	private SimpleArrayAdapter<T> _innerListAdapter = null;

	private float _textSize = 0;

	private int position = -1;

	public ListDialog(Context context, List<T> objects,
					  OnSelectedListener listener) {
		this(context, 0, objects, null, false, listener);
	}

	public ListDialog(Context context, int titleResId, List<T> objects,
					  OnSelectedListener listener) {
		this(context, titleResId, objects, null, false, listener);
	}

	public ListDialog(Context context, int titleResId, List<T> objects,
					  float textSixe, OnSelectedListener listener) {
		this(context, titleResId, objects, null, false, listener);
		_textSize = textSixe;
	}

	public ListDialog(Context context, List<T> objects, boolean isMultiChooice,
					  OnSelectedListener listener) {
		this(context, 0, objects, null, isMultiChooice, listener);
	}

	public ListDialog(Context context, int titleResId, List<T> objects,
					  boolean isMultiChooice, OnSelectedListener listener) {
		this(context, titleResId, objects, null, isMultiChooice, listener);
	}

	public ListDialog(Context context, int titleResId, List<T> objects,
					  int[] checkedItemIndexes, boolean isMultiChooice,
					  OnSelectedListener listener) {
		super(context);

		this._titleResId = titleResId;
		this._data = objects;
		this._isMultiChooice = isMultiChooice;
		this._listener = listener;

		this._checkedIndexes = new ArrayList<Integer>();
		if (checkedItemIndexes != null && checkedItemIndexes.length > 0) {
			for (int item : checkedItemIndexes) {
				this._checkedIndexes.add(item);
			}
		}

		this.init(context);
	}

	public ListDialog(Context context, int titleResId, List<T> objects,
					  int position, boolean isMultiChooice,
					  OnSelectedListener listener) {
		super(context);

		this._titleResId = titleResId;
		this._data = objects;
		this._isMultiChooice = isMultiChooice;
		this._listener = listener;
		this._checkedIndexes = new ArrayList<Integer>();
		this.position = position;
		this.init(context);

		this.position = -1;
	}

	// /**
	// * 支持从WordHelper中读取词语信息作为列表内容
	// *
	// * @param context
	// * 上下文
	// * @param wordGroupId
	// * 词语分组Id
	// * @param checkedItemIndexes
	// * 选中的项目索引数组
	// * @param isMultiChooice
	// * 是否MultiChooice
	// * @param listener
	// * 选中后结果，回调接口
	// */
	// public ListDialog(Context context, int titleResId, int wordGroupId, int[]
	// checkedItemIndexes,
	// boolean isMultiChooice, OnSelectedListener listener) {
	// super(context);
	//
	// this._titleResId = titleResId;
	// this._wordGroupId = wordGroupId;
	// this._isMultiChooice = isMultiChooice;
	// this._listener = listener;
	//
	// this._checkedIndexes = new ArrayList<Integer>();
	// if (checkedItemIndexes != null && checkedItemIndexes.length > 0) {
	// for (int item : checkedItemIndexes) {
	// this._checkedIndexes.add(item);
	// }
	// }
	//
	// this.init(context);
	// }

	private void init(Context context) {

		this.setIcon(0);
		this.setTitle(this._titleResId <= 0 ? R.string.title_question
				: this._titleResId);

		this.setButton1(this.getContext().getString(R.string.text_ok), this);
		this.setButton2(this.getContext().getString(R.string.text_cancel), this);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.select_listdialog, null);

		_innerListView = (ListView) view
				.findViewById(R.id.select_dialog_listview);
		_innerListView
				.setChoiceMode(this._isMultiChooice ? ListView.CHOICE_MODE_MULTIPLE
						: ListView.CHOICE_MODE_SINGLE);
		_innerListView.setItemsCanFocus(false);
		_innerListView.setOnItemClickListener(this);
		_innerListView.setSelector(this.getContext().getResources()
				.getDrawable(R.drawable.dialogbutton));
		_innerListAdapter = new SimpleArrayAdapter<T>(getContext(), this,
				getData());
		_innerListView.setAdapter(_innerListAdapter);

		if (position != -1) {
			_innerListView.setItemChecked(position, true);
		}

		int height = _innerListAdapter.getCount() > 3 ? 350
				: ViewGroup.LayoutParams.WRAP_CONTENT;
		_innerListView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, height));

		this.setView(view);
	}

	/**
	 * @return 获取数据
	 */
	// @SuppressWarnings("unchecked")
	protected List<T> getData() {
		// if (this._wordGroupId >= 0 && this._data == null) {
		// WordsHelper wh = WordsHelper.loadDefault();
		// // this._data = wh.getGroup(this._wordGroupId);
		// List<Words> words = wh.getGroup(this._wordGroupId);
		// this._data = new ArrayList<T>();
		// for (Words wit : words) {
		// this._data.add((T) wit);
		// }
		// }
		return this._data;
	}

	/**
	 * IViewBuilder.build
	 */
	@Override
	public View build(Context context, LayoutInflater inflater,
					  View convertView, ViewGroup parent, Object data, int position) {

		if (convertView == null) {
			// convertView = inflater.inflate(
			// this._isMultiChooice ? android.R.layout.select_dialog_multichoice
			// : android.R.layout.select_dialog_singlechoice, parent, false);

			convertView = inflater.inflate(
					this._isMultiChooice ? R.layout.item_dialog_multichoice
							: R.layout.item_dialog_singlechoice, parent, false);
		}

		CheckedTextView checkedTextView = (CheckedTextView) convertView
				.findViewById(R.id.text1);

		if (_textSize != 0) {
			checkedTextView.setTextSize(_textSize);
		}

		checkedTextView.setChecked(isChecked(position));
		checkedTextView.setText(data.toString());

		return convertView;
	}

	/**
	 * 判断指定位置是否Checked
	 *
	 * @param position
	 *            指定位置
	 * @return 指定位置的Checked状态
	 */
	private boolean isChecked(int position) {
		if (this._checkedIndexes == null || this._checkedIndexes.size() == 0) {
			return false;
		}

		int len = this._checkedIndexes.size();
		boolean checked = false;
		for (int i = 0; i < len; i++) {
			if (this._checkedIndexes.get(i) == position) {
				checked = true;
				break;
			}
		}
		return checked;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (this._listener != null /*&& !ApplicationsUtil.isFastClick()*/) {
			int[] indexes = null;
			Object[] values = null;

			if (which == BUTTON1) { // ok
				int valueCount = this._checkedIndexes.size();
				indexes = new int[valueCount];
				values = new Object[valueCount];
				for (int i = 0; i < valueCount; i++) {
					int index = this._checkedIndexes.get(i).intValue();
					indexes[i] = index;
					values[i] = this._innerListAdapter.getItem(index);
				}
			} else { // cancel, 返回空
				indexes = new int[] {};
				values = new Object[] {};
			}

			this._listener.onSelectedResult(which, indexes, values);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {

		SparseBooleanArray sba = this._innerListView.getCheckedItemPositions();

		boolean checked = sba.get(position);

		LogUtil.d(
				"ListDialog",
				String.format("ListItem, %d: %s", position,
						String.valueOf(checked)));

		if (checked) {
			if (!this._isMultiChooice) {
				this._checkedIndexes.clear();
			}
			this._checkedIndexes.add(position);
		} else {
			this._checkedIndexes.remove(this._checkedIndexes.indexOf(position));
		}
	}
}
