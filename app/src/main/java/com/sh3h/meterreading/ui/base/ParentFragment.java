package com.sh3h.meterreading.ui.base;

import android.support.v4.app.Fragment;

import java.text.NumberFormat;

public abstract class ParentFragment extends Fragment {

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible = true;

    public NumberFormat _f = NumberFormat.getInstance();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {


    }


    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();
}
