package com.sh3h.meterreading.ui.InspectionInput.lr;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sh3h.meterreading.ui.base.ParentFragment;

import java.util.List;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2019/12/11 17:51
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<ParentFragment> data;

    public MyFragmentPagerAdapter(FragmentManager fm, List<ParentFragment> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
