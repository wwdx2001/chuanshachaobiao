package com.sh3h.meterreading.util;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerSpacingItemDecoration extends RecyclerView.ItemDecoration {

  private int left;
  private int top;
  private int right;
  private int bottom;

  public RecyclerSpacingItemDecoration(int left, int top, int right, int bottom) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  @Override
  public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);

    outRect.left = ScreenUtils.dip2px(parent.getContext(), left);
    outRect.top = ScreenUtils.dip2px(parent.getContext(), top);
    outRect.right = ScreenUtils.dip2px(parent.getContext(), right);
    outRect.bottom = ScreenUtils.dip2px(parent.getContext(), bottom);
  }
}
