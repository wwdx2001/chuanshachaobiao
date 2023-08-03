/**
 *
 */
package com.sh3h.meterreading.images.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.gc.materialdesign.views.CheckBox;
import com.kankan.wheel.WheelView;
import com.kankan.wheel.adapter.ArrayWheelAdapter;
import com.kankan.wheel.widget.OnWheelChangedListener;
import com.kankan.wheel.widget.OnWheelScrollListener;
import com.sh3h.dataprovider.DBManager;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoZT;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoZTFL;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.UserConfig;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.mobileutil.view.BaseDialog;
import com.sh3h.mobileutil.widget.SimpleArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表选择对话框
 */
@SuppressLint("ResourceAsColor")
public class ChaoBiaoZTChooseDialog extends BaseDialog implements
//		IViewBuilder,
		OnClickListener
		, OnItemClickListener
		, OnCheckedChangeListener, OnWheelChangedListener,
		OnWheelScrollListener, View.OnClickListener {

	public static final String DALET_BM = "daLeiBM";
	public static final String XIAOLET_BM = "xiaoLeiBM";
	public static final String XIAOLET_MC = "xiaoLeiMC";
	public static final String DALET_MC = "daLeiMC";
	public static final String CHANGYONG_MC = "changYongMC";
	public static final String CHANGYONG_BM = "changYongBM";

	private MyReceiver _receiver = null;
	private List<ChaoBiaoZT> _chaoBiaoZTList = null;
	private Bundle _bundle = null;

	private ChaoBiaoZTChooseListener _listener = null;
	private Context _context;
	private ListView _listView;
	private boolean _scrolling = false;
	private View _view;
	private LayoutInflater _inflater = null;
	private LinearLayout _window_dialog;

	private String _zhuangtaiDaLei;
	private String _zhuangtaiXiaoLei;
	private int _daLeiBM;
	private int _xiaoLeiBM;
	private String _changYongZTMC;
	private int _changYongZTBM;
	private boolean _flag = false;

	private TextView _dialog_text;
	private LinearLayout _dialog_layout;
	//	private IGreenDaoMeterReadingDataProvider _dataProvider;
	private ArrayWheelAdapter<ChaoBiaoZTFL> _daLeiAdapter = null;
	private ArrayWheelAdapter<ChaoBiaoZT> _cityAdapter = null;
	private WheelView _country;
	private WheelView _city;
	private SimpleArrayAdapter<ChaoBiaoZT> _innerListAdapter;

	private ConfigHelper mConfigHelper = null;

	private int index = -1;
	private int djChecekBox = -1;//只点击checkBox

	public class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			init();
		}

	}

	public ChaoBiaoZTChooseDialog(ConfigHelper configHelper, Context context, ChaoBiaoZTChooseListener listener) {
		super(context);
		this._listener = listener;
		this.mConfigHelper = configHelper;
		this._context = context;
		this._inflater = (LayoutInflater) this._context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		IntentFilter filter = new IntentFilter("update_UseageData");
		_receiver = new MyReceiver();
		context.registerReceiver(_receiver, filter);
		init();
	}

	private void init() {
		this.setTitle(0);
		this.setButton1(this._context.getString(R.string.text_ok), this);
		this.setButton2(this._context.getString(R.string.text_cancel), this);

		this._view = this._inflater.inflate(R.layout.chaobiao_list_dialog, null);

		RadioGroup dialog_radioGroup = (RadioGroup) this._view.findViewById(R.id.dialog_radio);

		dialog_radioGroup.setOnCheckedChangeListener(this);

		this._window_dialog = (LinearLayout) this._view.findViewById(R.id.window_dialog);
		this._dialog_layout = (LinearLayout) _view.findViewById(R.id.dialog_layout);
		this._dialog_text = (TextView) _view.findViewById(R.id.dialog_text);
		this._dialog_text.setOnClickListener(this);

		this._listView = (ListView) _view.findViewById(R.id.chaobiao_dialog_list);
		this._listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		this._listView.setItemsCanFocus(false);
		this._listView.setOnItemClickListener(this);

		this._country = (WheelView) this._view.findViewById(R.id.country);
		this._city = (WheelView) this._view.findViewById(R.id.city);
		// 数据
		this._country.setVisibility(2);
		List<ChaoBiaoZTFL> chaoBiaoZTFLList = DBManager.getInstance().getAllChaobiaoZTFL();
		String[] cbztMC = new String[chaoBiaoZTFLList.size()];

		this._daLeiAdapter = new ArrayWheelAdapter<ChaoBiaoZTFL>(this._context,
				chaoBiaoZTFLList.toArray(new ChaoBiaoZTFL[chaoBiaoZTFLList.size()]));

		this._country.setViewAdapter(this._daLeiAdapter);
		this._country.addChangingListener(this);
		this._country.addScrollingListener(this);
		this._city.addScrollingListener(this);
		this._country.setCurrentItem(0);
		ChaoBiaoZTFL ztfl = _daLeiAdapter.getItemValue(_country.getCurrentItem());
		this._cityAdapter = new ArrayWheelAdapter<ChaoBiaoZT>(_context, filter(ztfl.getI_FenLeiBM()));
		this._city.setViewAdapter(_cityAdapter);
		this._city.setCurrentItem(0);
		setValues(_country, _city);
		// 设置显示视图为常用ListView
		loadCommonUseageData();

		// 设置显示View
		super.setView(_view);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
			case R.id.dialog_changyong:// 常用
				this._window_dialog.setVisibility(View.GONE);
				this._dialog_layout.setVisibility(View.VISIBLE);
				if (_innerListAdapter == null) {
					this._dialog_text.setVisibility(View.VISIBLE);
				} else {
					if (_innerListAdapter.getCount() == 0) {
						this._dialog_text.setVisibility(View.VISIBLE);
					}
				}
				this._zhuangtaiDaLei = "";
				this._zhuangtaiXiaoLei = "";
				this._xiaoLeiBM = 0;
				this._daLeiBM = 0;

				break;
			case R.id.dialog_all:// 全部

				this._dialog_text.setVisibility(View.GONE);
				this._dialog_layout.setVisibility(View.GONE);
				this._window_dialog.setVisibility(View.VISIBLE);
				this._flag = true;
				break;

			default:
				break;
		}
	}

	/**
	 * 加载常用列表内数据
	 */
	private void loadCommonUseageData() {
		// 获取数据
//		IGreenDaoMeterReadingDataProvider dataProvider = MeterReadingDataManager
//				.getGreenDaoDataProvider();

//		MainApplication application = ((MainApplication) this._context.getApplicationContext());
//		String user_changYong = application.getUserConfig(application.getUserId()).getString(
//				UserSetting.PARAM_CB_DEFAULT_CYCBZT);

		String user_changYong = mConfigHelper.getUserConfig().getString(UserConfig.PARAM_CB_DEFAULT_CYCBZT);

		if (!TextUtil.isNullOrEmpty(user_changYong)) {
			this._listView.setVisibility(View.VISIBLE);
			this._dialog_text.setVisibility(View.GONE);
			this._chaoBiaoZTList = DBManager.getInstance().getZhuangTaiBMList(user_changYong);
			_innerListAdapter = new MyAdapter(this._context
					,this._chaoBiaoZTList);
			this._listView.setAdapter(_innerListAdapter);
		} else {
			this._listView.setVisibility(View.GONE);
			this._dialog_text.setVisibility(View.VISIBLE);
		}
	}

//	/**
//	 * 常用抄表状态，列表项构建方法
//	 */
//	@Override
//	public View build(Context context, LayoutInflater inflater, View convertView, ViewGroup parent,
//					  Object data, int position) {
//		mInflater = inflater;
//		if (convertView == null) {
//			convertView = inflater.inflate(R.layout.item_dialog_singlechoice_md, parent, false);
//		}
//		TextView tv = (TextView) convertView.findViewById(R.id.singlechoic_md);
//
////		checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
//
//		// tv.setTextColor(getContext().getResources().getColor(R.drawable.button7_text));
//		// tv.setTextSize(20);
//		// tv.setTypeface(Typeface.DEFAULT_BOLD);
//		ChaoBiaoZT chaoBiaoZT = (ChaoBiaoZT) data;
//		tv.setText(chaoBiaoZT.getS_ZHUANGTAIMC());
//		return convertView;
//	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (this._listener != null) {
			this._bundle = new Bundle();
			if (which == BUTTON1) {// Ok
				// this._bundle.putString(CHANGYONG_MC, this._changYongZTMC);
				// this._bundle.putInt(CHANGYONG_BM, this._changYongZTBM);
				this._bundle.putString(XIAOLET_MC, this._changYongZTMC);
				this._bundle.putInt(XIAOLET_BM, this._changYongZTBM);
				if (this._flag) {
					this._bundle.putString(DALET_MC, this._zhuangtaiDaLei);
					this._bundle.putString(XIAOLET_MC, this._zhuangtaiXiaoLei);
					this._bundle.putInt(XIAOLET_BM, this._xiaoLeiBM);
					this._bundle.putInt(DALET_BM, this._daLeiBM);
				}

				this._listener.onResult(this._bundle);

			} else {
				this._listener.onResult(null);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		index = arg2;
		djChecekBox = arg2;
		ChaoBiaoZT chaoBiaoZT = this._chaoBiaoZTList.get(arg2);
		this._changYongZTMC = chaoBiaoZT.getS_ZHUANGTAIMC();
		this._changYongZTBM = chaoBiaoZT.getI_ZHUANGTAIBM();
		_innerListAdapter.notifyDataSetChanged();
	}


	/**
	 * 二级联动选择抄表编码名称
	 *
	 * @return
	 */
	public void loadAllData(View view) {

	}

	/**
	 * 设置接口返回值
	 *
	 * @param country
	 * @param city
	 */
	private void setValues(final WheelView country, final WheelView city) {
		ChaoBiaoZT zt = _cityAdapter.getItemValue(city.getCurrentItem());
		ChaoBiaoZTFL ztfl = _daLeiAdapter.getItemValue(country.getCurrentItem());
		if (ztfl != null) {
			_zhuangtaiDaLei = ztfl.getS_FenLeiMC();
			_daLeiBM = ztfl.getI_FenLeiBM();
		}
		if (zt != null) {
			_zhuangtaiXiaoLei = zt.getS_ZHUANGTAIMC();
			_xiaoLeiBM = zt.getI_ZHUANGTAIBM();
		}
		// Toast.makeText(
		// this._context,
		// _zhuangtaiDaLei + "和" + _daLeiBM + "  " + _zhuangtaiXiaoLei
		// + "和" + _xiaoLeiBM, Toast.LENGTH_SHORT).show();
	}

	/**
	 *
	 * @param flbm
	 * @return
	 */
	private ChaoBiaoZT[] filter(int flbm) {
		List<ChaoBiaoZT> tmp = new ArrayList<ChaoBiaoZT>();
		List<ChaoBiaoZT> fl = DBManager.getInstance().getAllChaobiaozt();
		ChaoBiaoZT[] cbzt = new ChaoBiaoZT[tmp.size()];
		for (int i = 0; i < fl.size(); i++) {
			if (fl.get(i).getI_ZHUANGTAIFLBM() == flbm) {
				tmp.add(fl.get(i));
			}
		}
		return tmp.toArray(new ChaoBiaoZT[tmp.size()]);
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (!_scrolling) {
			setAdapter();
		}
		_city.setCurrentItem(0);
	}

	/**
	 * 设置是配置中的值
	 */
	private void setAdapter() {
		ChaoBiaoZTFL ztfl = _daLeiAdapter.getItemValue(_country.getCurrentItem());
		_cityAdapter = new ArrayWheelAdapter<ChaoBiaoZT>(_context, filter(ztfl.getI_FenLeiBM()));
		_city.setViewAdapter(_cityAdapter);
		setValues(_country, _city);
	}

	@Override
	public void onScrollingStarted(WheelView wheel) {
		_scrolling = true;
	}

	@Override
	public void onScrollingFinished(WheelView wheel) {
		_scrolling = false;
		this.setAdapter();
	}

	@Override
	public void onClick(View v) {
		if (R.id.dialog_text == v.getId()) {
//			Intent intent = new Intent(this._context, UserCommonActivity.class);
//			this._context.startActivity(intent);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (_receiver != null) {
			this._context.unregisterReceiver(_receiver);
			_receiver = null;
		}
	}

	private class MyAdapter extends SimpleArrayAdapter {
		Context mContext;
		List<ChaoBiaoZT> chaoBiaoZTList;
		//		IViewBuilder _viewBuilder =mContext;
		public MyAdapter(Context context, List objects) {
			super(context, objects);
			mContext = context;
			chaoBiaoZTList = objects;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null){
				convertView = _inflater.inflate(R.layout.item_dialog_singlechoice_md,
						null);
			}
			TextView tv = (TextView) convertView.findViewById(R.id.singlechoic_md);
			CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
			if (index != -1){
				if (index == position){
					checkBox.setChecked(true);
				}else
				{
					checkBox.setChecked(false);
				}
			}
			ChaoBiaoZT chaoBiaoZT = chaoBiaoZTList.get(position);
			tv.setText(chaoBiaoZT.getS_ZHUANGTAIMC());

			checkBox.setOncheckListener(new CheckBox.OnCheckListener() {
				@Override
				public void onCheck(CheckBox view, boolean check) {
					int poisition = _listView.getPositionForView(view);
					ChaoBiaoZT chaoBiaoZT = _chaoBiaoZTList.get(position);
					_changYongZTMC = chaoBiaoZT.getS_ZHUANGTAIMC();
					_changYongZTBM = chaoBiaoZT.getI_ZHUANGTAIBM();
					index = poisition;
					_innerListAdapter.notifyDataSetChanged();
				}
			});
			return convertView;
		}
	}
}
