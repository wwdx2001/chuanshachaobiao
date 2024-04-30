package com.sh3h.meterreading.ui.urge.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class OrderDetailFragmentAdapter extends FragmentPagerAdapter {
  private List<Fragment> mFragments;

  public OrderDetailFragmentAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
    super(fm);
    this.mFragments = fragments;
  }

  @NonNull
  @Override
  public Fragment getItem(int position) {
    return mFragments.get(position);
  }

  @Override
  public int getCount() {
    return mFragments.size();
  }

  @Override
  public int getItemPosition(@NonNull Object object) {
    return POSITION_NONE;
  }
}
