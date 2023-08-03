package com.sh3h.mobileutil.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AttachmentView extends LinearLayout implements OnClickListener,
		OnItemLongClickListener, OnItemClickListener {

	private Context _context;
	private ImageView _btn;
	private LinearLayout _linearLayout;
	private List<Picture> _picturePath = null;
	public static final int TEXT_HEIGHT = 260;
	public static final int TEXT_WIDTH = 260;

	public static final int GD_ZHAOXIANG = 1001;
	public static final int CB_ZHAOXIANG = 1002;
	public static final int WF_ZHAOXIANG = 1003;
	private int _zhaoxiangType = 1002;
	private int _renWuBH = 0;

	private GridView gridView;
//	private MyAdapter _adapter;
//	private AsyncLoaded _asyncLoaded;
	private String _cID;
	private int _chaoBiaoID;
	private String _ch;
	private TextView img_hint;
	private LinearLayout.LayoutParams _params;

	public AttachmentView(Context context) {
		super(context);

		this._context = context;
		_picturePath = new ArrayList<Picture>();
	}

	public AttachmentView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this._context = context;
		_picturePath = new ArrayList<Picture>();
	}

//	/**
//	 * 关闭异步线程
//	 */
//	public void clearAysncloaded() {
//		if (this._asyncLoaded != null) {
//			this._asyncLoaded.cancel(true);
//			this._asyncLoaded = null;
//		}
//	}
//
//	/**
//	 * 设置LinearLayoutView显示，根据变动的数据，更新多媒体显示信息
//	 */
//	public void setlinearLayoutView() {
//
//		LayoutInflater inflate = LayoutInflater.from(this._context);
//		this._linearLayout = (LinearLayout) inflate.inflate(R.layout.atachment, null);
//		this._btn = (ImageView) this._linearLayout.findViewById(R.id.btn);
//		img_hint = (TextView) this._linearLayout.findViewById(R.id.img_hint);
//		gridView = (GridView) this._linearLayout.findViewById(R.id.app_attachment_grid);
//
//		img_hint.setVisibility(View.VISIBLE);
//
//		_adapter = new MyAdapter();
//		gridView.setAdapter(_adapter);
//
//		gridView.setOnItemLongClickListener(this);
//		gridView.setOnItemClickListener(this);
//		this._btn.setOnClickListener(this);
//
//		_params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//		this._linearLayout.setLayoutParams(_params);
//		this.addView(this._linearLayout);
//	}
//
//	/**
//	 * 显示表务工单图片
//	 *
//	 * @param chaoBiaoID
//	 *            抄表id
//	 * @param cID
//	 *            用户id
//	 * @param ch
//	 *            册本号
//	 */
//	public void AsyncLoad(int chaoBiaoID, String cID, String ch, int type) {
//		_zhaoxiangType = type;
//		_picturePath.clear();
//		_cID = cID;
//		_chaoBiaoID = chaoBiaoID;
//		_ch = ch;
//		_adapter.notifyDataSetChanged();
////		setData(chaoBiaoID, _cID, ch);
//		executeAsyncLoaded();
//	}
//
//	/**
//	 * 开启异步线程，下载图片
//	 */
//	private void executeAsyncLoaded() {
//		if (_picturePath.size() <= 0) {
//			_params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
//					LayoutParams.FILL_PARENT);
//			img_hint.setVisibility(View.VISIBLE);
//		} else {
//			if (getImageSize() > 3) {
//				_params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 260);
//			} else {
//				_params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
//						LayoutParams.FILL_PARENT);
//			}
//			img_hint.setVisibility(View.GONE);
//		}
//		this._linearLayout.setLayoutParams(_params);
//		_asyncLoaded = new AsyncLoaded();
//		_asyncLoaded.execute();
//	}

	/**
	 * 显示表务工单图片
	 *
	 */
//	public void AsyncLoad(int renWuBH, int type) {
//		_renWuBH = renWuBH;
//		_zhaoxiangType = type;
//		_picturePath.clear();
////		setDate(renWuBH, type);
//		executeAsyncLoaded();
//	}

//	/**
//	 * 设置变动 数据
//	 *
//	 * @param chaoBiaoID
//	 * @param CID
//	 */
//	public void setData(int chaoBiaoID, String CID, String ch) {
//
//		IGreenDaoMeterReadingDataProvider dataProvider = MeterReadingDataManager.getGreenDaoDataProvider();
//		List<DuoMeiTXX> list = dataProvider.getDuoMeiTXXList(chaoBiaoID, CID);
//		if (list.size() == 0)
//			return;
//		int index = 0;
//		for (DuoMeiTXX duoMeiTXX : list) {
//			String wenjianLJ = ConfigHelper.getImagePath() + "/" + duoMeiTXX.getS_WENJIANLJ();
//			if (new File(wenjianLJ).exists()) {
//				Picture picture = new Picture(duoMeiTXX.getS_WENJIANMC(), wenjianLJ, index, null);
//				_picturePath.add(picture);
//				index++;
//			}
//		}
//		_adapter.notifyDataSetChanged();
//	}
//
//	public void setDate(int renWuBH, int type) {
//		IGreenDaoMeterReadingDataProvider dataProvider = MeterReadingDataManager.getGreenDaoDataProvider();
//		String imgName = null;
//		if (type == GD_ZHAOXIANG) {
//			imgName = dataProvider.getBiaoWuImgName(renWuBH);
//		} else {
//			imgName = dataProvider.getWaiFuMainImgName(renWuBH);
//		}
//
//		if (imgName == null) {
//			imgName = "";
//		}
//		String[] names = imgName.split(",");
//		if (names == null)
//			return;
//		int index = 0;
//		for (int i = 0; i < names.length; i++) {
//			if (names[i] == null || names[i].equals(""))
//				return;
//			String wenjianLJ = ConfigHelper.getImagePath() + "/" + names[i];
//			if (new File(wenjianLJ).exists()) {
//				Picture picture = new Picture(names[i], wenjianLJ, index, null);
//				_picturePath.add(picture);
//				index++;
//			}
//		}
//		_adapter.notifyDataSetChanged();
//	}

	public void setHintTextColor(int id){
		img_hint.setTextColor(id);
	}


	/**
	 * 返回带边框的 ImageView
	 *
	 * @param color
	 */
	private ImageView getBorderImageView(final int color) {
		return new ImageView(this._context) {
			@SuppressLint("DrawAllocation")
			@Override
			protected void onDraw(Canvas canvas) {
				super.onDraw(canvas);
				Paint paint = new Paint();
				paint.setColor(color);
				canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
				canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
				canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight() - 1,
						paint);
				canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1,
						paint);

			}
		};
	}

	/**
	 * 异步加载压缩图片
	 *
	 * @author zengdezhi
	 *
	 */
/*
	class AsyncLoaded extends AsyncTask<Void, ImageView, Object> {

		@Override
		protected Object doInBackground(Void... params) {
			if (_picturePath.size() > 0) {
				for (int i = 0; i < _picturePath.size(); i++) {
					ImageView imageView = getBorderImageView(Color.WHITE);
					try {
						Bitmap bitmap = SystemEquipmentUtil.decodeBitmap(_picturePath.get(i)
								.getUrl());
						imageView.setTag(_picturePath.get(i));
						imageView.setId(i);
						imageView.setImageBitmap(bitmap);
						publishProgress(imageView);
						if (this.isCancelled()) {
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(ImageView... values) {
			super.onProgressUpdate(values);
			for (ImageView item : values) {
				int id = item.getId();
				_picturePath.get(id).setImageView(item);
				_adapter.notifyDataSetChanged();
			}
		}
	}

	*/
/**
	 * GridView适配器
	 *
	 * @author zengdezhi
	 *
	 *//*

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return _picturePath.size();
		}

		@Override
		public Object getItem(int position) {
			return _picturePath.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (_picturePath.get(position).getImageView() == null) {
				ImageView image = new ImageView(_context);
				image.setBackgroundResource(R.drawable.bg_photo);
				return image;
			}
			return _picturePath.get(position).getImageView();
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.btn) {

			if (_picturePath.size() > 2) {
				ApplicationsUtil.showMessage(_context, "拍摄照片不得超过三张");
				return;
			}

			if (_zhaoxiangType == CB_ZHAOXIANG) {
				try {
					SystemEquipmentUtil.cameraMethod((Activity) AttachmentView.this._context,
							ConfigHelper.getImagePath(), _cID, _chaoBiaoID, _ch);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (_zhaoxiangType == GD_ZHAOXIANG) {
				try {
					SystemEquipmentUtil.cameraMethod((Activity) AttachmentView.this._context,
							_renWuBH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (_zhaoxiangType == WF_ZHAOXIANG) {
				try {
					SystemEquipmentUtil.cameraMethod((Activity) AttachmentView.this._context,
							_renWuBH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	*/
/**
	 * 长按事件，删除对应的多媒体照片资源
	 *//*

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		final Picture pricture = (Picture) arg1.getTag();
		final ImageView imageView = (ImageView) arg1;
		new ConfirmDialog(_context, ConfirmDialog.BUTTON_OK_CANCEL, R.string.title_question,
				R.string.text_phone_delete, new OnConfirmListener() {
			@Override
			public void onResult(int result) {
				if (result == OnConfirmListener.RESULT_YES) {
					String url = pricture.getUrl();
					File file = new File(url);
					if (file.exists()) {
						file.delete();
						IGreenDaoMeterReadingDataProvider dataProvider = MeterReadingDataManager
								.getGreenDaoDataProvider();
						dataProvider.deleteDuoMeiTXX(pricture.getName());
					}
					for (int i = 0; i < _picturePath.size(); i++) {
						if (_picturePath.get(i).getImageView() == imageView) {
							_picturePath.remove(i);
							_adapter.notifyDataSetChanged();
						}
					}
				}
			}
		}).show();
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		List<BaseEquipment> listEquipment = new ArrayList<BaseEquipment>();
		int size = _picturePath.size();
		for (int i = size - 1; i >= 0; i--) {
			BaseEquipment equipment = _picturePath.get(i);
			listEquipment.add(equipment);
		}
		SystemEquipmentUtil.openImage(_context, null, listEquipment, arg2);
	}
*/

	private int getImageSize() {
		return _picturePath.size();
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		return false;
	}
}
