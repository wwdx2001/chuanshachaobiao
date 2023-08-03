package com.sh3h.meterreading.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class UserCommonAdapter extends BaseAdapter implements
		OnCheckedChangeListener {

	private int mZhuangTaiID;
	private Context mContext;
	private List<ZhuangTaiBM> mZhuangTaiBMList = null;
	private Map<Integer, List<ZhuangTaiBM>> mMapList = null;
	private List<DUChaoBiaoZT> mChaoBiaoZTList = null;
	private String user_changYong;
	private boolean enable = true;

	public UserCommonAdapter(Context context, int zhuangTaiBM,
							 List<DUChaoBiaoZT> dataList,String user_changYong) {
		super();
		this.mContext = context;
		this.mZhuangTaiID = zhuangTaiBM;
		mChaoBiaoZTList = dataList;
		this.user_changYong = user_changYong;
		this.mMapList = new HashMap<>();
		setAdapter();
	}

	/**
	 * 第一次进来，默认设为正常大类，显示正常对应的小类信息
	 */
	public void setAdapter() {
		appedList(this.mZhuangTaiID);
		this.mZhuangTaiBMList = mMapList.get(this.mZhuangTaiID);
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * 获取大类中对应小类的数据，保存Map文件中
	 *
	 * @param bMId
	 *            大类id
	 */
	public void appedList(int bMId) {
		MainApplication application = ((MainApplication) this.mContext
				.getApplicationContext());
//		String user_changYong = application.getUserConfig(
//				application.getUserId()).getString(
//				UserSetting.PARAM_CB_DEFAULT_CYCBZT);
		String[] changYong = null;
		if (user_changYong != null) {
			if (!user_changYong.equals("")) {
				changYong = user_changYong.split(",");
			}
		}

		List<ZhuangTaiBM> zhuangTaiBMList = new ArrayList<ZhuangTaiBM>();
		for (DUChaoBiaoZT item : mChaoBiaoZTList) {
			if (item.getZhuangtaiflbm() == bMId) {
				ZhuangTaiBM zhuangTaiBM = new ZhuangTaiBM();
				if (changYong != null) {
					for (int i = 0; i < changYong.length; i++) {
						int bm = Integer.parseInt(changYong[i]);
						if (bm == item.getZhuangtaibm()) {
							zhuangTaiBM.setbMState(1);
						}
					}
				}
				zhuangTaiBM.setbMId(item.getZhuangtaibm());
				zhuangTaiBM.setbMName(item.getZhuangtaimc());
				zhuangTaiBMList.add(zhuangTaiBM);
			}
		}
		this.mMapList.put(bMId, zhuangTaiBMList);
	}

	/**
	 * 获取用户选择的常用编码
	 *
	 * @return List<ChaoBiaoZT>
	 */
	public List<ChaoBiaoZT> getUserChangYong() {
		List<ChaoBiaoZT> list = new ArrayList<ChaoBiaoZT>();
		for (Entry<Integer, List<ZhuangTaiBM>> entry : mMapList.entrySet()) {
			List<ZhuangTaiBM> zhuangtTaiBMList = entry.getValue();
			for (ZhuangTaiBM item : zhuangtTaiBMList) {
				if (item.getbMState() == 1) {
					ChaoBiaoZT chaoBiaoZT = new ChaoBiaoZT();
					chaoBiaoZT.setS_ZHUANGTAIMC(item.getbMName());
					chaoBiaoZT.setI_ZHUANGTAIBM(item.getbMId());
					list.add(chaoBiaoZT);
				}
			}
		}

		return list;
	}

	/**
	 * 当切换大类时，获取对应的小类值，设置到适配器中
	 *
	 * @param bMId
	 *            大类id
	 */
	public void setDaleiId(int bMId) {
		List<ZhuangTaiBM> list = this.mMapList.get(bMId);
		if (list != null) {
			this.mZhuangTaiBMList = list;
		} else {
			appedList(bMId);
			List<ZhuangTaiBM> mapList = this.mMapList.get(bMId);
			this.mZhuangTaiBMList = mapList;
		}
		notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		return this.mZhuangTaiBMList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.mZhuangTaiBMList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) this.mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_usercommon_child,
					parent, false);
		}
		ZhuangTaiBM zhuangTaiBM = this.mZhuangTaiBMList.get(position);
		TextView content = (TextView) convertView.findViewById(R.id.userCommon);
		// content.setTextColor(0xffffffff);
		content.setText(zhuangTaiBM.getbMName());
		CheckBox user_checkbox = (CheckBox) convertView.findViewById(R.id.user_checkbox);
		user_checkbox.setTag(zhuangTaiBM);

		user_checkbox.setChecked(zhuangTaiBM.getbMState() == 1);
		user_checkbox.setEnabled(enable);
		user_checkbox.setOnCheckedChangeListener(this);
		return convertView;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		ZhuangTaiBM zhuangTaiBM = (ZhuangTaiBM) buttonView.getTag();
		if (isChecked) {
			// 保存状态
			zhuangTaiBM.setbMState(1);
		} else {
			// 取消状态
			zhuangTaiBM.setbMState(0);
		}

	}

	/**
	 * 状态BM类，保存临时的数据
	 *
	 */
	class ZhuangTaiBM {
		private int _bMId;
		private int _bMState;
		private String _bMName;

		/**
		 * @return the bMId
		 */
		public int getbMId() {
			return _bMId;
		}

		/**
		 * @param bMId
		 *            the bMId to set
		 */
		public void setbMId(int bMId) {
			this._bMId = bMId;
		}

		/**
		 * @return the bMState
		 */
		public int getbMState() {
			return _bMState;
		}

		/**
		 * @param bMState
		 *            the bMState to set
		 */
		public void setbMState(int bMState) {
			this._bMState = bMState;
		}

		/**
		 * @return the bMName
		 */
		public String getbMName() {
			return _bMName;
		}

		/**
		 * @param bMName
		 *            the bMName to set
		 */
		public void setbMName(String bMName) {
			this._bMName = bMName;
		}

	}
}
