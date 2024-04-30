package com.sh3h.meterreading.ui.custom_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SelectSpinnerView extends AppCompatSpinner {
  private SpinnerItemClick mItemClick;

  public SelectSpinnerView(Context context) {
    super(context);
  }

  public SelectSpinnerView(Context context, int mode) {
    super(context, mode);
    init();
  }

  public SelectSpinnerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public SelectSpinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public SelectSpinnerView(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
    super(context, attrs, defStyleAttr, mode);
    init();
  }

  public SelectSpinnerView(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
    super(context, attrs, defStyleAttr, mode, popupTheme);
    init();
  }

  @TargetApi(Build.VERSION_CODES.KITKAT)
  private void init() {
    Class<?> myClass = Spinner.class;
    try {
      Class<?>[] params = new Class[1];
      params[0] = AdapterView.OnItemClickListener.class;
      Method m = myClass.getDeclaredMethod("setOnItemClickListenerInt", params);
      m.setAccessible(true);
      m.invoke(this, new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          Class<?> clazz = AdapterView.class;
          try {
            Field field = clazz.getDeclaredField("mOldSelectedPosition");
            field.setAccessible(true);                                          //设置mOldSelectedPosition可访问
            field.setInt(SelectSpinnerView.this, AdapterView.INVALID_POSITION);     //设置mOldSelectedPosition的值
            if (mItemClick != null) {
              mItemClick.onSpinnerItemClick(position);
            }

          } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
          }
        }
      });
    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      e.printStackTrace();
    }

  }

  public void setItemClick(SpinnerItemClick spinnerItemClick) {
    this.mItemClick = spinnerItemClick;
  }

  public interface SpinnerItemClick {
    void onSpinnerItemClick(int position);
  }
}
