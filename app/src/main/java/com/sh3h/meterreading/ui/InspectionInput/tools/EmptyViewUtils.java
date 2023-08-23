package com.sh3h.meterreading.ui.InspectionInput.tools;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.sh3h.meterreading.R;


/**
 * @author xiaochao.dev@gamil.com
 * @date 2020/3/2 17:04
 */
public class EmptyViewUtils {

    private EmptyViewUtils() {
    }


    public static EmptyViewUtils getSingleIntance() {
        return SingleHolder.singleIntance;
    }

    public View getEmptyView(String emptyText) {
        View view = View.inflate(ActivityUtils.getTopActivity(), R.layout.empty_view, null);
        TextView textview = view.findViewById(R.id.tv_empty);
        textview.setText(emptyText);
        return view;
    }

    static class SingleHolder {
        private static final EmptyViewUtils singleIntance = new EmptyViewUtils();
    }
}
