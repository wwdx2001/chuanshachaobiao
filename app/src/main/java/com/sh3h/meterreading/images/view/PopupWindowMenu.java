/**
 * @author zeng.jing
 */
package com.sh3h.meterreading.images.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.images.entity.BaseEquipment;
import com.sh3h.meterreading.images.entity.Picture;
import com.sh3h.meterreading.util.SystemEquipmentUtil;
import com.sh3h.mobileutil.view.ConfirmDialog;
import com.sh3h.mobileutil.widget.IBInvoke;
import com.sh3h.mobileutil.widget.PopupWindowImageAdapter;
import com.sh3h.mobileutil.widget.SimpleArrayAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PopupWindowMenu {

    private Context _context = null;
    private PopupWindow _popupWindow = null;
    private Window _window = null;
    private WindowManager.LayoutParams _lp = null;
    public static final int ATLOCATION_TOP = 1;
    public static final int ATLOCATION_DOWN = 2;
    public static final int ATLOCATION_LEFT = 3;
    public static final int ATLOCATION_RIGHT = 4;

    public PopupWindowMenu(Context context) {

        this._context = context;
    }

    public PopupWindowMenu(Context context, Window window) {
        this._window = window;
        this._context = context;
    }

    /**
     * popupWindow 在2.3.3以上版本均可使用
     *
     * @param v        View
     * @param width    弹窗width
     * @param height   弹窗height
     * @param title    内容
     * @param listener 事件
     * @return PopupWindow
     */
    public PopupWindow popupWindow(View v, int atLocation, int width,
                                   int height, String title[], int backgroundId, int itemId,
                                   OnItemClickListener listener) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(_context)
                .inflate(R.layout.popupwindow_list, null);
        ListView listView = (ListView) layout.findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter<String>(_context,
                itemId == 0 ? R.layout.item_popupwindow : itemId, title));
        _popupWindow = new PopupWindow(_context);
        if (backgroundId != -1) {
            _popupWindow.setBackgroundDrawable(_context.getResources()
                    .getDrawable(backgroundId));
        }
        _popupWindow.setWidth(width);
        _popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        _popupWindow.setOutsideTouchable(true);
        _popupWindow.setFocusable(true);
        _popupWindow.setContentView(layout);
        _popupWindow.setAnimationStyle(R.style.PopupAnimationTitleBarTop);
        this.showAtLocation(v, atLocation);
        listView.setOnItemClickListener(listener);

        return _popupWindow;
    }

    @SuppressWarnings("rawtypes")
    public PopupWindow popupWindowListViwe(View v, int atLocation, int width,
                                           int height, int layoutId, int listViewId, String title,
                                           SimpleArrayAdapter adapter, OnItemClickListener listener) {

        LayoutInflater inflater = LayoutInflater.from(_context);
        View view = inflater.inflate(R.layout.layout_titlebar, null);
        TextView titleView = (TextView) view.findViewById(R.id.activity_title);
        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.layouttitlebar);

        LinearLayout layoutList = (LinearLayout) inflater.inflate(layoutId,
                null);

        ListView listView = (ListView) layoutList.findViewById(listViewId);

        layout.addView(layoutList);

        listView.setAdapter(adapter);
        titleView.setText(title);
        listView.setOnItemClickListener(listener);
        _popupWindow = new PopupWindow(_context);
        _popupWindow.setBackgroundDrawable(null);
        _popupWindow.setWidth(width);
        _popupWindow.setHeight(height);
        _popupWindow.setOutsideTouchable(true);
        _popupWindow.setFocusable(true);
        _popupWindow.setContentView(view);
        _popupWindow.setAnimationStyle(R.style.PopupAnimationTitleBarBottom);
        // 物理返回键事件
        _popupWindow.setBackgroundDrawable(new BitmapDrawable());
        this.showAtLocation(v, atLocation);

        // 半透明背景
        _lp = _window.getAttributes();
        _lp.alpha = 0.3f;
        _window.setAttributes(_lp);

        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                _popupWindow.dismiss();
            }
        });

        _popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                _lp.alpha = 1f;
                _window.setAttributes(_lp);
            }
        });

        return _popupWindow;
    }

    @SuppressWarnings("rawtypes")
    public PopupWindow popupWindowImageViwe(View v, int atLocation, int width,
                                            int height, String title, PopupWindowImageAdapter adapter,
                                            final File imageFolder,
                                            final String cH, final List<DUMedia> wenJianXXList,
                                            final IBInvoke iBInvoke) {

        LayoutInflater inflater = LayoutInflater.from(_context);
        View view = inflater.inflate(R.layout.layout_titlebar, null);
        TextView titleView = (TextView) view.findViewById(R.id.activity_title);
        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.layouttitlebar);

        LinearLayout layoutList = (LinearLayout) inflater.inflate(
                R.layout.popupwindow_imageviews, null);

        LinearLayout picLinearLayout = (LinearLayout) layoutList.findViewById(R.id.pictures_bg);
        if (wenJianXXList.size() > 0) {
            picLinearLayout.setVisibility(View.VISIBLE);
        } else {
            picLinearLayout.setVisibility(View.INVISIBLE);
        }

        GridView gridView = (GridView) layoutList.findViewById(R.id.pictures);
        layout.addView(layoutList);

        gridView.setAdapter(adapter);
        titleView.setText(title);
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                List<BaseEquipment> listEquipment = new ArrayList<BaseEquipment>();
                int size = wenJianXXList.size();
//				String imgPath = ConfigHelper.getImagePath();
//				File dir = new File(imgPath, cH);
                File dir = new File(imageFolder, cH);
                for (int i = size - 1; i >= 0; i--) {
                    String path = dir.getPath() + "/"
                            + wenJianXXList.get(i).getWenjianmc();
                    Picture picture = new Picture(wenJianXXList.get(i)
                            .getWenjianmc(), path, i, null);
                    listEquipment.add(picture);
                }
                SystemEquipmentUtil.openImage(_context, null, listEquipment,
                        arg2);
            }
        });
        gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int arg2, long arg3) {
                new ConfirmDialog(_context, ConfirmDialog.BUTTON_OK_CANCEL,
                        R.string.title_question, R.string.main_question_image,
                        new ConfirmDialog.OnConfirmListener() {
                            @Override
                            public void onResult(int result) {
                                if (result == ConfirmDialog.OnConfirmListener.RESULT_OK) {
                                    iBInvoke.after(arg2);
                                }
                            }
                        }).show();
                return true;
            }
        });

        _popupWindow = new PopupWindow(_context);
        _popupWindow.setBackgroundDrawable(null);
        _popupWindow.setWidth(width);
        _popupWindow.setHeight(height);
        _popupWindow.setOutsideTouchable(true);
        _popupWindow.setFocusable(true);
        _popupWindow.setContentView(view);
        _popupWindow.setAnimationStyle(R.style.PopupAnimationTitleBarBottom);
        // 物理返回键事件
        _popupWindow.setBackgroundDrawable(new BitmapDrawable());
        this.showAtLocation(v, atLocation);

        // 半透明背景
        _lp = _window.getAttributes();
        _lp.alpha = 0.3f;
        _window.setAttributes(_lp);

        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                _popupWindow.dismiss();
            }
        });

        _popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                _lp.alpha = 1f;
                _window.setAttributes(_lp);
            }
        });

        return _popupWindow;
    }


    @SuppressWarnings("rawtypes")
    public PopupWindow popupWindowImageView(View v, int atLocation, int width,
                                            int height, String title, PopupWindowImageAdapter adapter,
                                            final File imageFolder,
                                            final int groupId, final List<DUMedia> wenJianXXList,
                                            final IBInvoke iBInvoke) {

        LayoutInflater inflater = LayoutInflater.from(_context);
        View view = inflater.inflate(R.layout.layout_titlebar, null);
        TextView titleView = (TextView) view.findViewById(R.id.activity_title);
        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.layouttitlebar);

        LinearLayout layoutList = (LinearLayout) inflater.inflate(
                R.layout.popupwindow_imageviews, null);

        LinearLayout picLinearLayout = (LinearLayout) layoutList.findViewById(R.id.pictures_bg);
        if (wenJianXXList.size() > 0) {
            picLinearLayout.setVisibility(View.VISIBLE);
        } else {
            picLinearLayout.setVisibility(View.INVISIBLE);
        }

        GridView gridView = (GridView) layoutList.findViewById(R.id.pictures);
        layout.addView(layoutList);

        gridView.setAdapter(adapter);
        titleView.setText(title);
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                List<BaseEquipment> listEquipment = new ArrayList<BaseEquipment>();
                int size = wenJianXXList.size();
//				String imgPath = ConfigHelper.getImagePath();
//				File dir = new File(imgPath, cH);
                File dir = new File(imageFolder, String.valueOf(groupId));
                for (int i = size - 1; i >= 0; i--) {
                    String path = dir.getPath() + "/"
                            + wenJianXXList.get(i).getWenjianmc();
                    Picture picture = new Picture(wenJianXXList.get(i)
                            .getWenjianmc(), path, i, null);
                    listEquipment.add(picture);
                }
                SystemEquipmentUtil.openImage(_context, null, listEquipment,
                        arg2);
            }
        });
        gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int arg2, long arg3) {
                new ConfirmDialog(_context, ConfirmDialog.BUTTON_OK_CANCEL,
                        R.string.title_question, R.string.main_question_image,
                        new ConfirmDialog.OnConfirmListener() {
                            @Override
                            public void onResult(int result) {
                                if (result == ConfirmDialog.OnConfirmListener.RESULT_OK) {
                                    iBInvoke.after(arg2);
                                }
                            }
                        }).show();
                return true;
            }
        });

        _popupWindow = new PopupWindow(_context);
        _popupWindow.setBackgroundDrawable(null);
        _popupWindow.setWidth(width);
        _popupWindow.setHeight(height);
        _popupWindow.setOutsideTouchable(true);
        _popupWindow.setFocusable(true);
        _popupWindow.setContentView(view);
        _popupWindow.setAnimationStyle(R.style.PopupAnimationTitleBarBottom);
        // 物理返回键事件
        _popupWindow.setBackgroundDrawable(new BitmapDrawable());
        this.showAtLocation(v, atLocation);

        // 半透明背景
        _lp = _window.getAttributes();
        _lp.alpha = 0.3f;
        _window.setAttributes(_lp);

        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                _popupWindow.dismiss();
            }
        });

        _popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                _lp.alpha = 1f;
                _window.setAttributes(_lp);
            }
        });

        return _popupWindow;
    }

    /**
     * 设置位置
     */
    private void showAtLocation(View v, int atLocation) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);

        switch (atLocation) {
            case ATLOCATION_TOP:
                _popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0],
                        location[1] - _popupWindow.getHeight());
                break;
            case ATLOCATION_DOWN:
                _popupWindow.showAsDropDown(v);// 向下
                break;
            case ATLOCATION_LEFT:
                _popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0]
                        - _popupWindow.getWidth(), location[1]);
                break;
            case ATLOCATION_RIGHT:
                _popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
                        location[0] + v.getWidth(), location[1]);
                break;
            default:
                break;
        }
    }

    /**
     * 点击popupWindow后消失
     */
    public void popupWindowDismiss() {
        _popupWindow.dismiss();
    }

    /**
     * popupMenu 3.0以上版本使用
     *
     * @param menuRes menu.xml
     */
    @SuppressLint("NewApi")
    public void popupMenu(View v, int menuRes, OnMenuItemClickListener listener) {
        PopupMenu popupMenu = new PopupMenu(_context, v);
        MenuInflater inFlater = popupMenu.getMenuInflater();
        inFlater.inflate(menuRes, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(listener);
    }
}
