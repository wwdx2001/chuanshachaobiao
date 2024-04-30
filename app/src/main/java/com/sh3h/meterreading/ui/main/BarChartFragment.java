/**
 * @author zengdezhi
 */
package com.sh3h.meterreading.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.mobileutil.util.LogUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarChartFragment extends ParentFragment implements OnChartValueSelectedListener {
    private static final String TAG = "BarChartFragment";

    @BindView(R.id.chart1)
    BarChart mChart;

    @Inject
    Bus mEventBus;

    private StatisticsActivity statisticsActivity;

    private boolean isPrepared;

    private Map<String, Integer> mMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statisticsActivity = (StatisticsActivity) getActivity();
        statisticsActivity.getActivityComponent().inject(this);
        mEventBus.register(this);
        LogUtil.i(TAG, "---onCreate---");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_barchart, container, false);

        ButterKnife.bind(this, rootView);

        isPrepared = true;
        lazyLoad();

        LogUtil.i(TAG, "---onCreateView---");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshChart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        isPrepared = false;
        mEventBus.unregister(this);
//        ButterKnife.unbind(this);
        LogUtil.i(TAG, "---onDestroyView---");
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void updateBarChart() {
        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(15);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(0);
        xAxis.setDrawGridLines(false);

        mChart.getAxisLeft().setDrawGridLines(false);

        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setEnabled(false);

        mChart.animateY(1000);

        mChart.getLegend().setEnabled(false);

        int[] colors = new int[]{
                getResources().getColor(R.color.colors_bar_1),
                getResources().getColor(R.color.colors_bar_2),
                /*getResources().getColor(R.color.colors_bar_3)*/};

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        yVals1.add(new BarEntry(mMap.get(MainActivity.YICHAO), 0));
//		yVals1.add(new BarEntry(mMap.get(MainActivity.GUCHAO), 1));
        yVals1.add(new BarEntry(mMap.get(MainActivity.WEICHAO), 1));

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add(MainActivity.YICHAO);
//		xVals.add(MainActivity.GUCHAO);
        xVals.add(MainActivity.WEICHAO);

        BarDataSet set1 = new BarDataSet(yVals1, "Data Set");
        set1.setColors(colors);
        set1.setDrawValues(false);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);

        mChart.setData(data);

        for (DataSet<?> set : mChart.getData().getDataSets()) {
            set.setDrawValues(!set.isDrawValuesEnabled());
            set.setValueTextSize(20);
        }

        mChart.setTouchEnabled(false);
        mChart.invalidate();
    }

    public void refreshChart() {
        if (mChart != null) {
            mChart.animateY(1000);
            mChart.invalidate();
        }
    }

    @Override
    protected void lazyLoad() {
        LogUtil.i(TAG, "---lazyLoad 1---");
        if (!isPrepared || !isVisible) {
            return;
        }

        refreshChart();
        LogUtil.i(TAG, "---lazyLoad 2---");
    }

    @Subscribe
    public void onStatisticDataFinish(UIBusEvent.StatisticDataFinish statisticDataFinish) {
        LogUtil.i(TAG, "---onStatisticDataFinish---");
        DUTask duTask = statisticDataFinish.getDuTask();
        mMap = new HashMap<String, Integer>();
        mMap.put("已抄", duTask.getYiChaoShu());
        mMap.put("未抄", duTask.getZongShu() - duTask.getYiChaoShu());
        updateBarChart();
    }
}
